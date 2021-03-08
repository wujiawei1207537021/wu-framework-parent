package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

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
 * @version 1.0
 * @describe : 执行SQL  自定义数据库持久层操作方法对Bean执行SQL
 * @date : 2020/7/3 下午10:28
 */
@RepositoryOnDifferentMethods(RepositoryOnDifferentMethods.LayerOperationMethodEnum.EXECUTE_SQL_FOR_BEAN)
public class LazyOperationMethodExecuteSQLForBean extends AbstractLazyOperationMethod {

    @Override
    public PersistenceRepository getPersistenceRepository(Method method, Object[] args) throws Exception {
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
    public Object execute(PreparedStatement preparedStatement, PersistenceRepository persistenceRepository) throws SQLException {
        try {
            ResultSet resultSet=preparedStatement.executeQuery();
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
