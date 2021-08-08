package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 根据ID更新  自定义数据库持久层操作方法I按ID列表删除
 * @date : 2020/7/4 下午7:22
 */
@Deprecated
@Component
public class LazyOperationMethodDeleteByIdList extends AbstractLazyOperationMethod {


    @Override
    public PersistenceRepository analyzePersistenceRepository(Object param) throws IllegalArgumentException {
        String queryString = "";
        Object object = param;
        Class clazz;
        // 第一个参数 list
        Collection collection = (Collection) object;
        clazz = collection.iterator().next().getClass();
        for (Object o : collection) {
            queryString += deletePreparedStatementSQL(o) + " ; \n ";
        }
        PersistenceRepository persistenceRepository = new PersistenceRepository();
        persistenceRepository.setQueryString(queryString);
        persistenceRepository.setResultClass(clazz);
        return persistenceRepository;
    }

    /**
     * description 执行SQL 语句
     *
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/11/22 上午11:02
     *@param dataSource
     * @param sourceParams  */
    @Override
    public Object execute(DataSource dataSource, Object[] sourceParams) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(analyzePersistenceRepository(sourceParams[0]).getQueryString());
        try {
            return preparedStatement.executeBatch();
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            connection.close();
            preparedStatement.close();
        }
    }
}
