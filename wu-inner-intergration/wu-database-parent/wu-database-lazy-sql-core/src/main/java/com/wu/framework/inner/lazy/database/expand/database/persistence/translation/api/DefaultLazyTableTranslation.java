package com.wu.framework.inner.lazy.database.expand.database.persistence.translation.api;

import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.endpoint.LazyTranslationTableEndpoint;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/10/04 15:48
 */
@Slf4j
public class DefaultLazyTableTranslation implements LazyTranslationAPI {
    /**
     * description  转换数据
     *
     * @param lazyTranslationTableEndpoint 原始数据
     * @return Map<String sourceValue, Object 目标数据>
     * @author Jia wei Wu
     * @date 2020/8/6 下午7:54
     */
    @Override
    public ConcurrentMap<String, Object> translation(LazyTranslationTableEndpoint lazyTranslationTableEndpoint) {
        log.info("转换数据");
        return new ConcurrentHashMap<>();
    }
}
