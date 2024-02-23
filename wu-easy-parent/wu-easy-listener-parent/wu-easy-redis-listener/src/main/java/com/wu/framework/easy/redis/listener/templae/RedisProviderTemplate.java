package com.wu.framework.easy.redis.listener.templae;

import com.wu.framework.easy.listener.core.ProviderTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import redis.clients.jedis.Jedis;

/**
 * describe :
 *
 * @author : 吴佳伟
 * @version 1.0
 * @date : 2021/12/16 9:04 下午
 */
@ConditionalOnProperty(prefix = "spring.redis", value = "host")
@ConditionalOnMissingBean(value = RedisProviderTemplate.class)
public class RedisProviderTemplate<T> implements ProviderTemplate<T, Long> {

    private final Jedis jedis;

    public RedisProviderTemplate(Jedis jedis) {
        this.jedis = jedis;
    }


    /**
     * 数据发送
     *
     * @param channel 发送的主题、通道
     * @param message 发送的数据
     * @return Long 消息订阅者数量
     */
    @Override
    public <T> Long send(String channel, T message) {
        final Long publish = jedis.publish(channel, message.toString());
        return publish;
    }
}
