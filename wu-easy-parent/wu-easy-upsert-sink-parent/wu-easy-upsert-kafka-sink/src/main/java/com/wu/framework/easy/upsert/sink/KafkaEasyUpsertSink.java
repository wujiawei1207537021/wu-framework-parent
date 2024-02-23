package com.wu.framework.easy.upsert.sink;


import com.google.common.collect.Maps;
import com.wu.framework.easy.upsert.autoconfigure.EasySmart;
import com.wu.framework.easy.upsert.autoconfigure.config.SpringUpsertAutoConfigure;
import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.upsert.autoconfigure.enums.EasyUpsertType;
import com.wu.framework.easy.upsert.autoconfigure.sink.LocalStorageClassAnnotation;
import com.wu.framework.easy.upsert.core.dynamic.IEasyUpsert;
import com.wu.framework.easy.upsert.core.dynamic.exception.UpsertException;
import com.wu.framework.easy.upsert.core.dynamic.function.EasyUpsertFunction;
import com.wu.framework.easy.upsert.sink.converter.ConverterClass2KafkaSchema;
import com.wu.framework.easy.upsert.sink.converter.JsonFileConverter;
import com.wu.framework.easy.upsert.sink.kafka.KafkaJsonMessage;
import com.wu.framework.easy.upsert.sink.kafka.TargetJsonSchema;
import com.wu.framework.inner.layer.data.ClassSchema;
import com.wu.framework.inner.layer.data.IBeanUpsert;
import com.wu.framework.inner.layer.data.ProcessException;
import com.wu.framework.inner.layer.data.UserConvertService;
import com.wu.framework.inner.lazy.persistence.analyze.EasyAnnotationConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * description kafka数据插入
 *
 * @author Jia wei Wu
 * @date 2020/9/11 上午10:22
 */
@Slf4j
@ConditionalOnBean(EasyUpsertExtractKafkaProducer.class)
@EasyUpsertStrategy(value = EasyUpsertType.KAFKA)
public class KafkaEasyUpsertSink implements IEasyUpsert {

    private final UserConvertService userConvertService;
    private final SpringUpsertAutoConfigure springUpsertAutoConfigure;
    private final EasyUpsertExtractKafkaProducer easyUpsertExtractKafkaProducer;

    public KafkaEasyUpsertSink(UserConvertService userConvertService, SpringUpsertAutoConfigure springUpsertAutoConfigure, EasyUpsertExtractKafkaProducer easyUpsertExtractKafkaProducer) {
        this.userConvertService = userConvertService;
        this.springUpsertAutoConfigure = springUpsertAutoConfigure;
        this.easyUpsertExtractKafkaProducer = easyUpsertExtractKafkaProducer;
    }

    @Override
    public <T> Object upsert(List<T> list, ClassSchema classSchema) throws UpsertException, ExecutionException, InterruptedException {
        splitListThen(list, springUpsertAutoConfigure.getBatchLimit(), new EasyUpsertFunction() {
            @Override
            public <t> void handle(List<t> source) {
                Class<T> clazz = classSchema.clazz();
                // 模块名称+业务+表名
                EasySmart lazyTableAnnotation =
                        LocalStorageClassAnnotation.getEasyTableAnnotation(clazz, springUpsertAutoConfigure.isForceDuplicateNameSwitch());
                String schemaName = lazyTableAnnotation.kafkaSchemaName();

                TargetJsonSchema targetJsonSchema = KafkaJsonMessage.targetSchemaMap.get(schemaName);
                if (targetJsonSchema == null) {
                    synchronized (KafkaJsonMessage.targetSchemaMap) {
                        targetJsonSchema = ConverterClass2KafkaSchema.converterClass2TargetJsonSchema(clazz, springUpsertAutoConfigure.isForceDuplicateNameSwitch());
                        KafkaJsonMessage.targetSchemaMap = Maps.uniqueIndex(Arrays.asList(targetJsonSchema), TargetJsonSchema::getName);
                        log.info(" Automatic loading TargetJsonSchema for class {}", schemaName);
                    }
                }
                KafkaJsonMessage kafkaJsonMessage = KafkaJsonMessage.newInstance("", schemaName);

                Map iEnumList = new HashMap();
                if (null != userConvertService) {
                    iEnumList = userConvertService.userConvert(clazz);
                }
                iEnumList.putAll(EasyAnnotationConverter.collectionConvert(clazz));
                for (Object value : source) {
                    if (IBeanUpsert.class.isAssignableFrom(clazz)) {
                        try {
                            ((IBeanUpsert) value).beforeObjectProcess();
                        } catch (ProcessException e) {
                            e.printStackTrace();
                            throw new UpsertException(e);
                        }
                    }
                    kafkaJsonMessage.setPayload(JsonFileConverter.parseBean2map(value, iEnumList));
                    easyUpsertExtractKafkaProducer.sendAsync(lazyTableAnnotation.kafkaCode(),
                            lazyTableAnnotation.kafkaTopicName(), kafkaJsonMessage);
                }

            }
        });
        return true;
    }

}