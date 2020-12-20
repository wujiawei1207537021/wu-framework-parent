package com.wu.framework.inner.database.expand.database.persistence.method;

import com.wu.framework.inner.database.converter.PreparedStatementSQLConverter;
import com.wu.framework.inner.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.database.expand.database.persistence.stereotype.RepositoryOnDifferentMethods;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 根据ID更新 自定义数据库持久层操作方法I按ID更新全部
 * @date : 2020/7/4 下午7:22
 */
@RepositoryOnDifferentMethods(RepositoryOnDifferentMethods.LayerOperationMethodEnum.UPDATE_ALL_BY_ID_LIST)
public class LayerOperationMethodIUpdateAllById extends AbstractLayerOperationMethod {

    @Override
    public PersistenceRepository getPersistenceRepository(Method method, Object[] args) throws IllegalArgumentException {
        String queryString = "";
        Object object = args[0];
        Class clazz;
        // 第一个参数 list
        if (object instanceof Collection && !ObjectUtils.isEmpty(args)) {
            Collection collection = (Collection) object;
            clazz = collection.iterator().next().getClass();
            for (Object o : collection) {
                queryString += PreparedStatementSQLConverter.updatePreparedStatementSQL(o) + " ; \n ";
            }
            PersistenceRepository persistenceRepository = new PersistenceRepository();
            persistenceRepository.setQueryString(queryString);
            persistenceRepository.setResultClass(clazz);
            return persistenceRepository;
        } else {
            throw new IllegalArgumentException("fail invoke this method in method" + method.getName());
        }
    }

    /**
     * description 执行SQL 语句
     *
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/11/22 上午11:02
     **/
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
