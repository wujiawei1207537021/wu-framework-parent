package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build;


import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.execute.Execute;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.execute.LambdaExecute;

import java.lang.reflect.ParameterizedType;


/**
 * @author wujiawei
 */
public class LambdaBuild<T> extends AbstractBuild<T> {

    private final LazyOperation lazyOperation;
    private Class<T> type;

    public LambdaBuild(Class<T> type, LazyOperation lazyOperation) {
        this.type = type;
        this.lazyOperation = lazyOperation;
    }

    public LambdaBuild(LazyOperation lazyOperation) {
        this.lazyOperation = lazyOperation;
    }


    @Override
    protected Execute<T> createExecute(PersistenceRepository persistenceRepository) {
        persistenceRepository.setResultClass(getClassT());
        final LambdaExecute lambdaExecute = new LambdaExecute(lazyOperation);
        lambdaExecute.setPersistenceRepository(persistenceRepository);
        return lambdaExecute;
    }


    /**
     * 获取T 的class
     *
     * @return
     */
    @Override
    public Class<T> getClassT() {
        if (null == type) {
            ParameterizedType superClass = (ParameterizedType) this.getClass().getGenericSuperclass();
            this.type = (Class<T>) superClass.getActualTypeArguments()[0];
        }
        return type;
    }


}
