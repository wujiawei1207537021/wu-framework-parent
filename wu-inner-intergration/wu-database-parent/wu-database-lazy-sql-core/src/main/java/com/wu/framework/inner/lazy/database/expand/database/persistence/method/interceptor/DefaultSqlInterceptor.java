package com.wu.framework.inner.lazy.database.expand.database.persistence.method.interceptor;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;

/**
 * 默认拦截器 什么都不操作
 */
public class DefaultSqlInterceptor extends AbstractSqlInterceptor {
    /**
     * before insert sql解析拦截
     *
     * @param persistenceRepository 预执行SQL需要的属性
     */
    @Override
    protected void beforeInsertInterceptor(PersistenceRepository persistenceRepository) {

    }

    /**
     * before delete sql解析拦截
     *
     * @param persistenceRepository 预执行SQL需要的属性
     */
    @Override
    protected void beforeDeleteInterceptor(PersistenceRepository persistenceRepository) {

    }

    /**
     * before update sql解析拦截
     *
     * @param persistenceRepository 预执行SQL需要的属性
     */
    @Override
    protected void beforeUpdateInterceptor(PersistenceRepository persistenceRepository) {

    }

    /**
     * before select sql解析拦截
     *
     * @param persistenceRepository 预执行SQL需要的属性
     */
    @Override
    protected void beforeSelectInterceptor(PersistenceRepository persistenceRepository) {

    }

    /**
     * 支持
     *
     * @param persistenceRepository 预执行SQL需要的属性
     * @return 布尔类型
     */
    @Override
    public boolean support(PersistenceRepository persistenceRepository) {
        return false;
    }
}
