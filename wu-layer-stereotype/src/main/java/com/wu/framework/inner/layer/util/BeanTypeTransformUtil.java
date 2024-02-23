package com.wu.framework.inner.layer.util;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * describe : 对象类型转换
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/6/9 22:31
 */
public class BeanTypeTransformUtil {

    /**
     * describe 将字符串转换成指定类型数据
     *
     * @param
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/6/9 22:38
     **/
    public static <T> T transform(String text, Class<T> clazz) {
        if (clazz.isAssignableFrom(String.class)) {
            return (T) text;
        } else if (clazz.isAssignableFrom(Double.class) | clazz.isAssignableFrom(double.class)) {
            return (T) Double.valueOf(text);
        } else if (clazz.isAssignableFrom(Integer.class) | clazz.isAssignableFrom(int.class)) {
            return (T) Integer.valueOf(text);
        } else if (clazz.isAssignableFrom(Long.class) | clazz.isAssignableFrom(long.class)) {
            return (T) Long.valueOf(text);
        } else if (clazz.isAssignableFrom(Float.class) | clazz.isAssignableFrom(float.class)) {
            return (T) Float.valueOf(text);
        } else if (clazz.isAssignableFrom(Date.class)) {
            return (T) new Date(text);
        } else if (clazz.isAssignableFrom(Calendar.class)) {
        } else if (clazz.isAssignableFrom(Boolean.class) | clazz.isAssignableFrom(boolean.class)) {
            return (T) Boolean.valueOf(text);
        } else if (clazz.isAssignableFrom(LocalDateTime.class)) {
            return (T) LocalDateTime.parse(text);
        }
        return null;
    }
}
