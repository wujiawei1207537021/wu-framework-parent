package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build;


import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepositoryFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.ConditionList;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.execute.Execute;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;

import java.lang.reflect.ParameterizedType;

/**
 * @author wujiawei
 */
public abstract class AbstractBuild<T> implements Build<T> {

    protected final String SELECT_ALL = "select * from ";
    protected final String DELETE_FROM = "delete from ";

    protected abstract Execute<T> createExecute(PersistenceRepository persistenceRepository);

    @Override
    public Execute<T> select(BasicComparison comparison) {

        final Class<T> classT = getClassT();
        final String tableName = LazyTableUtil.getTableName(classT);
        String prefix = SELECT_ALL + tableName;
        PersistenceRepository persistenceRepository;
        if (null == comparison) {
            persistenceRepository = PersistenceRepositoryFactory.create();
            persistenceRepository.setQueryString(prefix);
        } else {
            final ConditionList conditionList = comparison.getConditionList();
            conditionList.setPrefix(prefix);
            persistenceRepository = conditionList.persistenceRepository();
        }
        persistenceRepository.setExecutionType(LambdaTableType.SELECT);
        final Execute<T> execute = createExecute(persistenceRepository);
        return execute;
    }


    /**
     * 删除数据
     *
     * @param comparison
     * @return
     */
    @Override
    public Long delete(BasicComparison comparison) {
        final Class<T> classT = getClassT();
        final String tableName = LazyTableUtil.getTableName(classT);
        String prefix = DELETE_FROM + tableName;
        PersistenceRepository persistenceRepository;
        if (null == comparison) {
            persistenceRepository = PersistenceRepositoryFactory.create();
            persistenceRepository.setQueryString(prefix);
        } else {
            final ConditionList conditionList = comparison.getConditionList();
            conditionList.setPrefix(prefix);
            persistenceRepository = conditionList.persistenceRepository();
        }
        persistenceRepository.setExecutionType(LambdaTableType.DELETE);
        Execute<T> execute = createExecute(persistenceRepository);
        return execute.collectOne(Long.class);
    }

    /**
     * 获取T 的class
     *
     * @return
     */
    protected Class<T> getClassT() {
        ParameterizedType superClass = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<T> type = (Class<T>) superClass.getActualTypeArguments()[0];
        return type;
    }

}
