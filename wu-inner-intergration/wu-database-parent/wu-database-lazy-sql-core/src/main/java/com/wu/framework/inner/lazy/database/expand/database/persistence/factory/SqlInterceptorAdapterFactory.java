package com.wu.framework.inner.lazy.database.expand.database.persistence.factory;

import com.wu.framework.inner.lazy.database.expand.database.persistence.method.interceptor.DefaultSqlInterceptor;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.interceptor.SqlInterceptorAdapter;

import java.util.List;

public class SqlInterceptorAdapterFactory {

    /**
     * 创建默认的sql 拦截器 适配器
     *
     * @return sql 拦截器适配器
     */
    public static SqlInterceptorAdapter createDefaultSqlInterceptorAdapter() {
        DefaultSqlInterceptor defaultSqlInterceptor = new DefaultSqlInterceptor();
        return new SqlInterceptorAdapter(List.of(defaultSqlInterceptor));
    }
}
