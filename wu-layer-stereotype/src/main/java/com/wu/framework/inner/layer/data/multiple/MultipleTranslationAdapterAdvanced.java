package com.wu.framework.inner.layer.data.multiple;

/**
 * 广义的、、多种多样、网格 转译
 */
public interface MultipleTranslationAdapterAdvanced {

    /**
     * 转译
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
