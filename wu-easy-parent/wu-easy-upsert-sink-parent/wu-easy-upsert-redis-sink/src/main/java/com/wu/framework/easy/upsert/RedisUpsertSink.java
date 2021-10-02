package com.wu.framework.easy.upsert;

import com.wu.framework.easy.upsert.autoconfigure.EasySmart;
import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.upsert.autoconfigure.enums.EasyUpsertType;
import com.wu.framework.easy.upsert.core.dynamic.IEasyUpsert;
import com.wu.framework.easy.upsert.core.dynamic.exception.UpsertException;
import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.ClassSchema;
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
 * describe :
 * @date : 2020/12/20 8:27 下午
 */
@Slf4j
@ConditionalOnProperty(prefix = "spring.redis", value = "host")
@EasyUpsertStrategy(value = EasyUpsertType.REDIS)
public class RedisUpsertSink implements IEasyUpsert {

    private final LazyRedisTemplate lazyRedisTemplate;

    public RedisUpsertSink(LazyRedisTemplate lazyRedisTemplate) {
        this.lazyRedisTemplate = lazyRedisTemplate;
    }


    @Override
    public <T> Object upsert(List<T> list, ClassSchema classSchema) throws UpsertException {
        Class<?> clazz = list.get(0).getClass();
        EasySmart easySmart = AnnotatedElementUtils.findMergedAnnotation(clazz, EasySmart.class);
        String key;
        if (ObjectUtils.isEmpty(easySmart)) {
            key = CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
        } else {
            key = easySmart.redisKey();
        }
        String[] strings = list.stream().map(Object::toString).collect(Collectors.toList()).toArray(new String[list.size()]);
        return lazyRedisTemplate.opsForSet().add(key, strings);
    }
}
