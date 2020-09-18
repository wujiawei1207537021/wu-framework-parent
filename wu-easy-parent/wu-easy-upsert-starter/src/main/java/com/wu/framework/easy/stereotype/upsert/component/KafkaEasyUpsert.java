package com.wu.framework.easy.stereotype.upsert.component;


import com.google.common.collect.Maps;
import com.wu.framework.easy.stereotype.upsert.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
import com.wu.framework.easy.stereotype.upsert.ienum.UserDictionaryService;
import com.wu.framework.easy.stereotype.upsert.IEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.config.UpsertConfig;
import com.wu.framework.easy.stereotype.upsert.converter.ConverterClass2KafkaSchema;
import com.wu.framework.easy.stereotype.upsert.converter.CustomAnnotationConverter;
import com.wu.framework.easy.stereotype.upsert.converter.JsonFileConverter;
import com.wu.framework.easy.stereotype.upsert.entity.kafka.KafkaJsonMessage;
import com.wu.framework.easy.stereotype.upsert.entity.kafka.TargetJsonSchema;
import com.wu.framework.easy.stereotype.upsert.entity.stereotye.CustomTableAnnotation;
import com.wu.framework.easy.stereotype.upsert.entity.stereotye.LocalStorageClassAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * description kafka数据插入
 *
 * @author 吴佳伟
 * @date 2020/9/11 上午10:22
 */
@Slf4j
@ConditionalOnBean(EasyUpsertExtractKafkaProducer.class)
@EasyUpsertStrategy(value = EasyUpsertType.KAFKA)
public class KafkaEasyUpsert implements IEasyUpsert, InitializingBean {

    private final UserDictionaryService userDictionaryService;
    private final UpsertConfig upsertConfig;
    private final EasyUpsertExtractKafkaProducer easyUpsertExtractKafkaProducer;

    public KafkaEasyUpsert(UserDictionaryService userDictionaryService, UpsertConfig upsertConfig, EasyUpsertExtractKafkaProducer easyUpsertExtractKafkaProducer) {
        this.userDictionaryService = userDictionaryService;
        this.upsertConfig = upsertConfig;
        this.easyUpsertExtractKafkaProducer = easyUpsertExtractKafkaProducer;
    }

    @Override
    public <T> Object upsert(List<T> list) throws Exception {
        String threadName = Thread.currentThread().getName();
        Future task = easyUpsertExecutor.submit(() -> {
            Thread.currentThread().setName(threadName);
            Class clazz = list.get(0).getClass();
            // 模块名称+业务+表名
            CustomTableAnnotation customTableAnnotation = LocalStorageClassAnnotation.getCustomTableAnnotationAttr(clazz, upsertConfig.isForceDuplicateNameSwitch());
            String schemaName = customTableAnnotation.getKafkaSchemaName();

            TargetJsonSchema targetJsonSchema = KafkaJsonMessage.targetSchemaMap.get(schemaName);
            if (targetJsonSchema == null) {
                targetJsonSchema = ConverterClass2KafkaSchema.converterClass2TargetJsonSchema(clazz, upsertConfig.isForceDuplicateNameSwitch());
                upsertConfig.getSchema().add(targetJsonSchema);
                KafkaJsonMessage.targetSchemaMap = Maps.uniqueIndex(upsertConfig.getSchema(), TargetJsonSchema::getName);
                log.info(" Automatic loading TargetJsonSchema for class {}", schemaName);
            }
            KafkaJsonMessage kafkaJsonMessage = KafkaJsonMessage.newInstance("", schemaName);

            Map iEnumList = new HashMap();
            if (null != userDictionaryService) {
                iEnumList = userDictionaryService.userDictionary(list.get(0).getClass());
            }
            iEnumList.putAll(CustomAnnotationConverter.collectionDictionary(clazz));
            for (Object value : list) {
                kafkaJsonMessage.setPayload(JsonFileConverter.parseBean2map(value, iEnumList));
                easyUpsertExtractKafkaProducer.sendAsync(customTableAnnotation.getKafkaCode(), customTableAnnotation.getKafkaTopicName(), kafkaJsonMessage);
            }
            return true;
        });
        return task.get();
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
