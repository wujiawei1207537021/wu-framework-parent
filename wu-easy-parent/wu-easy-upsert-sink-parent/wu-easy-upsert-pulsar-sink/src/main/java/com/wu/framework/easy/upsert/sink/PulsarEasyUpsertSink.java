package com.wu.framework.easy.upsert.sink;

import com.wu.framework.easy.upsert.PulsarSchema;
import com.wu.framework.easy.upsert.autoconfigure.config.SpringBootPulsarConfigProperties;
import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.upsert.autoconfigure.enums.EasyUpsertType;
import com.wu.framework.easy.upsert.core.dynamic.IEasyUpsert;
import com.wu.framework.easy.upsert.core.dynamic.exception.UpsertException;
import com.wu.framework.inner.layer.data.ClassSchema;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import java.util.ArrayList;
import java.util.List;

/**
 * description MySQL数据插入
 *
 * @author Jia wei Wu
 * @date 2020/9/11 上午10:22
 */
@Slf4j
@ConditionalOnBean(SpringBootPulsarConfigProperties.class)
@EasyUpsertStrategy(value = EasyUpsertType.PULSAR)
public class PulsarEasyUpsertSink implements IEasyUpsert, InitializingBean {

    private final PulsarClient pulsarClient;

    public PulsarEasyUpsertSink(PulsarClient pulsarClient) {
        this.pulsarClient = pulsarClient;
    }

    @SneakyThrows
    @Override
    public <T> Object upsert(List<T> list, ClassSchema classSchema) throws UpsertException {
        PulsarSchema pulsarSchema = classSchema.classAnnotation(PulsarSchema.class);
        List<MessageId> messageIdList=new ArrayList<>();
        for (T item : list) {
            MessageId messageId = pulsarClient.newProducer(JSONSchema.of(classSchema.clazz())).
                    topic(pulsarSchema.topic()).create().send(item);
            messageIdList.add(messageId);
        }
        return messageIdList;
    }
}
