package com.wu.framework.easy.redis.listener.ack;

import com.wu.framework.easy.listener.core.support.Acknowledgment;
import com.wu.framework.easy.redis.listener.consumer.RedisConsumerRecord;
import redis.clients.jedis.Jedis;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/12/14 9:32 下午
 */
public class RedisAcknowledgment implements Acknowledgment {


    private final Object record;

    public RedisAcknowledgment(Object record) {
        this.record = record;
    }


    @Override
    public void acknowledge() {

    }
}
