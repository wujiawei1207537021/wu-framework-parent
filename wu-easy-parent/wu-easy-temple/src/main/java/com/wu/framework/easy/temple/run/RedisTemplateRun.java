package com.wu.framework.easy.temple.run;

import com.wu.framework.easy.stereotype.web.EasyController;
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
 * @author : 吴佳伟
 * @version 1.0
 * @describe : Redis数据插入
 * @date : 2020/12/27 7:07 下午
 */
@Api(tags = "Redis数据插入")
@EasyController("/redis")
public class RedisTemplateRun {
    private final RedisTemplate redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    public RedisTemplateRun(RedisTemplate redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @ApiOperation(tags = "Redis数据插入",value = "Redis数据插入")
    @GetMapping()
    public Long upsert(){
        Set<ZSetOperations.TypedTuple<String>> set=new HashSet();
        for (int i = 0; i < 10; i++) {
            DefaultTypedTuple defaultTypedTuple=new DefaultTypedTuple("第"+i+"个",0.00);
            set.add(defaultTypedTuple);
        }
        final Long opsForZSet = stringRedisTemplate.opsForZSet().add("opsForZSet", set);
        final Set opsForZSet1 = stringRedisTemplate.opsForZSet().range("opsForZSet", 1, 1000);

        return opsForZSet;
    }
}
