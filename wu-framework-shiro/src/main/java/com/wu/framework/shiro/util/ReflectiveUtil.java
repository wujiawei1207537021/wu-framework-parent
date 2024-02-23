package com.wu.framework.shiro.util;

import com.wu.framework.response.exceptions.ShiroException;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;

/**
 * 反射工具
 */
public class ReflectiveUtil {

    /**
     * 获取对象属性
     *
     * @param o
     * @param attributeName
     * @return
     */
    public static Object getValByAttributeName(Object o, String attributeName) {
        if (ObjectUtils.isEmpty(o)) {
            throw new ShiroException("反射获取属性异常");
        }
        Class clazz = o.getClass();
        for (Field declaredField : clazz.getDeclaredFields()) {
            if (declaredField.getName().equals(attributeName)) {
                declaredField.setAccessible(true);
                try {
                    return declaredField.get(o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new ShiroException("反射获取属性失败 attributeName" + attributeName);
    }

    public static Object getBaseClassValByAttributeName(Object o, String attributeName) {
        if (o.getClass().isAssignableFrom(String.class)) {
            String bo = (String) o;
            return bo;
        }
        if (o.getClass().isAssignableFrom(Integer.class)) {
            Integer bo = (Integer) o;
            return bo;
        }
        if (o.getClass().isAssignableFrom(Long.class)) {
            Long bo = (Long) o;
            return bo;
        }
        if (o.getClass().isAssignableFrom(Boolean.class)) {
            Boolean bo = (Boolean) o;
            return bo;
        }
        return getValByAttributeName(o, attributeName);
    }
}
