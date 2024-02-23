package com.wu.framework.inner.layer.data.translation.api;

import com.wu.framework.inner.layer.data.acsII.AcsIIFactory;
import com.wu.framework.inner.layer.data.translation.TranslationField2AcsII;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * @see TranslationField2AcsII
 */
public class DefaultTranslationObject2AcsIIAPI implements TranslationAPI {
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
        return sourceValues.stream().collect(Collectors.toConcurrentMap(Object::toString, AcsIIFactory::acsII, (A, B) -> A));
    }
}
