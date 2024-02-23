package com.wu.framework.easy.pulsar;

import com.wu.framework.easy.listener.core.ProviderTemplate;
import com.wu.framework.easy.pulsar.config.PulsarConfigProperties;
import org.apache.pulsar.client.api.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@ConditionalOnMissingBean(value = PulsarProviderTemplate.class)
@ConditionalOnProperty(prefix = PulsarConfigProperties.PULSAR_PREFIX, value = "url")
public class PulsarProviderTemplate<T> implements ProviderTemplate<T, MessageId> {

    private final PulsarClient pulsarClient;
    private final ConcurrentHashMap<String, Producer> topicProducer = new ConcurrentHashMap();

    public PulsarProviderTemplate(PulsarClient pulsarClient) {
        this.pulsarClient = pulsarClient;
    }

    /**
     * 数据发送
     *
     * @param topic   发送的主题、通道
     * @param message 发送的数据
     * @return ID 返回数据
     */
    @Override
    public <T> MessageId send(String topic, T message) {
        Class<T> clazz = (Class<T>) message.getClass();
        Producer producer = getProducer(topic, clazz);
        if (Collection.class.isAssignableFrom(clazz)) {
            try {
                return producer.send(message.toString().getBytes());
            } catch (PulsarClientException e) {
                e.printStackTrace();
            }
        } else {
            try {
                return producer.send(message);
            } catch (PulsarClientException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @param
     * @return description 发送字符串
     * @exception/throws
     */
    public MessageId sendBytes(String topic, String message) throws PulsarClientException {
        return pulsarClient.newProducer().topic(topic).create().send(message.getBytes(StandardCharsets.UTF_8));
    }


    /**
     * description 获取主题对应的Producer
     *
     * @param
     * @return
     * @exception/throws
     * @date 2021/9/8 4:20 下午
     */
    private Producer getProducer(String topic, Class clazz) {
        if (topicProducer.containsKey(topic)) {
            return topicProducer.get(topic);
        }
        Producer producer = null;
        if (Collection.class.isAssignableFrom(clazz)) {
            try {
                producer = pulsarClient.newProducer().topic(topic).create();
            } catch (PulsarClientException e) {
                e.printStackTrace();
            }
        } else {
            try {
                producer = pulsarClient.newProducer(Schema.JSON(clazz)).topic(topic).create();
            } catch (PulsarClientException e) {
                e.printStackTrace();
            }
        }
        Assert.notNull(producer, "producer can not null ");
        topicProducer.put(topic, producer);
        return topicProducer.get(topic);
    }


}
