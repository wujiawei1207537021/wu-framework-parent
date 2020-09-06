package com.wu.framework.inner.database.custom.database.persistence.mehtod;

import com.wu.framework.inner.database.custom.database.persistence.domain.CustomPersistenceRepository;
import com.wu.framework.inner.database.converter.PreparedStatementSQLConverter;
import com.wu.framework.inner.database.custom.database.persistence.stereotype.GetCustomRepositoryOnDifferentMethods;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author : 吴佳伟
 * @version : 1.0
 * @describe: 根据ID更新  自定义数据库持久层操作方法I按ID删除
 * @date : 2020/7/4 下午7:22
 */
@GetCustomRepositoryOnDifferentMethods(GetCustomRepositoryOnDifferentMethods.CustomRepositoryMethodEnum.DELETE_BY_ID)
public class LayerOperationMethodIDeleteById extends AbstractLayerOperationMethod {

    @Override
    public CustomPersistenceRepository getCustomRepository(Method method, Object[] args) throws IllegalArgumentException {
        String queryString = "";
        if(ObjectUtils.isEmpty(args)){
            throw new IllegalArgumentException("fail invoke this method in method"+method.getName());
        }
        Object object = args[0];
        Class clazz = object.getClass();
        queryString = PreparedStatementSQLConverter.deletePreparedStatementSQL(object);
        CustomPersistenceRepository customPersistenceRepository = new CustomPersistenceRepository();
        customPersistenceRepository.setQueryString(queryString);
        customPersistenceRepository.setResultClass(clazz);
        return customPersistenceRepository;
    }
    @Override
    public Object execute(PreparedStatement preparedStatement, String resultType) throws SQLException {
        try {
            return preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            preparedStatement.close();
        }
        return 0;

    }
}
