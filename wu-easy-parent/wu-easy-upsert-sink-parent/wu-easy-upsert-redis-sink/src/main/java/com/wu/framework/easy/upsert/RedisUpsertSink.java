package com.wu.framework.easy.upsert;

import com.wu.framework.easy.upsert.autoconfigure.EasySmart;
import com.wu.framework.easy.upsert.autoconfigure.config.SpringUpsertAutoConfigure;
import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.upsert.autoconfigure.enums.EasyUpsertType;
import com.wu.framework.easy.upsert.core.dynamic.IEasyUpsert;
import com.wu.framework.easy.upsert.core.dynamic.exception.UpsertException;
import com.wu.framework.easy.upsert.core.dynamic.function.EasyUpsertFunction;
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
    private final SpringUpsertAutoConfigure springUpsertAutoConfigure;

    public RedisUpsertSink(LazyRedisTemplate lazyRedisTemplate, SpringUpsertAutoConfigure springUpsertAutoConfigure) {
        this.lazyRedisTemplate = lazyRedisTemplate;
        this.springUpsertAutoConfigure = springUpsertAutoConfigure;
    }


    @Override
    public <T> Object upsert(List<T> list, ClassSchema classSchema) throws UpsertException {
        try {
            splitListThen(list, springUpsertAutoConfigure.getBatchLimit(), new EasyUpsertFunction() {
                @Override
                public <T> void handle(List<T> source) {
                    Class<?> clazz = source.get(0).getClass();
                    EasySmart easySmart = AnnotatedElementUtils.findMergedAnnotation(clazz, EasySmart.class);
                    String key = CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
                    if (!ObjectUtils.isEmpty(easySmart)) {
                        if (!ObjectUtils.isEmpty(easySmart.redisKey())) {
                            key = easySmart.redisKey();
                        }
                    }
                    String[] strings = source.stream().map(Object::toString).collect(Collectors.toList()).toArray(new String[source.size()]);
                    lazyRedisTemplate.opsForSet().add(key, strings);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
