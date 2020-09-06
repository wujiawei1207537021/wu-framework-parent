package com.wu.framework.inner.database.custom.database.persistence.mehtod;

import com.wu.framework.inner.database.custom.database.persistence.domain.CustomPersistenceRepository;
import com.wu.framework.inner.database.converter.PreparedStatementSQLConverter;
import com.wu.framework.inner.database.custom.database.persistence.stereotype.GetCustomRepositoryOnDifferentMethods;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author : 吴佳伟
 * @version : 1.0
 * @describe: 根据ID更新  自定义数据库持久层操作方法我选择一种
 * @date : 2020/7/4 下午7:22
 */
@GetCustomRepositoryOnDifferentMethods(GetCustomRepositoryOnDifferentMethods.CustomRepositoryMethodEnum.SELECT_ONE)
public class LayerOperationMethodISelectOne extends AbstractLayerOperationMethod {

    @Override
    public CustomPersistenceRepository getCustomRepository(Method method, Object[] args) throws IllegalArgumentException {
        String queryString = "";
        if (ObjectUtils.isEmpty(args)) {
            throw new IllegalArgumentException("fail invoke this method in method" + method.getName());
        }
        Object object = args[0];
        Class clazz = object.getClass();
        queryString = PreparedStatementSQLConverter.selectPreparedStatementSQL(object);
//        System.out.println(queryString);
        CustomPersistenceRepository customPersistenceRepository = new CustomPersistenceRepository();
        customPersistenceRepository.setQueryString(queryString);
        customPersistenceRepository.setResultClass(clazz);
        return customPersistenceRepository;
    }

    @Override
    public Object execute(PreparedStatement preparedStatement, String resultType) throws SQLException {
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            List result = resultSetConverter(resultSet, resultType);
            if(result.size()>1){
                throw new IllegalArgumentException(" expected one but found "+result.size() );
            }
            if(ObjectUtils.isEmpty(result)){
                return null;
            }
            return result.get(0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            preparedStatement.close();
        }
        return null;

    }
}
