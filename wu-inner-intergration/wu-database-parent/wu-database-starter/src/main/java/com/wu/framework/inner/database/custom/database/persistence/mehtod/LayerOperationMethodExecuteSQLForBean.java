package com.wu.framework.inner.database.custom.database.persistence.mehtod;

import com.wu.framework.inner.database.custom.database.persistence.domain.CustomPersistenceRepository;
import com.wu.framework.inner.database.custom.database.persistence.stereotype.GetCustomRepositoryOnDifferentMethods;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 执行SQL  自定义数据库持久层操作方法对Bean执行SQL
 * @date : 2020/7/3 下午10:28
 */
@GetCustomRepositoryOnDifferentMethods(GetCustomRepositoryOnDifferentMethods.CustomRepositoryMethodEnum.EXECUTE_SQL)
public class LayerOperationMethodExecuteSQLForBean extends AbstractLayerOperationMethod {

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
            return preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            preparedStatement.close();
        }
        return false;
    }
}
