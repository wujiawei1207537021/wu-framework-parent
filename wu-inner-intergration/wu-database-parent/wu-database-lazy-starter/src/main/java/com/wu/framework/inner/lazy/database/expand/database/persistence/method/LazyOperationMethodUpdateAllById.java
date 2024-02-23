package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 根据ID更新 自定义数据库持久层操作方法I按ID更新全部
 * @date : 2020/7/4 下午7:22
 */
@Deprecated
@Component
public class LazyOperationMethodUpdateAllById extends AbstractLazyOperationMethod {

    @Override
    public PersistenceRepository analyzePersistenceRepository(Object param) throws IllegalArgumentException {
        String queryString = "";
        Object object = param;
        Class clazz;
        // 第一个参数 list
        Collection collection = (Collection) object;
        clazz = collection.iterator().next().getClass();
        for (Object o : collection) {
            queryString += updatePreparedStatementSQL(o) + " ; \n ";
        }
        PersistenceRepository persistenceRepository = new PersistenceRepository();
        persistenceRepository.setQueryString(queryString);
        persistenceRepository.setResultClass(clazz);
        return persistenceRepository;
    }

    /**
     * description 执行SQL 语句
     *
     * @param dataSource
     * @param params
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/11/22 上午11:02
     */
    @Override
    public Object execute(DataSource dataSource, Object[] params) throws SQLException {
        Connection connection = dataSource.getConnection();
        PersistenceRepository persistenceRepository = analyzePersistenceRepository(params[0]);
        PreparedStatement preparedStatement = connection.prepareStatement(persistenceRepository.getQueryString());
        try {
            return preparedStatement.executeBatch();
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            connection.close();
            preparedStatement.close();
        }
    }
}
