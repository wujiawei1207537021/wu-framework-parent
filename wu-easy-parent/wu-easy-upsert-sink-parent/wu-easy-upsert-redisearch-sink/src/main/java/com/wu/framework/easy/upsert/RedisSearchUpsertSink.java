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
import io.redisearch.client.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2020/12/20 8:27 下午
 */
@Slf4j
@ConditionalOnProperty(prefix = "spring.redis.search", value = {"host"})
@EasyUpsertStrategy(value = EasyUpsertType.REDIS_SEARCH)
public class RedisSearchUpsertSink implements IEasyUpsert {

    private final LazyRedisTemplate lazyRedisTemplate;
    private final SpringUpsertAutoConfigure springUpsertAutoConfigure;
    private final Client client;

    public RedisSearchUpsertSink(LazyRedisTemplate lazyRedisTemplate, SpringUpsertAutoConfigure springUpsertAutoConfigure, Client client) {
        this.lazyRedisTemplate = lazyRedisTemplate;
        this.springUpsertAutoConfigure = springUpsertAutoConfigure;
        this.client = client;
    }


    @Override
    public <T> Object upsert(List<T> list, ClassSchema classSchema) throws UpsertException {
        try {
            splitListThen(list, springUpsertAutoConfigure.getBatchLimit(), new EasyUpsertFunction() {
                @Override
                public <T> void handle(List<T> source) {


                    Class<?> clazz = classSchema.clazz();
                    EasySmart easySmart = classSchema.classAnnotation(EasySmart.class);
                    String key = CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
                    if (!ObjectUtils.isEmpty(easySmart)) {
                        if (!ObjectUtils.isEmpty(easySmart.redisKey())) {
                            key = easySmart.redisKey();
                        }
                    }
                    for (T t : source) {
                        final Map<String, Object> fields =
                                Arrays.stream(t.getClass().getDeclaredFields()).collect(Collectors.toMap(Field::getName, field -> {
                                    try {
                                        return field.get(t);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                        return null;
                                    }
                                }));
                        client.addDocument(key, fields);
                    }


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new UpsertException(e);
        }
        return true;
    }


}
