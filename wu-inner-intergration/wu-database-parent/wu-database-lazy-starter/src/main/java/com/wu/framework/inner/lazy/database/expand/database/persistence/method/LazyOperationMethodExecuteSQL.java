package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepositoryFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :  自定义数据库持久层操作方法执行SQL
 * @date : 2020/7/3 下午10:28
 */
@Component
public class LazyOperationMethodExecuteSQL extends AbstractLazyOperationMethod {

    private final LazyOperationConfig operationConfig;

    public LazyOperationMethodExecuteSQL(LazyOperationConfig operationConfig) {
        this.operationConfig = operationConfig;
    }


    /**
     * @param param
     * @return description 通过参数获取持久性存储库对象
     * @author Jia wei Wu
     * @date 2021/4/17 3:38 下午
     **/
    @Override
    public PersistenceRepository analyzePersistenceRepository(Object param) throws IllegalArgumentException {
        // 第一个参数 SQL
        Object[] p = (Object[]) param;
        String sourceSql = (String) p[0];
        Class clazz = (Class) p[1];

        Object[] params = (Object[]) p[2];
        String sql;
        if (ObjectUtils.isEmpty(params)) {
            sql = sourceSql;
        } else {
            sql = String.format(sourceSql, params);
        }


        PersistenceRepository persistenceRepository = PersistenceRepositoryFactory.create(operationConfig);
        if (sql.contains(LambdaTableType.DELETE.getValue())) {
            persistenceRepository.setExecutionType(LambdaTableType.DELETE);
        }
        if (sql.contains(LambdaTableType.INSERT.getValue())) {
            persistenceRepository.setExecutionType(LambdaTableType.INSERT);
        }
        if (sql.contains(LambdaTableType.UPDATE.getValue())) {
            persistenceRepository.setExecutionType(LambdaTableType.UPDATE);
        }
        persistenceRepository.setQueryString(sql);
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
    public Object execute(Connection connection, Object[] sourceParams) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        PersistenceRepository persistenceRepository = analyzePersistenceRepository(sourceParams);
        PreparedStatement preparedStatement = connection.prepareStatement(persistenceRepository.getQueryString());
        try {
            if (persistenceRepository.getExecutionType().equals(LambdaTableType.SELECT)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                List result = resultSetConverter(resultSet, persistenceRepository.getResultType());
                return result;
            } else {
                int update = preparedStatement.executeUpdate();
                return Arrays.asList(update);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            preparedStatement.close();
        }
    }
}
