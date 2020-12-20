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
     * description 获取基本数据类型默认值
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/12/9 下午3:48
     */
    public static Object defaultBasicDataType(Object o) {
        if (!ObjectUtils.isEmpty(o)) {
            return o;
        }
        Class<?> aClass = o.getClass();
        if (Integer.class.isAssignableFrom(aClass)) {
            return 0;
        } else if (Double.class.isAssignableFrom(aClass)) {
            return 0.00;
        } else if (Float.class.isAssignableFrom(aClass)) {
            return 0f;
        } else if (Long.class.isAssignableFrom(aClass)) {
            return 0L;
        } else if (Short.class.isAssignableFrom(aClass)) {
            return 0;
        } else if (Byte.class.isAssignableFrom(aClass)) {
            return 0;
        } else if (BigDecimal.class.isAssignableFrom(aClass)) {
            return 0;
        }else return o;
    }
}
