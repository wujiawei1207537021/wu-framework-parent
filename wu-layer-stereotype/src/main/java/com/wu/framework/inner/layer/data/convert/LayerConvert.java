package com.wu.framework.inner.layer.data.convert;

/**
 * @author Jia wei Wu
 */
public interface LayerConvert {
    /**
     * 支持
     *
     * @param fieldType 字段类型
     * @return
     */
    boolean support(Class<?> fieldType);
}
