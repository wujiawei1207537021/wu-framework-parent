package com.wu.framework.inner.layer.data.acsII;



/**
 * 数字串转换成 acsII
 */
public class NumberAcsII implements AcsII {

    /**
     * 当前数据是否支持转换成 acsII
     *
     * @param o 当前数据
     * @return 是否支持
     */
    @Override
    public boolean support(Object o) {
        return o != null && (Integer.class.isAssignableFrom(o.getClass()) || Long.class.isAssignableFrom(o.getClass()));
    }

    @Override
    public Long acsII(Object o) {
        Class<?> clazz = o.getClass();
        if (Integer.class.isAssignableFrom(clazz) || int.class.isAssignableFrom(clazz)) {
            return Long.valueOf((Integer) o);
        }
        if (Long.class.isAssignableFrom(clazz) || long.class.isAssignableFrom(clazz)) {
            return (Long) o;
        }
        return -1L;
    }
}
