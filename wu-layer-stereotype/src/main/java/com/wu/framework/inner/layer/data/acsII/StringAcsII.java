package com.wu.framework.inner.layer.data.acsII;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字符串转换成 acsII
 */
public class StringAcsII implements AcsII {

    /**
     * 当前数据是否支持转换成 acsII
     *
     * @param o 当前数据
     * @return 是否支持
     */
    @Override
    public boolean support(Object o) {
        return o != null && String.class.isAssignableFrom(o.getClass())&& !o.toString().isEmpty();
    }

    @Override
    public Long acsII(Object o) {
        List<Integer> acsIIList=new ArrayList<>();
        for (int i = 0; i < o.toString().length(); i++) {
            char c = o.toString().charAt(i);
            acsIIList.add((int) c);
        }
        return Long.valueOf(acsIIList.stream().map(Object::toString).collect(Collectors.joining()));
    }
}
