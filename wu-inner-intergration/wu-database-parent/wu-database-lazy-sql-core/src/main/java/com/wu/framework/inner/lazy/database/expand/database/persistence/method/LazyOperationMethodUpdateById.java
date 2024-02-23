package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.persistence.converter.SQLConverter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * describe: 根据ID更新 自定义数据库持久层操作方法I通过ID更新
 * @date : 2020/7/4 下午7:22
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Deprecated
public class LazyOperationMethodUpdateById extends AbstractLazyOperationMethod {


    public LazyOperationMethodUpdateById(LazyOperationParameter lazyOperationParameter) {
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
        String queryString;
        Object object = sourceParams[0];
        Class<?> clazz = object.getClass();
        queryString = SQLConverter.updatePreparedStatementSQL(object);
        PersistenceRepository persistenceRepository = createPersistenceRepository();
        persistenceRepository.setQueryString(queryString);
        persistenceRepository.setResultClass(clazz);
        return persistenceRepository;
    }

    /**
     * description 执行SQL 语句
     *
     * @param connection
     * @param sourceParams
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/11/22 上午11:02
     */
    @Override
    public Object doExecute(Connection connection, Object[] sourceParams) throws SQLException {
        int updateRw = 0;
        for (Object param : sourceParams) {
            PersistenceRepository persistenceRepository = analyzePersistenceRepository(new Object[]{param});
            PreparedStatement preparedStatement = connection.prepareStatement(persistenceRepository.getQueryString());
            try {
                updateRw += preparedStatement.executeUpdate();
            } catch (SQLException sqlException) {
                throw sqlException;
            } finally {
                preparedStatement.close();
            }
        }
        return updateRw;
    }
}
