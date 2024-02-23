package com.wu.framework.inner.lazy.database.expand.database.persistence.method.interceptor;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;

public abstract class AbstractSqlInterceptor extends AbstractSqlTableInterceptor implements SqlInterceptor {

    /**
     * before insert sql解析拦截
     *
     * @param persistenceRepository 预执行SQL需要的属性
     */
    protected abstract void beforeInsertInterceptor(PersistenceRepository persistenceRepository);

    /**
     * before delete sql解析拦截
     *
     * @param persistenceRepository 预执行SQL需要的属性
     */
    protected abstract void beforeDeleteInterceptor(PersistenceRepository persistenceRepository);


    /**
     * before update sql解析拦截
     *
     * @param persistenceRepository 预执行SQL需要的属性
     */
    protected abstract void beforeUpdateInterceptor(PersistenceRepository persistenceRepository);

    /**
     * before select sql解析拦截
     *
     * @param persistenceRepository 预执行SQL需要的属性
     */
    protected abstract void beforeSelectInterceptor(PersistenceRepository persistenceRepository);

    /**
     * sql解析拦截
     *
     * @param persistenceRepository 预执行SQL需要的属性
     */
    @Override
    public void interceptor(PersistenceRepository persistenceRepository) {
        LambdaTableType executionType = persistenceRepository.getExecutionType();
        switch (executionType) {
            case INSERT, UPSERT, UPSERT_REMOVE_NULL -> beforeInsertInterceptor(persistenceRepository);
            case DELETE -> beforeDeleteInterceptor(persistenceRepository);
            case UPDATE -> beforeUpdateInterceptor(persistenceRepository);
            case SELECT, BATCH -> beforeSelectInterceptor(persistenceRepository);
        }

    }
}
