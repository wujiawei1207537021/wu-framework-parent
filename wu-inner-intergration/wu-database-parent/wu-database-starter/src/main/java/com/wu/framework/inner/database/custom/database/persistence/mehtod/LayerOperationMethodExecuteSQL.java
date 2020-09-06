package com.wu.framework.inner.database.custom.database.persistence.mehtod;

import com.wu.framework.inner.database.custom.database.persistence.stereotype.GetCustomRepositoryOnDifferentMethods;
import com.wu.framework.inner.database.custom.database.persistence.domain.CustomPersistenceRepository;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :  自定义数据库持久层操作方法执行SQL
 * @date : 2020/7/3 下午10:28
 */
@GetCustomRepositoryOnDifferentMethods(GetCustomRepositoryOnDifferentMethods.CustomRepositoryMethodEnum.EXECUTE_SQL)
public class LayerOperationMethodExecuteSQL extends AbstractLayerOperationMethod {

    @Override
    public CustomPersistenceRepository getCustomRepository(Method method, Object[] args) throws IllegalArgumentException {
        // 第一个参数 SQL
        String sql = (String) args[0];
        Class clazz = (Class) args[1];
        CustomPersistenceRepository customPersistenceRepository = new CustomPersistenceRepository();
        customPersistenceRepository.setQueryString(sql);
        customPersistenceRepository.setResultClass(clazz);
        return customPersistenceRepository;
    }

    @Override
    public Object execute(PreparedStatement preparedStatement, String resultType) throws SQLException {
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            List result = resultSetConverter(resultSet, resultType);
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            preparedStatement.close();
        }
        return Arrays.asList();
    }
}
