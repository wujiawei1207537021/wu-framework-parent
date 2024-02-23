package com.wu.framework.inner.redis.dynamic;


import lombok.Data;

/**
 * 获取数据源名称
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/29 23:06
 */
@Data
public class LazyDynamicRedisEndpoint {

    /**
     * 获取数据源名称
     *
     * @return String 数据源名称
     */
    private String name;

    /**
     * 数据库
     *
     * @return
     */
    private Integer database;
}
