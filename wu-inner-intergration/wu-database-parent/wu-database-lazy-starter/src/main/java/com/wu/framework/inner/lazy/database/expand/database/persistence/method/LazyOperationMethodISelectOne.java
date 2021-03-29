package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.database.converter.PreparedStatementSQLConverter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.constant.LayerOperationMethodCounts;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.RepositoryOnDifferentMethods;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 根据ID更新  自定义数据库持久层操作方法我选择一种
 * @date : 2020/7/4 下午7:22
 */
@RepositoryOnDifferentMethods(methodName = LayerOperationMethodCounts.SELECT_ONE)
public class LazyOperationMethodISelectOne extends AbstractLazyOperationMethod {

    @Override
    public PersistenceRepository getPersistenceRepository(Method method, Object[] args) throws Exception {
        String queryString = "";
        if (ObjectUtils.isEmpty(args)) {
            throw new IllegalArgumentException("fail invoke this method in method" + method.getName());
        }
        Object object = args[0];
        Class clazz = object.getClass();
        queryString = PreparedStatementSQLConverter.selectPreparedStatementSQL(object);
//        System.out.println(queryString);
        PersistenceRepository persistenceRepository = new PersistenceRepository();
        persistenceRepository.setQueryString(queryString);
        persistenceRepository.setResultClass(clazz);
        return persistenceRepository;
    }

    @Override
    public Object execute(PreparedStatement preparedStatement, PersistenceRepository persistenceRepository) throws SQLException {
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            List result = resultSetConverter(resultSet, persistenceRepository.getResultType());
            if (result.size() > 1) {
                throw new IllegalArgumentException(" expected one but found " + result.size());
            }
            if (ObjectUtils.isEmpty(result)) {
                return null;
            }
            return result.get(0);
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            preparedStatement.close();
        }
    }
}
