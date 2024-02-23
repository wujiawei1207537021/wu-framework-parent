package com.wu.framework.inner.layer.data.translation.api;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * description 转译 API
 *
 * @author 吴佳伟
 * @date 2023/09/22 13:32
 */
public interface TranslationAPI {

    /**
     * description  转换数据
     *
     * @param sourceValues 原始数据
     * @return Map<String sourceValue, Object 目标数据>
     * @author Jia wei Wu
     * @date 2020/8/6 下午7:54
     */
    ConcurrentMap<String/*sourceValue**/, Object/*目标数据**/> translation(Set<Object> sourceValues);
}
