package com.wu.framework.easy.stereotype.upsert.component;

import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.stereotype.upsert.IEasyUpsert;
import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.easy.stereotype.upsert.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
import com.wu.framework.inner.redis.component.LazyRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/12/20 8:27 下午
 */
@Slf4j
@ConditionalOnProperty(prefix = "spring.redis", value = "host")
@EasyUpsertStrategy(value = EasyUpsertType.REDIS)
public class RedisUpsert implements IEasyUpsert {

    private final LazyRedisTemplate lazyRedisTemplate;

    public RedisUpsert(LazyRedisTemplate lazyRedisTemplate) {
        this.lazyRedisTemplate = lazyRedisTemplate;
    }


    @Override
    public <T> Object upsert(List<T> list) {
        Class<?> clazz = list.get(0).getClass();
        EasySmart easySmart = AnnotatedElementUtils.findMergedAnnotation(clazz, EasySmart.class);
        String key = easySmart.redisKey();
        if (ObjectUtils.isEmpty(key)) key = CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
        String[] strings = list.stream().map(Object::toString).collect(Collectors.toList()).toArray(new String[list.size()]);
        Long add = lazyRedisTemplate.opsForSet().add(key, strings);
        return add;
    }
}
