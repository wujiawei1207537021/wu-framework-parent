package com.wu.framework.inner.lazy.database.expand.database.persistence.method.dml;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.AbstractLazyOperationMethod;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationParameter;
import com.wu.framework.inner.lazy.persistence.converter.SQLConverter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;

import java.sql.Connection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * describe: 根据ID更新  自定义数据库持久层操作方法按ID删除
 * @date : 2020/7/4 下午7:22
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyOperationMethodDeleteById extends AbstractLazyOperationMethod {


    public LazyOperationMethodDeleteById(LazyOperationParameter lazyOperationParameter) {
        super(lazyOperationParameter);
    }


    /**
     * @param sourceParams 原始数据
     * @return description 通过参数获取持久性存储库对象
     * @author Jia wei Wu
     * @date 2021/4/17 3:38 下午
     **/
    @Override
    public PersistenceRepository doAnalyzePersistenceRepository(Object[] sourceParams) throws Exception {
        String queryString = "";
        Class<?> clazz = ((Object) sourceParams).getClass();
        queryString = SQLConverter.deletePreparedStatementSQL(sourceParams);
        PersistenceRepository persistenceRepository = createPersistenceRepository();
        persistenceRepository.setQueryString(queryString);
        persistenceRepository.setResultClass(clazz);
        return persistenceRepository;
    }

    /**
     * description 执行SQL 语句
     *
     * @param connection
     * @param sourceParams
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/11/22 上午11:02
     */
    @Override
    public Object doExecute(Connection connection, Object[] sourceParams) throws Exception {

        AtomicInteger affectRow = new AtomicInteger();
        Object param = sourceParams[0];
        if (param instanceof Object[]) {
            Object[] upsertList = (Object[]) param;
            for (Object upsert : upsertList) {
                executionFunction(connection, preparedStatement -> {
                    affectRow.addAndGet(preparedStatement.executeUpdate());
                    return preparedStatement;
                }, upsert);
            }
        } else {
            executionFunction(connection, preparedStatement -> {
                affectRow.addAndGet(preparedStatement.executeUpdate());
                return preparedStatement;
            }, param);
        }
        return affectRow.get();
    }
}
