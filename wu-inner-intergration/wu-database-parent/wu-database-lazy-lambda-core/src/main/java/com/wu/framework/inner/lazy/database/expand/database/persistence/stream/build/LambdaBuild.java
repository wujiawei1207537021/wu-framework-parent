package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build;


import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyBaseOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.execute.Execute;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.execute.LambdaExecute;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * @author Jia wei Wu
 */
public class LambdaBuild<T> extends AbstractBuild<T> {

    private final LazyBaseOperation lazyOperation;
    private Class<T> type;

    public LambdaBuild(Class<T> type, LazyBaseOperation lazyOperation) {
        this.type = type;
        this.lazyOperation = lazyOperation;
    }

    public LambdaBuild(LazyBaseOperation lazyOperation) {
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
     * 创建处理对象
     *
     * @return
     */
    @Override
    protected LambdaExecute<T> createExecute() {
        return new LambdaExecute(lazyOperation);
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
            final Type[] actualTypeArguments = superClass.getActualTypeArguments();
            final Type actualTypeArgument = actualTypeArguments[0];
            if (actualTypeArgument.getClass().isAssignableFrom(Class.class)) {
                this.type = (Class<T>) actualTypeArgument;
            }
        }
        return type;
    }

    @Override
    protected Class<T> getClassT(BasicComparison<T, ?, ?, ?> basicComparison) {
        getClassT();
        if (null == type) {
            this.type = basicComparison.getClassT();
        }
        return type;
    }
}
