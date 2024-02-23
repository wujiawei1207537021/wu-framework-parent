package com.wu.framework.inner.redis;

public interface LazyRedisDBEnum extends LazyRedis {


    Integer getDb();

    String getMsg();
}
