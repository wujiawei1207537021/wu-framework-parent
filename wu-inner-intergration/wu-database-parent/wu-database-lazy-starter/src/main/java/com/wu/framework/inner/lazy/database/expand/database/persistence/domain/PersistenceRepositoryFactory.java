package com.wu.framework.inner.lazy.database.expand.database.persistence.domain;

import com.wu.framework.inner.lazy.config.LazyOperationConfig;

/**
 * describe: PersistenceRepositoryFactory 工厂
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/1 8:59 下午
 */
public class PersistenceRepositoryFactory {


    public static PersistenceRepository create(LazyOperationConfig lazyOperationConfig) {
        return new PersistenceRepository(lazyOperationConfig.isPrintfQuery());
    }

    public static PersistenceRepository create() {
        return new PersistenceRepository(LazyOperationConfig.printfQuery);
    }


}
