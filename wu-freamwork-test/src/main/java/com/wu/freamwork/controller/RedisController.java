package com.wu.freamwork.controller;

import com.wu.framework.inner.layer.web.EasyController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * describe : redis 测试
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/12/14 9:45 下午
 */
@ConditionalOnBean(Jedis.class)
@EasyController("/redis")
public class RedisController {

    private final Jedis jedis;

    public RedisController(Jedis jedis) {
        this.jedis = jedis;
    }


    @PostMapping("/publish")
    public Long publish(@RequestParam("channel") String channel, @RequestParam("message") String message) {
        final Long publish =
                jedis.publish(channel, message);
        return publish;
    }

    @PostMapping("/subscribe")
    public void subscribe(@RequestParam("channel") String channel) {

        jedis.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println(channel);
                System.out.println(message);
                super.onMessage(channel, message);
            }
        }, channel);

    }
}
