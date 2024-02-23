package com.wu.framework.inner.layer.data.convert;

/**
 * @author wujiawei
 */
public interface LayerConvert<T> {
    /**
     * 支持
     *
     * @param valueClass 字段类型
     * @return
     */
    boolean support(Class<T> valueClass);
}
