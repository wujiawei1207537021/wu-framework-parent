package com.wu.framework.inner.lazy.database.expand.database.persistence.method.interceptor;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;

/**
 * sql 拦截器
 */
public interface SqlInterceptor {

    /**
     * 支持
     *
     * @param persistenceRepository 预执行SQL需要的属性
     * @return 布尔类型
     */
    boolean support(PersistenceRepository persistenceRepository);

    /**
     * sql解析拦截
     *
     * @param persistenceRepository 预执行SQL需要的属性
     */
    void interceptor(PersistenceRepository persistenceRepository);


}
