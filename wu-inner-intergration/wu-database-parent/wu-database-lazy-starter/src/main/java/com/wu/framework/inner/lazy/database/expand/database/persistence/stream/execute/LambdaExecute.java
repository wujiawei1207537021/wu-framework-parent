package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.execute;


import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;

/**
 * @author wujiawei
 */
public class LambdaExecute<T> extends AbstractExecute<T> {

    private PersistenceRepository persistenceRepository;

    public LambdaExecute(LazyOperation lazyOperation) {
        super(lazyOperation);
    }

    @Override
    public PersistenceRepository persistenceRepository() {
        return persistenceRepository;
    }

    public void setPersistenceRepository(PersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }
}
