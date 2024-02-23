package com.wu.framework.inner.layer.data.dictionary.convert;

import org.springframework.core.Ordered;

/**
 * description 智能转译抽象类 接口
 *
 * @author 吴佳伟
 * @date 2023/08/30 10:24
 */
public interface LazyDictionaryConvert extends Ordered {
    /**
     * 字段转译
     *
     * @param source 原始数据
     */
    void transformation(Object source);


    /**
     * 支持
     *
     * @param source 原始数据
     * @return 布尔类型
     */
    boolean support(Object source);


}
