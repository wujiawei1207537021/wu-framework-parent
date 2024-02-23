package com.wu.framework.easy.upsert;

import com.alibaba.fastjson.JSON;
import com.wu.framework.easy.upsert.autoconfigure.EasySmart;
import com.wu.framework.easy.upsert.autoconfigure.config.SpringUpsertAutoConfigure;
import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.upsert.autoconfigure.enums.EasyUpsertType;
import com.wu.framework.easy.upsert.core.dynamic.IEasyUpsert;
import com.wu.framework.easy.upsert.core.dynamic.exception.UpsertException;
import com.wu.framework.easy.upsert.core.dynamic.function.EasyUpsertFunction;
import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.ClassSchema;
import com.wu.framework.inner.layer.data.FieldSchema;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.framework.inner.redis.component.LazyRedisTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2020/12/20 8:27 下午
 */
@Slf4j
@ConditionalOnProperty(prefix = "spring.data.redis", value = "host", matchIfMissing = true)
@EasyUpsertStrategy(value = EasyUpsertType.REDIS)
public class RedisUpsertSink implements IEasyUpsert {

    private final LazyRedisTemplate<String, String> lazyRedisTemplate;
    private final SpringUpsertAutoConfigure springUpsertAutoConfigure;

    public RedisUpsertSink(LazyRedisTemplate<String, String> lazyRedisTemplate, SpringUpsertAutoConfigure springUpsertAutoConfigure) {
        this.lazyRedisTemplate = lazyRedisTemplate;
        this.springUpsertAutoConfigure = springUpsertAutoConfigure;
    }


    @Override
    public <T> Object upsert(List<T> list, ClassSchema classSchema) throws UpsertException {
        try {
            splitListThen(list, springUpsertAutoConfigure.getBatchLimit(), new EasyUpsertFunction() {
                @Override
                public <T> void handle(List<T> source) {
                    Class<?> clazz = classSchema.clazz();
                    if (EasyHashMap.class.isAssignableFrom(clazz)) {
                        // Map 数据解析
                        log.warn("Map 数据解析 待完成");
//                        lazyRedisTemplate.opsForHash().putAll(key, tMap);

                    } else {
                        EasySmart easySmart = classSchema.classAnnotation(EasySmart.class);
                        String key = CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
                        if (!ObjectUtils.isEmpty(easySmart)) {
                            if (!ObjectUtils.isEmpty(easySmart.redisKey())) {
                                key = easySmart.redisKey();
                            }
                        }

                        // Hash 数据存储
                        final List<FieldSchema> uniqueFieldSchemaList = classSchema.fieldSchema().stream().filter(fieldSchema -> {
                            final LayerField layerField = fieldSchema.fieldAnnotation(LayerField.class);
                            if (ObjectUtils.isEmpty(layerField)) {
                                return false;
                            }
                            return LayerField.LayerFieldType.UNIQUE.equals(layerField.indexType());

                        }).collect(Collectors.toList());

                        final Map<String, String> tMap = source.stream().collect(Collectors.toMap(t -> {
                            if (ObjectUtils.isEmpty(uniqueFieldSchemaList)) {
                                return UUID.randomUUID().toString();
                            }
                            // 获取唯一索引数据
                            final String uniqueKey = uniqueFieldSchemaList.stream().map(fieldSchema -> {
                                try {
                                    final Field field = fieldSchema.field();
                                    field.setAccessible(true);
                                    final Object invoke = field.get(t);
                                    return null == invoke ? NormalUsedString.NULL : invoke.toString();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                                return NormalUsedString.NULL;
                            }).collect(Collectors.joining("_"));
                            return uniqueKey;
                        }, JSON::toJSONString, (A, B) -> B));
                        lazyRedisTemplate.opsForHash().putAll(key, tMap);
//                        String[] strings = source.stream().map(Object::toString).collect(Collectors.toList()).toArray(new String[source.size()]);
//                        lazyRedisTemplate.opsForSet().add(key, strings);
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
