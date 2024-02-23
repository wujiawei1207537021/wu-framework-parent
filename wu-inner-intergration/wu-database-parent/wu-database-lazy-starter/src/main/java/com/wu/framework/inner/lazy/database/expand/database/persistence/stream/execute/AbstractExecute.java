package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.execute;

import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;

import java.util.Collection;


/**
 * @author wujiawei
 */
public abstract class AbstractExecute<T> implements Execute<T> {

    private final LazyOperation lazyOperation;

    protected AbstractExecute(LazyOperation lazyOperation) {
        this.lazyOperation = lazyOperation;
    }

    protected abstract PersistenceRepository persistenceRepository();


    /**
     * @param aClass@return describe 返回指定类型的数据
     *                      收集集合
     * @author Jia wei Wu
     * @date 2021/8/8 12:03 下午
     **/
    @Override
    public Collection collection(Class aClass) {
        PersistenceRepository persistenceRepository = persistenceRepository();
        persistenceRepository.setResultClass(aClass);
        return lazyOperation.execute(persistenceRepository);
    }

    /**
     * @return describe 收集集合
     * @author Jia wei Wu
     * @date 2021/8/8 12:04 下午
     **/
    @Override
    public Collection collection() {
        PersistenceRepository persistenceRepository = persistenceRepository();
        return lazyOperation.execute(persistenceRepository);
    }

    /**
     * @param aClass@return describe 返回指定类型的数据
     *                      收集一个数据
     * @author Jia wei Wu
     * @date 2021/8/8 12:04 下午
     **/
    @Override
    public T collectOne(Class aClass) {
        PersistenceRepository persistenceRepository = persistenceRepository();
        persistenceRepository.setResultClass(aClass);
        return (T) lazyOperation.executeOne(persistenceRepository);
    }

    /**
     * @return describe 返回数据
     * 收集一个数据
     * @author Jia wei Wu
     * @date 2021/8/8 12:04 下午
     **/
    @Override
    public T collectOne() {
        return null;
    }
}
