package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTable;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :  自定义数据库持久层操作方法执行SQL
 * @date : 2020/7/3 下午10:28
 */
@Component
public class LazyOperationMethodExecuteSQL extends AbstractLazyOperationMethod {


    @Override
    public PersistenceRepository analyzePersistenceRepository(Object param) throws IllegalArgumentException {
        // 第一个参数 SQL
        Object[] p = (Object[]) param;
        String sql = (String) p[0];
        Class clazz = (Class) p[1];
        PersistenceRepository persistenceRepository = new PersistenceRepository();
        if (sql.contains(LambdaTable.LambdaTableType.DELETE.getValue())) {
            persistenceRepository.setExecutionType(LambdaTable.LambdaTableType.DELETE);
        }
        persistenceRepository.setQueryString(sql);
        persistenceRepository.setResultClass(clazz);
        return persistenceRepository;
    }

    /**
     * description 执行SQL 语句
     *
     * @param dataSource
     * @param sourceParams
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/11/22 上午11:02
     */
    @Override
    public Object execute(DataSource dataSource, Object[] sourceParams) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        Connection connection = dataSource.getConnection();
        PersistenceRepository persistenceRepository = analyzePersistenceRepository(sourceParams);
        PreparedStatement preparedStatement = connection.prepareStatement(persistenceRepository.getQueryString());
        try {
            if (persistenceRepository.getExecutionType().equals(LambdaTable.LambdaTableType.DELETE)) {
                int update = preparedStatement.executeUpdate();
                return Arrays.asList(update);
            } else {
                ResultSet resultSet = preparedStatement.executeQuery();
                List result = resultSetConverter(resultSet, persistenceRepository.getResultType());
                return result;
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            connection.close();
            preparedStatement.close();
        }
    }
}
