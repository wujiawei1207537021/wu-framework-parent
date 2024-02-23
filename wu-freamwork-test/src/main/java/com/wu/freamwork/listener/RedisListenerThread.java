package com.wu.freamwork.listener;


import com.wu.framework.easy.listener.core.consumer.ConsumerRecord;
import com.wu.framework.easy.listener.core.support.Acknowledgment;
import com.wu.framework.easy.listener.stereotype.EasyListener;
import com.wu.framework.easy.listener.stereotype.redis.EasyRedisListener;
import org.springframework.stereotype.Component;

@Component
public class RedisListenerThread {


    /**
     * 使用redis监听注解监听数据
     *
     * @param consumerRecord
     */
    @EasyRedisListener(topics = "channel", concurrency = "2")
    public void subscription(ConsumerRecord consumerRecord, Acknowledgment acknowledgment) {
        System.out.println(consumerRecord);
        acknowledgment.acknowledge();
    }

    /**
     * 使用通用监听注解监听
     *
     * @param consumerRecord
     */
    @EasyListener(topics = "channel", concurrency = "2")
    public void subscriptionListener(ConsumerRecord consumerRecord) {
        System.out.println(consumerRecord);
    }

}
