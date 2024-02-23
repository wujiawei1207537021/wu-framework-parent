package com.wu.framework.inner.layer.data.acsII;

/**
 * 对象转换成 acsII
 */
public interface AcsII {

    /**
     * 当前数据是否支持转换成 acsII
     * @param o 当前数据
     * @return 是否支持
     */
    boolean support(Object o);

    /**
     * 当前数据转换成 acsII
     *
     * @param o 当前数据
     * @return acsII
     */
    Long acsII(Object o);
}
