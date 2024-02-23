package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.support.toolkit;

import java.lang.reflect.AccessibleObject;
import java.security.PrivilegedAction;

/**
 * @author Jia wei Wu
 */
public class SetAccessibleAction<T extends AccessibleObject> implements PrivilegedAction<T> {
    private final T obj;

    public SetAccessibleAction(T obj) {
        this.obj = obj;
    }

    @Override
    public T run() {
        obj.setAccessible(true);
        return obj;
    }

}
