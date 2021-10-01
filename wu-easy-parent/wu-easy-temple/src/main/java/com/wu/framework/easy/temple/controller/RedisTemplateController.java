package com.wu.framework.easy.temple.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.redis.annotation.LazyRedis;
import com.wu.framework.inner.redis.component.LazyRedisTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : Jiawei Wu
 * @version 1.0
 * describe : Redis数据插入
 * @date : 2020/12/27 7:07 下午
 */
@Api(tags = "Redis数据插入")
@EasyController("/redis/template")
public class RedisTemplateController {

    private final RedisTemplate redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    private final LazyRedisTemplate lazyRedisTemplate;

    public RedisTemplateController(RedisTemplate redisTemplate, StringRedisTemplate stringRedisTemplate, LazyRedisTemplate lazyRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
        this.lazyRedisTemplate = lazyRedisTemplate;
    }

    @ApiOperation(tags = "Redis数据插入", value = "Redis数据插入")
    @GetMapping()
    public Long upsert() {
        Set<ZSetOperations.TypedTuple<String>> set = new HashSet();
        for (int i = 0; i < 10; i++) {
            DefaultTypedTuple defaultTypedTuple = new DefaultTypedTuple("第" + i + "个", 0.00);
            set.add(defaultTypedTuple);
        }
        final Long opsForZSet = stringRedisTemplate.opsForZSet().add("opsForZSet", set);
        final Set opsForZSet1 = stringRedisTemplate.opsForZSet().range("opsForZSet", 1, 1000);

        return opsForZSet;
    }


    /***
     * 测试自定义一redis
     */
    @LazyRedis(database = 1)
    @GetMapping("/set1")
    public Object setRedis() {
        lazyRedisTemplate.opsForValue().set("test1", "test1");
        return lazyRedisTemplate.opsForValue().get("test1");
    }

    /***
     * 测试自定义redis 切换数据库
     */
    @LazyRedis(database = 2)
    @GetMapping("/set2")
    public Object setRedis2() {
        lazyRedisTemplate.opsForValue().set("test2", "test2");
        return lazyRedisTemplate.opsForValue().get("test1");
    }


}
