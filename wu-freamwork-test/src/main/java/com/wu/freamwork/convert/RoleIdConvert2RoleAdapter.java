package com.wu.freamwork.convert;

import com.wu.framework.inner.layer.data.translation.api.SourceValues;
import com.wu.framework.inner.layer.data.translation.api.TranslationAPI;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/09/26 15:35
 */
@Component
public class RoleIdConvert2RoleAdapter implements TranslationAPI {
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
        return null;
    }
}
