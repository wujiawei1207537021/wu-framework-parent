package com.wu.freamwork.translation.api;

import com.wu.framework.inner.layer.data.translation.api.TranslationAPI;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.freamwork.translation.domain.UserRoleTranslationAdvancedDO;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * description 用户角色转换
 *
 * @author 吴佳伟
 * @date 2023/09/25 15:29
 */
@Component
public class UserRoleTranslationAPI implements TranslationAPI {
    private final LazyLambdaStream lazyLambdaStream;

    public UserRoleTranslationAPI(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;
    }

    /**
     * description  转换数据
     *
     * @param sourceValues 原始数据
     * @return Map<String sourceValue, Object 目标数据>
     * @author Jia wei Wu
     * @date 2020/8/6 下午7:54
     */
    @Override
    public ConcurrentMap<String, Object> translation(Set<Object> sourceValues) {
        // 根据角色ID 获取角色名称
        return lazyLambdaStream.selectList(LazyWrappers.<UserRoleTranslationAdvancedDO>lambdaWrapper()
                        .in(UserRoleTranslationAdvancedDO::getId, sourceValues))
                .stream()
                .collect(Collectors.toConcurrentMap(o -> o.getId().toString(), Function.identity()));
    }
}
