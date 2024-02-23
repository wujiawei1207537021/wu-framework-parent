package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build;


import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepositoryFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyTableStructureConverterFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.ConditionList;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.execute.Execute;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyTableEndpoint;
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
        final ClassLazyTableEndpoint classLazyTableEndpoint = LazyTableUtil.analyzeLazyTable(classT);
        final String tableName = classLazyTableEndpoint.getFullTableName();
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
    public Integer delete(BasicComparison comparison) {
        final Class<T> classT = getClassT();
        final ClassLazyTableEndpoint classLazyTableEndpoint = LazyTableUtil.analyzeLazyTable(classT);
        final String tableName = classLazyTableEndpoint.getFullTableName();
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
        return execute.collectOne(Integer.class);
    }

    /**
     * 判断是否存在
     *
     * @param comparison 条件
     * @return boolean true存在 false 不存在
     */
    @Override
    public boolean exists(BasicComparison comparison) {
        final Class<T> classT = getClassT();
        final ClassLazyTableEndpoint classLazyTableEndpoint = LazyTableUtil.analyzeLazyTable(classT);
        final String tableName = classLazyTableEndpoint.getFullTableName();
        String prefix = "select 1 from " + tableName;
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
        Execute<T> execute = createExecute(persistenceRepository);
        final Long aLong = execute.collectOne(Long.class);
        return aLong == 1;
    }

    /**
     * 统计表中的数据
     *
     * @param comparison 条件
     * @return boolean true存在 false 不存在
     */
    @Override
    public Long count(BasicComparison comparison) {
        final Class<T> classT = getClassT();
        final ClassLazyTableEndpoint classLazyTableEndpoint = LazyTableUtil.analyzeLazyTable(classT);
        final String tableName = classLazyTableEndpoint.getFullTableName();
        String prefix = "select count(1) from " + tableName;
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
        Execute<T> execute = createExecute(persistenceRepository);
        final Long aLong = execute.collectOne(Long.class);
        return aLong;
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
