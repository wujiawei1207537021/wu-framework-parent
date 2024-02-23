package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.support.toolkit;


import java.lang.reflect.AccessibleObject;
import java.security.AccessController;

/**
 * @author wujiawei
 */
public class ReflectionKit {

    /**
     * 设置可访问对象的可访问权限为 true
     *
     * @param object 可访问的对象
     * @param <T>    类型
     * @return 返回设置后的对象
     */
    public static <T extends AccessibleObject> T setAccessible(T object) {
        return AccessController.doPrivileged(new SetAccessibleAction<>(object));
    }
}
