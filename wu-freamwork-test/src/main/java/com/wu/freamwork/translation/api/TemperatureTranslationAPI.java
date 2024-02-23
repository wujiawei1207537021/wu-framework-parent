package com.wu.freamwork.translation.api;

import com.wu.framework.inner.layer.data.translation.api.TranslationAPI;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * description 温度转换
 *
 * @author 吴佳伟
 * @date 2023/09/26 15:29
 */
@Component
public class TemperatureTranslationAPI implements TranslationAPI {
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
        // value 对应的ID
        return sourceValues.stream().collect(Collectors.toConcurrentMap(Object::toString, o -> o + "摄氏度", (A, B) -> A));
    }
}
