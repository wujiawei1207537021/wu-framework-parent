package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.execute;

import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyBaseOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;

import java.util.List;


/**
 * @author Jia wei Wu
 */
public abstract class AbstractExecute<T> implements Execute<T> {

    private final LazyBaseOperation lazyOperation;

    protected AbstractExecute(LazyBaseOperation lazyOperation) {
        this.lazyOperation = lazyOperation;
    }

    /**
     * describe PersistenceRepository 持久性存储库
     *
     * @return 返回持久性存储库 对象
     * @author Jia wei Wu
     * @date 2022/6/25 21:46
     **/
    protected abstract PersistenceRepository persistenceRepository();


    /**
     * 返回指定类型的数据 收集集合
     *
     * @param aClass
     * @return
     * @author Jia wei Wu
     * @date 2021/8/8 12:03 下午
     */
    @Override
    public <R> List<R> collection(Class<R> aClass) {
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
    public List<T> collection() {
        PersistenceRepository persistenceRepository = persistenceRepository();
        return lazyOperation.execute(persistenceRepository);
    }

    /**
     * describe 分叶
     *
     * @param lazyPage
     * @return
     * @author Jia wei Wu
     * @date 2022/2/1 15:13
     */
    @Override
    public <R> LazyPage<R> page(LazyPage<R> lazyPage) {
        PersistenceRepository persistenceRepository = persistenceRepository();
        return lazyOperation.executePage(persistenceRepository, lazyPage);
    }

    /**
     * describe 分页
     *
     * @param lazyPage 分页参数
     * @param rClass
     * @return LazyPage<T> 分页结果
     * @author Jia wei Wu
     * @date 2022/6/1 21:29
     **/
    @Override
    public <R> LazyPage<R> page(LazyPage<R> lazyPage, Class<R> rClass) {
        PersistenceRepository persistenceRepository = persistenceRepository();
        if (null != rClass) {
            persistenceRepository.setResultClass(rClass);
        }
        return lazyOperation.executePage(persistenceRepository, lazyPage);
    }

    /**
     * @param aClass@return describe 返回指定类型的数据
     *                      收集一个数据
     * @author Jia wei Wu
     * @date 2021/8/8 12:04 下午
     **/
    @Override
    public <R> R collectOne(Class<R> aClass) {
        PersistenceRepository persistenceRepository = persistenceRepository();
        persistenceRepository.setResultClass(aClass);
        final Object o = lazyOperation.executeOne(persistenceRepository);
        return (R) o;
    }

    /**
     * @return describe 返回数据
     * 收集一个数据
     * @author Jia wei Wu
     * @date 2021/8/8 12:04 下午
     **/
    @Override
    public T collectOne() {
        PersistenceRepository persistenceRepository = persistenceRepository();
        return (T) lazyOperation.executeOne(persistenceRepository);
    }
}
