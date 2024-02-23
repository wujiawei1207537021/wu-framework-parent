package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build;


import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.Persistence;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepositoryFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build.delete.SimpleDeleteBuild;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build.select.SimpleSelectBuild;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.build.update.SimpleUpdateBuild;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.SqlPart;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.execute.Execute;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.execute.LambdaExecute;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.function.Snippet;
import com.wu.framework.inner.lazy.factory.LazyTableStructureConverterFactory;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableStructure;
import com.wu.framework.inner.lazy.persistence.util.LazyFieldStrategyUtil;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.inner.lazy.source.SqlSourceClass;
import com.wu.framework.inner.lazy.source.adapter.SourceFactory;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jia wei Wu
 */
public abstract class AbstractBuild<T> extends AbstractSimpleSelectBuild<T> implements Build<T>, SimpleSelectBuild<T>, SimpleUpdateBuild<T>, SimpleDeleteBuild<T> {


    protected final String SELECT_ALL = "select %s.* from %s";
    protected final String DELETE_FROM = "delete from ";

    /**
     * 创建处理对象
     *
     * @param persistenceRepository
     * @return
     */
    protected abstract Execute<T> createExecute(PersistenceRepository persistenceRepository);

    /**
     * 创建处理对象
     *
     * @return
     */
    protected abstract LambdaExecute<T> createExecute();

    /**
     * 查询数据
     *
     * @param comparison 条件
     * @param snippets   查询的数据
     * @return Execute<T>
     * @see SimpleSelectBuild#select(BasicComparison)   you can use as to instance snippets
     */
    @Deprecated
    @Override
    public Execute<T> select(BasicComparison comparison, Snippet<T, ?>... snippets) {
        Class<T> classT = getClassT(comparison);
        if (ObjectUtils.isEmpty(classT)) {
            Assert.notNull(comparison, "当前未指定主表,请使用LazyLambdaStream.of(Class clazz) or make comparison not null");
            classT = comparison.getClassT();
        }
        LazyTableEndpoint lazyTableEndpoint = SourceFactory.defaultAnalyzeLazyTableFromClass(classT);
//        LazyTableEndpoint lazyTableEndpoint = LazyTableUtil.analyzeLazyTable(classT);
        String tableName = lazyTableEndpoint.getTableName();
        SqlPart sqlPart;

        PersistenceRepository persistenceRepository;
        if (null == comparison) {
            sqlPart = new SqlPart();
            sqlPart.setPrimaryClass(classT);
        } else {
            sqlPart = comparison.getSqlPart();
        }
        sqlPart.setExecutionEnum(Persistence.ExecutionEnum.SELECT);
        sqlPart.setPrimaryClass(classT);
        sqlPart.setPrimaryTable(tableName);

        persistenceRepository = sqlPart.persistenceRepository();
        persistenceRepository.setExecutionType(LambdaTableType.SELECT);
        return createExecute(persistenceRepository);
    }

    @Override
    public Execute<T> select(BasicComparison<T, ?, ?, ?> comparison) {
        Class<T> classT = getClassT(comparison);
        if (ObjectUtils.isEmpty(classT)) {
            Assert.notNull(comparison, "当前未指定主表,请使用LazyLambdaStream.of(Class clazz) or make comparison not null");
            classT = comparison.getClassT();
        }

        LazyTableEndpoint lazyTableEndpoint = SourceFactory.defaultAnalyzeLazyTableFromClass(classT);
//        LazyTableEndpoint lazyTableEndpoint = LazyTableUtil.analyzeLazyTable(classT);
        String tableName = lazyTableEndpoint.getTableName();

        SqlPart sqlPart;

        PersistenceRepository persistenceRepository;
        if (null == comparison) {
            sqlPart = new SqlPart();
            sqlPart.setPrimaryClass(classT);
        } else {
            sqlPart = comparison.getSqlPart();
        }
        sqlPart.setExecutionEnum(Persistence.ExecutionEnum.SELECT);
        sqlPart.setPrimaryClass(classT);
        sqlPart.setPrimaryTable(tableName);
        persistenceRepository = sqlPart.persistenceRepository();
        persistenceRepository.setExecutionType(LambdaTableType.SELECT);
        return createExecute(persistenceRepository);
    }

    /**
     * 查询数据
     *
     * @param sql        条件
     * @param returnType
     * @return Execute<T>
     */
    @Override
    public Execute<T> select(String sql, Class<T> returnType) {
        PersistenceRepository persistenceRepository = PersistenceRepositoryFactory.create();
        persistenceRepository.setExecutionType(LambdaTableType.SELECT);
        persistenceRepository.setQueryString(sql);
        persistenceRepository.setResultClass(returnType);
        return createExecute(persistenceRepository);
    }


    /**
     * describe 内链查询返回数据
     *
     * @param
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/6/26 01:06
     **/
    protected <R> Execute<R> createExecute(Collection<?> rResult) {
        return null;
    }

