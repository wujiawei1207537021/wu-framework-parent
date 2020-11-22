package com.wu.framework.inner.database.expand.database.persistence.method;

import com.wu.framework.inner.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.database.expand.database.persistence.stereotype.RepositoryOnDifferentMethods;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 执行SQL  自定义数据库持久层操作方法对Bean执行SQL
 * @date : 2020/7/3 下午10:28
 */
@RepositoryOnDifferentMethods(RepositoryOnDifferentMethods.LayerOperationMethodEnum.EXECUTE_SQL)
public class LayerOperationMethodExecuteSQLForBean extends AbstractLayerOperationMethod {

    @Override
    public PersistenceRepository getPersistenceRepository(Method method, Object[] args) throws IllegalArgumentException {
        // 第一个参数 SQL
        String sql = (String) args[0];
        Class clazz = (Class) args[1];
        PersistenceRepository persistenceRepository = new PersistenceRepository();
        persistenceRepository.setQueryString(sql);
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
    public Object execute(PreparedStatement preparedStatement, String resultType) throws SQLException {
        try {
            return preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            preparedStatement.close();
        }
        return false;
    }
}
