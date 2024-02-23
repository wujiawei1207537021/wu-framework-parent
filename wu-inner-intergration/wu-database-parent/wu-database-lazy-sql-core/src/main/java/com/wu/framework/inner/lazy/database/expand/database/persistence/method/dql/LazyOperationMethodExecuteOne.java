package com.wu.framework.inner.lazy.database.expand.database.persistence.method.dql;


import com.wu.framework.inner.layer.data.convert.LazyDataFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationParameter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.util.ObjectUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * describe: 执行返回一条数据
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/7/15 22:44
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyOperationMethodExecuteOne extends AbstractLazyDQLOperationMethod {
    public LazyOperationMethodExecuteOne(LazyOperationParameter lazyOperationParameter) {
        super(lazyOperationParameter);
    }

    /**
     * @param sourceParams
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
                List result = resultSetConverter(resultSet, persistenceRepository.getResultClass());
                if (ObjectUtils.isEmpty(result)) {
                    return null;
                }
                if (result.size() > 1) {
                    throw new IllegalArgumentException(" expected one but found " + result.size());
                }
                return result.get(0);
            } else {
                int update = preparedStatement.executeUpdate();
                return LazyDataFactory.handler(update, persistenceRepository.getResultClass());
            }
        } catch (SQLException sqlException) {
            throw new SQLException(queryString, sqlException);
        } finally {
            preparedStatement.close();
        }
    }
}
