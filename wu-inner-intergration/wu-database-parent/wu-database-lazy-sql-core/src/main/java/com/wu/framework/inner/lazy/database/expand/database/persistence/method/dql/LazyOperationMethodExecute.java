package com.wu.framework.inner.lazy.database.expand.database.persistence.method.dql;


import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationParameter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyOperationMethodExecute extends AbstractLazyDQLOperationMethod {
    public LazyOperationMethodExecute(LazyOperationParameter lazyOperationParameter) {
        super(lazyOperationParameter);
    }

    /**
     * @param sourceParams 原始参数
     * @return description 通过参数获取持久性存储库对象
     * @author Jia wei Wu
     * @date 2021/4/17 3:38 下午
     **/
    @Override
    public PersistenceRepository doAnalyzePersistenceRepository(Object[] sourceParams) throws Exception {
        return (PersistenceRepository) sourceParams[0];
    }

    @Override
    public Object doExecute(Connection connection, Object[] sourceParams) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        PersistenceRepository persistenceRepository = null;
        try {
            persistenceRepository = analyzePersistenceRepository(sourceParams);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String queryString = persistenceRepository.getQueryString();
        PreparedStatement preparedStatement = connection.prepareStatement(queryString);
        try {
            if (persistenceRepository.getExecutionType().equals(LambdaTableType.SELECT)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSetConverter(resultSet, persistenceRepository.getResultClass());
            } else {
                int update = preparedStatement.executeUpdate();
                return Arrays.asList(update);
            }
        } catch (SQLException sqlException) {
            throw new SQLException("doExecute with sql: " + queryString, sqlException);
        } finally {
            preparedStatement.close();
        }
    }
}