    /**
     * 删除数据
     *
     * @param comparison
     * @return
     */
    @Override
    public Integer delete(BasicComparison<T, ?, ?, ?> comparison) {
        Class<T> classT = getClassT(comparison);
        LazyTableEndpoint lazyTableEndpoint = SourceFactory.defaultAnalyzeLazyTableFromClass(classT);
//        LazyTableEndpoint lazyTableEndpoint = LazyTableUtil.analyzeLazyTable(classT);
        PersistenceRepository persistenceRepository;
        SqlPart sqlPart;
        if (null == comparison) {
            sqlPart = new SqlPart();
            sqlPart.setPrimaryClass(classT);
        } else {
            sqlPart = comparison.getSqlPart();
        }
        sqlPart.setExecutionEnum(Persistence.ExecutionEnum.DELETE);
        sqlPart.setPrimaryClass(classT);
        sqlPart.setPrimaryTable(lazyTableEndpoint.getTableName());
        persistenceRepository = sqlPart.persistenceRepository();
        persistenceRepository.setExecutionType(LambdaTableType.DELETE);
        LambdaExecute<T> execute = createExecute();
        execute.setPersistenceRepository(persistenceRepository);
        return execute.collectOne(Integer.class);
    }

    /**
     * 查询数据
     *
     * @param t          实体对象
     * @param comparison 条件
     * @return Execute<T>
     */
    @Override
    public Integer update(T t, BasicComparison<T, ?, ?, ?> comparison) {
        SqlPart sqlPart = comparison.getSqlPart();
        // update sys_user set id_deleted=false where id >1
        Assert.notNull(t, "更新数据不能为空");
        LazyTableStructure lazyTableStructure = LazyTableStructureConverterFactory.dataStructure(t);
        List<List<LazyTableFieldEndpoint>> payload = lazyTableStructure.payload();
        Map<String, Object> updateSet = new HashMap<>();
        // 更新数据
        payload.forEach(lazyTableFieldEndpoints -> {
            for (LazyTableFieldEndpoint lazyTableFieldEndpoint : lazyTableFieldEndpoints) {
                if (LazyFieldStrategyUtil.testUpdate(lazyTableFieldEndpoint.getFieldValue(), lazyTableFieldEndpoint.getUpdateStrategy())) {
                    updateSet.put(lazyTableFieldEndpoint.getColumnName(), lazyTableFieldEndpoint.getSqlValue());
                }
            }
        });


        sqlPart.setUpdateSet(updateSet);

        sqlPart.setExecutionEnum(Persistence.ExecutionEnum.UPDATE);
        sqlPart.setPrimaryClass(lazyTableStructure.schema().getClazz());
        sqlPart.setPrimaryTable(lazyTableStructure.schema().getTableName());
        PersistenceRepository persistenceRepository = sqlPart.persistenceRepository();
        persistenceRepository.setExecutionType(LambdaTableType.UPDATE);
        LambdaExecute<T> execute = createExecute();
        execute.setPersistenceRepository(persistenceRepository);
        return execute.collectOne(Integer.class);
    }


    /**
     * 判断是否存在
     *
     * @param comparison 条件
     * @return boolean true存在 false 不存在
     */
    @Override
    public boolean exists(BasicComparison<T, ?, ?, ?> comparison) {
        Class<T> classT = getClassT(comparison);
        if (ObjectUtils.isEmpty(classT)) {
            Assert.notNull(comparison, "当前未指定主表,请使用LazyLambdaStream.of(Class clazz) or make comparison not null");
            classT = comparison.getClassT();
        }
//        LazyTableEndpoint lazyTableEndpoint = LazyTableUtil.analyzeLazyTable(classT);
        LazyTableEndpoint lazyTableEndpoint = SourceFactory.defaultAnalyzeLazyTableFromClass(classT);
         String tableName = lazyTableEndpoint.getFullTableName();
        String prefix = "select count(1) from " + tableName;
        PersistenceRepository persistenceRepository;
        if (null == comparison) {
            persistenceRepository = PersistenceRepositoryFactory.create();
            persistenceRepository.setQueryString(prefix);
        } else {
            final SqlPart sqlPart = comparison.getSqlPart();
            sqlPart.setPrefix(prefix);
            persistenceRepository = sqlPart.persistenceRepository();
        }
        persistenceRepository.setExecutionType(LambdaTableType.SELECT);
        Execute<T> execute = createExecute(persistenceRepository);
        final Long aLong = execute.collectOne(Long.class);
        return aLong != null && aLong != 0;
    }

    /**
     * 统计表中的数据
     *
     * @param comparison 条件
     * @return boolean true存在 false 不存在
     */
    @Override
    public Long count(BasicComparison<T, ?, ?, ?> comparison) {
        Class<T> classT = getClassT(comparison);
        if (ObjectUtils.isEmpty(classT)) {
            Assert.notNull(comparison, "当前未指定主表,请使用LazyLambdaStream.of(Class clazz) or make comparison not null");
            classT = comparison.getClassT();
        }
        SqlSourceClass sqlSourceClass =  SqlSourceClass.getInstance(classT);
        LazyTableEndpoint lazyTableEndpoint = sqlSourceClass.getLazyTableEndpoint();
//        LazyTableEndpoint lazyTableEndpoint = LazyTableUtil.analyzeLazyTable(classT);
        final String tableName = lazyTableEndpoint.getFullTableName();
        String prefix = "select count(1) from " + tableName;
        PersistenceRepository persistenceRepository;
        if (null == comparison) {
            persistenceRepository = PersistenceRepositoryFactory.create();
            persistenceRepository.setQueryString(prefix);
        } else {
            final SqlPart sqlPart = comparison.getSqlPart();
            sqlPart.setPrefix(prefix);
            persistenceRepository = sqlPart.persistenceRepository();
        }
        persistenceRepository.setExecutionType(LambdaTableType.SELECT);
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
        return (Class<T>) superClass.getActualTypeArguments()[0];
    }

    protected abstract Class<T> getClassT(BasicComparison<T, ?, ?, ?> basicComparison);

}
