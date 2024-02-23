package com.wu.freamwork.convert;

import com.wu.framework.inner.layer.data.dictionary.convert.LazyDictionaryConvert;
import org.springframework.stereotype.Component;

/**
 * description 用户ID解析成用户
 *
 * @author 吴佳伟
 * @date 2023/09/22 11:18
 */
@Component
public class UserIdConvert2UserAdapter implements LazyDictionaryConvert {
    /**
     * 字段转译
     *
     * @param source 原始数据
     */
    @Override
    public void transformation(Object source) {

    }

    /**
     * 支持
     *
     * @param source 原始数据
     * @return 布尔类型
     */
    @Override
    public boolean support(Object source) {
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
