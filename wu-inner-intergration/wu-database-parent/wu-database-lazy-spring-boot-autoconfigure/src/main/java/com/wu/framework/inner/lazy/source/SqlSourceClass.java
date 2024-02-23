package com.wu.framework.inner.lazy.source;

import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.source.adapter.SourceFactory;
import org.springframework.util.Assert;

/**
 *  sql 原始class
 */
public class SqlSourceClass {

    /**
     * class
     */
    private final Class<?> clazz;

    private SqlSourceClass(Class<?> clazz) {
        Assert.notNull(clazz,"原始class不能为空");
        this.clazz = clazz;
    }

    public static SqlSourceClass getInstance(Class<?> clazz) {
        return new SqlSourceClass(clazz);
    }

    /**
     * 获取当前class 对应的表结构
     * @return 表结构信息
     */
    public LazyTableEndpoint getLazyTableEndpoint() {
        // 通过class 获取对应的表结构
        return SourceFactory.defaultAnalyzeLazyTableFromClass(this.clazz);

    }
}
