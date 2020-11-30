package com.wu.framework.easy.stereotype.upsert.converter;


import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 基本数据类型转换
 * @date : 2020/11/30 下午21:47
 */
public class JavaBasicTypeConversion {


    public static Object toString(@NonNull Object o) {
        Class clazz = o.getClass();
        if (clazz.isAssignableFrom(LocalDateTime.class)) {
            LocalDateTime v = (LocalDateTime) o;
            return v.toString();
        }
        return o.toString();
    }
}
