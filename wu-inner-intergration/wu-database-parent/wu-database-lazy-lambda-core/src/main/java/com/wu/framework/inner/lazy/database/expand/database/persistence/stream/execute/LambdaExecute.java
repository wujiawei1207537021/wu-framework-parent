package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.execute;


import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyBaseOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;

/**
 * @author Jia wei Wu
 */
public class LambdaExecute<T> extends AbstractExecute<T> {

    private PersistenceRepository persistenceRepository;

    public LambdaExecute(LazyBaseOperation lazyOperation) {
        super(lazyOperation);
    }

    /**
     * describe PersistenceRepository 持久性存储库
     *
     * @return 返回持久性存储库 对象
     * @author Jia wei Wu
     * @date 2022/6/25 21:46
     **/
    @Override
    public PersistenceRepository persistenceRepository() {
        return persistenceRepository;
    }

    public void setPersistenceRepository(PersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }
}
