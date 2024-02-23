package com.wu.framework.inner.lazy.database.expand.database.persistence.method.interceptor;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;

import java.util.List;

/**
 * sql 拦截器 适配器
 */
public class SqlInterceptorAdapter {
    private final List<SqlInterceptor> sqlInterceptorList;


    public SqlInterceptorAdapter(List<SqlInterceptor> sqlInterceptorList) {
        this.sqlInterceptorList = sqlInterceptorList;
    }


    /**
     * sql解析拦截
     *
     * @param persistenceRepository 预执行SQL需要的属性
     */
    public void interceptor(PersistenceRepository persistenceRepository) {
        for (SqlInterceptor sqlInterceptor : sqlInterceptorList) {
            if (sqlInterceptor.support(persistenceRepository)) {
                sqlInterceptor.interceptor(persistenceRepository);
            }
        }
    }
}
