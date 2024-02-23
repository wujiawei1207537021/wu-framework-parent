package com.wu.smart.acw.server.translation.api;

import com.wu.framework.inner.layer.data.translation.api.TranslationAPI;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.smart.acw.core.domain.uo.AcwInstanceUo;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * description 实例ID转换成实例名称
 *
 * @author 吴佳伟
 * @date 2023/09/25 15:29
 */
@Component
public class InstanceIdTranslation2NameAPI implements TranslationAPI {
    private final LazyLambdaStream lazyLambdaStream;

    public InstanceIdTranslation2NameAPI(LazyLambdaStream lazyLambdaStream) {
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
        // 根据地址ID 获取地址名称
        return lazyLambdaStream.selectList(LazyWrappers.<AcwInstanceUo>lambdaWrapper()
                        .in(AcwInstanceUo::getId, sourceValues))
                .stream()
                .collect(Collectors.toConcurrentMap(AcwInstanceUo::getId, AcwInstanceUo::getInstanceName));
    }
}
