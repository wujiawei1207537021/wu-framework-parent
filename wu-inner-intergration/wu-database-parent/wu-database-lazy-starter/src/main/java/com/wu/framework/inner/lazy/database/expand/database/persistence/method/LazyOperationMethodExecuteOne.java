package com.wu.framework.inner.lazy.database.expand.database.persistence.method;


import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Component
public class LazyOperationMethodExecuteOne extends AbstractLazyOperationMethod {
    /**
     * @param param
     * @return description 通过参数获取持久性存储库对象
     * @author Jia wei Wu
     * @date 2021/4/17 3:38 下午
     **/
    @Override
    public PersistenceRepository analyzePersistenceRepository(Object param) {
        return (PersistenceRepository) param;
    }

    @Override
    public Object execute(Connection connection, Object[] sourceParams) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        PersistenceRepository persistenceRepository = analyzePersistenceRepository(sourceParams[0]);
        PreparedStatement preparedStatement = connection.prepareStatement(persistenceRepository.getQueryString());
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
                return update;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw sqlException;
        } finally {
            preparedStatement.close();
        }
    }
}
