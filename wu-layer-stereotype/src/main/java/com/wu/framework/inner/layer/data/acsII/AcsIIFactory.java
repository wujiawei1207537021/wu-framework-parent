package com.wu.framework.inner.layer.data.acsII;

import java.util.Arrays;
import java.util.List;

public class AcsIIFactory {

    protected static final List<AcsII> ACS_II_LIST = Arrays.asList(new StringAcsII(),new NumberAcsII());

    /**
     * 对象转换成acsII
     * @param o 对象
     * @return 转换后的acsII
     */
    public static Long acsII(Object o) {
        for (AcsII acsII : ACS_II_LIST) {
            if (acsII.support(o)) {
                return acsII.acsII(o);
            }
        }
        return -1L;
    }
}
