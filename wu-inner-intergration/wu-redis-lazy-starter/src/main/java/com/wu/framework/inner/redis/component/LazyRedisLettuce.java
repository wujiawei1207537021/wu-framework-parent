package com.wu.framework.inner.redis.component;


import io.lettuce.core.api.sync.RedisCommands;

/**
 * describe : Lettuce 二次封装
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/12/14 9:40 下午
 */
public interface LazyRedisLettuce {


    RedisCommands<String, String> sync();
}
