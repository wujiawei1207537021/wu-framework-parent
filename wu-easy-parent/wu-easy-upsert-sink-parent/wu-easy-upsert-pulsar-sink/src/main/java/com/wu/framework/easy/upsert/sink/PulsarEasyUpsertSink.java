package com.wu.framework.easy.upsert.sink;

import com.wu.framework.easy.upsert.PulsarSchema;
import com.wu.framework.easy.upsert.autoconfigure.config.SpringBootPulsarConfigProperties;
import com.wu.framework.easy.upsert.autoconfigure.config.SpringUpsertAutoConfigure;
import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.upsert.autoconfigure.enums.EasyUpsertType;
import com.wu.framework.easy.upsert.core.dynamic.IEasyUpsert;
import com.wu.framework.easy.upsert.core.dynamic.exception.UpsertException;
import com.wu.framework.easy.upsert.core.dynamic.function.EasyUpsertFunction;
import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.ClassSchema;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.Schema;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * description MySQL数据插入
 *
 * @author Jia wei Wu
 * @date 2020/9/11 上午10:22
 */
@Slf4j
@ConditionalOnProperty(prefix = SpringBootPulsarConfigProperties.PULSAR_PREFIX, value = "url")
@EasyUpsertStrategy(value = EasyUpsertType.PULSAR)
public class PulsarEasyUpsertSink implements IEasyUpsert, InitializingBean {

    private final PulsarClient pulsarClient;
    private final SpringUpsertAutoConfigure configure;

    public PulsarEasyUpsertSink(PulsarClient pulsarClient, SpringUpsertAutoConfigure configure) {
        this.pulsarClient = pulsarClient;
        this.configure = configure;
    }

    @Override
    public <T> Object upsert(List<T> list, ClassSchema classSchema) throws UpsertException, ExecutionException, InterruptedException {
        List<MessageId> messageIdList = new ArrayList<>();
        splitListThen(list, configure.getBatchLimit(), new EasyUpsertFunction() {
            @Override
            public <t> void handle(List<t> source) {
                PulsarSchema pulsarSchema = classSchema.classAnnotation(PulsarSchema.class);
                String topic = CamelAndUnderLineConverter.humpToLine2(classSchema.clazz().getSimpleName());
                if (!ObjectUtils.isEmpty(pulsarSchema)) {
                    if (!ObjectUtils.isEmpty(pulsarSchema.topic())) {
                        topic = pulsarSchema.topic();
                    }
                }
                for (t item : source) {
                    try {
                        MessageId messageId = pulsarClient.newProducer(Schema.JSON(classSchema.clazz())).
                                topic(topic).create().send(item);
                        messageIdList.add(messageId);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new UpsertException(e);
                    }
                }
            }
        });

        return messageIdList;
    }
}
