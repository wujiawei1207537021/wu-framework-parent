package com.wu.smart.acw.server.translation.api;

import com.wu.framework.inner.layer.data.translation.api.TranslationAPI;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * description schema 解析内存api调用
 *
 * @author 吴佳伟
 * @date 2023/09/25 15:29
 */
@Component
public class SchemaTranslationMemoryAPI implements TranslationAPI {
    private final LazyLambdaStream lazyLambdaStream;

    public SchemaTranslationMemoryAPI(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;
    }

    /**
     * description  转换数据
     *
     * @param sourceValuesSet 原始数据
     * @return Map<String sourceValue, Object 目标数据>
     * @author Jia wei Wu
     * @date 2020/8/6 下午7:54
     */
    @Override
    public ConcurrentMap<String, Object> translation(Set<Object> sourceValuesSet) {
        // 获取当前数据库信息

        return lazyLambdaStream.selectList(LazyWrappers.<LazyTableInfo>lambdaWrapper()
                        .in(LazyTableInfo::getTableSchema, sourceValuesSet))
                .stream()
                .collect(Collectors.toConcurrentMap(LazyTableInfo::getTableSchema, Function.identity()));
    }
}
