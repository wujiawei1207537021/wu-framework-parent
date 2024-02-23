package com.wu.framework.inner.lazy.database.expand.database.persistence.method.dml;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.AbstractLazyOperationMethod;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationParameter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.factory.LazyTableUpsertConverterFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :  自定义数据库持久层操作方法插入
 * @date : 2020/7/3 下午10:28
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyOperationMethodInsert extends AbstractLazyOperationMethod {


    public LazyOperationMethodInsert(LazyOperationParameter lazyOperationParameter) {
        super(lazyOperationParameter);
    }


    /**
     * description 通过参数获取持久性存储库对象
     *
     * @param sourceParams
     * @return
     * @author Jia wei Wu
     * @date 2021/4/17 3:38 下午
     **/
    @Override
    public PersistenceRepository doAnalyzePersistenceRepository(Object[] sourceParams) throws Exception {
        // TODO 深度解析问题
        String sql = LazyTableUpsertConverterFactory.insert(sourceParams[0]);
        PersistenceRepository persistenceRepository = createPersistenceRepository();
        persistenceRepository.setExecutionType(LambdaTableType.INSERT);
        persistenceRepository.setQueryString(sql);

        return persistenceRepository;
    }

    /**
     * description 执行SQL 语句
     *
     * @param connection
     * @param sourceParams
     * @author Jia wei Wu
     * @date 2020/11/22 上午11:02
     */
    @Override
    public Object doExecute(Connection connection, Object[] sourceParams) throws Exception {
        Object param = sourceParams[0];

        if (param instanceof Object[]) {
            Object[] insertList = (Object[]) param;
            for (Object insert : insertList) {
                accurateExecution(connection, insert);
            }
        } else {
            accurateExecution(connection, param);
        }
        return sourceParams.length;
    }

    /**
     * @param connection 数据源
     * @param param      单个对象或是单条记录
     * @return describe 精准执行
     * @author Jia wei Wu
     * @date 2021/4/26 5:12 下午
     */
    @Override
    public Object accurateExecution(Connection connection, Object param) throws Exception {
        PersistenceRepository persistenceRepository = analyzePersistenceRepository(new Object[]{param});
        String queryString = persistenceRepository.getQueryString();
        PreparedStatement preparedStatement = connection.prepareStatement(queryString);

        try {
            final boolean execute = preparedStatement.execute();
            return execute;
        } catch (Exception e) {
            throw new SQLException(queryString, e);

        } finally {
            preparedStatement.close();
        }
    }

}
