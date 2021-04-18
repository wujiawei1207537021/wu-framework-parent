package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 根据ID更新  自定义数据库持久层操作方法I按ID删除
 * @date : 2020/7/4 下午7:22
 */
@Component
public class LazyOperationMethodDeleteById extends AbstractLazyOperationMethod {

    @Override
    public PersistenceRepository analyzePersistenceRepository(Object param) throws IllegalArgumentException {
        String queryString = "";
        Object object = param;
        Class clazz = object.getClass();
        queryString = deletePreparedStatementSQL(object);
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
        int affectRow = 0;
        for (Object param : params) {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(analyzePersistenceRepository(param).getQueryString());
            try {
                affectRow += preparedStatement.executeUpdate();
            } catch (SQLException sqlException) {
                throw sqlException;
            } finally {
                connection.close();
                preparedStatement.close();
            }
        }
        return affectRow;
    }
}
