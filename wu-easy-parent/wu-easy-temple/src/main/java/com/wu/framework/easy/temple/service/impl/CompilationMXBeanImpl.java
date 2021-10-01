package com.wu.framework.easy.temple.service.impl;

import com.wu.framework.easy.temple.service.TestMXBean;

import javax.management.ObjectName;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2020/11/7 下午10:04
 */

//@Service
public class CompilationMXBeanImpl implements TestMXBean {

    /**
     * Returns an {@link ObjectName ObjectName} instance representing
     * the object tableName of this platform managed object.
     *
     * @return an {@link ObjectName ObjectName} instance representing
     * the object tableName of this platform managed object.
     */
    @Override
    public ObjectName getObjectName() {
        return null;
    }
}
