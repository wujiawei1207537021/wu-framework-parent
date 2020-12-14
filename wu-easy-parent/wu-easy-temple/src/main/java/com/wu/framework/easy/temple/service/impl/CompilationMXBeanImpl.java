package com.wu.framework.easy.temple.service.impl;

import com.wu.framework.easy.temple.service.TestMXBean;
import org.springframework.stereotype.Service;

import javax.management.ObjectName;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
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
