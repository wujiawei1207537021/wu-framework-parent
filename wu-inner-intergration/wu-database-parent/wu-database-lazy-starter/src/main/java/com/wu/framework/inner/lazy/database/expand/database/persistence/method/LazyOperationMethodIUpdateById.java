package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.database.converter.PreparedStatementSQLConverter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.RepositoryOnDifferentMethods;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 根据ID更新 自定义数据库持久层操作方法I通过ID更新
 * @date : 2020/7/4 下午7:22
 */
@RepositoryOnDifferentMethods(RepositoryOnDifferentMethods.LayerOperationMethodEnum.UPDATE_BY_ID)
public class LazyOperationMethodIUpdateById extends AbstractLazyOperationMethod {

    @Override
    public PersistenceRepository getPersistenceRepository(Method method, Object[] args) throws IllegalArgumentException {
        String queryString;
        if (ObjectUtils.isEmpty(args)) {
            throw new IllegalArgumentException("fail invoke this method in method" + method.getName());
        }
        Object object = args[0];
        Class clazz = object.getClass();
        queryString = PreparedStatementSQLConverter.updatePreparedStatementSQL(object);
        PersistenceRepository persistenceRepository = new PersistenceRepository();
        persistenceRepository.setQueryString(queryString);
        persistenceRepository.setResultClass(clazz);
        return persistenceRepository;
    }

    /**
     * description 执行SQL 语句
     *
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/11/22 上午11:02
     **/
    @Override
    public Object execute(PreparedStatement preparedStatement, PersistenceRepository persistenceRepository) throws SQLException {
        try {
            return preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            preparedStatement.close();
        }
    }
}
