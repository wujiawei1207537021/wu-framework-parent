package com.wu.framework.easy.stereotype.upsert.converter;


import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 基本数据类型转换
 * @date : 2020/11/30 下午21:47
 */
public class JavaBasicTypeConversion {


    public static Object toString(Object o) {
        if (null == o) {
            return o;
        }
        Class clazz = o.getClass();
        if (clazz.isAssignableFrom(LocalDateTime.class)) {
            LocalDateTime v = (LocalDateTime) o;
            return v.toString();
        }
        return o.toString();
    }

    /**
     * description 获取默认基本数据类型
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2020/12/9 下午3:48
     */
    public static Object defaultBasicDataType(Object o) {
        if (!ObjectUtils.isEmpty(o)) {
            return o;
        }
        Class<?> aClass = o.getClass();
        if (aClass.isAssignableFrom(Integer.class)) {
            return 0;
        } else if (aClass.isAssignableFrom(Double.class)) {
            return 0.00;
        } else if (aClass.isAssignableFrom(Float.class)) {
            return 0f;
        } else if (aClass.isAssignableFrom(Long.class)) {
            return 0L;
        } else if (aClass.isAssignableFrom(Short.class)) {
            return 0;
        } else if (aClass.isAssignableFrom(Byte.class)) {
            return 0;
        } else if (aClass.isAssignableFrom(BigDecimal.class)) {
            return 0;
        }else return o;
    }
}
