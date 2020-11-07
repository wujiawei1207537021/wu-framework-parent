package com.wu.framework.inner.database.custom.database.persistence.mehtod;

import com.wu.framework.inner.database.converter.PreparedStatementSQLConverter;
import com.wu.framework.inner.database.custom.database.persistence.domain.CustomPersistenceRepository;
import com.wu.framework.inner.database.custom.database.persistence.stereotype.GetCustomRepositoryOnDifferentMethods;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 根据ID更新  自定义数据库持久层操作方法I按ID列表删除
 * @date : 2020/7/4 下午7:22
 */
@GetCustomRepositoryOnDifferentMethods(GetCustomRepositoryOnDifferentMethods.CustomRepositoryMethodEnum.DELETE_BY_ID_LIST)
public class LayerOperationMethodIDeleteByIdList extends AbstractLayerOperationMethod {

    @Override
    public CustomPersistenceRepository getCustomRepository(Method method, Object[] args) throws IllegalArgumentException {
        String queryString = "";
        if (ObjectUtils.isEmpty(args)) {
            throw new IllegalArgumentException("fail invoke this method in method" + method.getName());
        }
        Object object = args[0];
        Class clazz;
        // 第一个参数 list
        Collection collection = (Collection) object;
        clazz = collection.iterator().next().getClass();
        for (Object o : collection) {
            queryString += PreparedStatementSQLConverter.deletePreparedStatementSQL(o) + " ; \n ";
        }
        CustomPersistenceRepository customPersistenceRepository = new CustomPersistenceRepository();
        customPersistenceRepository.setQueryString(queryString);
        customPersistenceRepository.setResultClass(clazz);
        return customPersistenceRepository;
    }

    @Override
    public Object execute(PreparedStatement preparedStatement, String resultType) throws SQLException {
        try {
            return preparedStatement.executeBatch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            preparedStatement.close();
        }
        return 0;

    }
}
