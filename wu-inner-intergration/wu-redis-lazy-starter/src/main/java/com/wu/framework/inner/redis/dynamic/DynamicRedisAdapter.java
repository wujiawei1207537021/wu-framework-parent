package com.wu.framework.inner.redis.dynamic;


import com.wu.framework.inner.redis.config.LazyRedisProperties;
import io.lettuce.core.RedisClient;


/**
 * 动态数据源 适配器
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/29 20:10
 */
public interface DynamicRedisAdapter {

    /**
     * describe 确定数据源
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2021/7/4 6:19 下午
     **/
    RedisClient determineRedisClient();

    /**
     * 数据源载入
     *
     * @param sourceProperties 数据源参数
     */
    void loading(LazyRedisProperties sourceProperties);

}
