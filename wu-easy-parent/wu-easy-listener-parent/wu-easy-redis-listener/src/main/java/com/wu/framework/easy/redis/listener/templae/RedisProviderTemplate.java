package com.wu.framework.easy.redis.listener.templae;

import com.alibaba.fastjson.JSONObject;
import com.wu.framework.easy.listener.core.ProviderTemplate;
import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import redis.clients.jedis.Jedis;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/12/16 9:04 下午
 */
@ConditionalOnProperty(prefix = "spring.data.redis", value = "host")
@ConditionalOnMissingBean(value = RedisProviderTemplate.class)
public class RedisProviderTemplate implements ProviderTemplate<Object, Boolean> {

    private final StatefulRedisPubSubConnection<String, String> statefulRedisPubSubConnection;

    public RedisProviderTemplate(StatefulRedisPubSubConnection<String, String> statefulRedisPubSubConnection) {
        this.statefulRedisPubSubConnection = statefulRedisPubSubConnection;
    }


    /**
     * 数据发送
     *
     * @param channel 发送的主题、通道
     * @param message 发送的数据
     * @return Long 消息订阅者数量
     */
    @Override
    public Boolean send(String channel, Object message) {

        try {
            RedisPubSubAsyncCommands<String, String> async = statefulRedisPubSubConnection.async();
            async.publish(channel, JSONObject.toJSONString(message));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
