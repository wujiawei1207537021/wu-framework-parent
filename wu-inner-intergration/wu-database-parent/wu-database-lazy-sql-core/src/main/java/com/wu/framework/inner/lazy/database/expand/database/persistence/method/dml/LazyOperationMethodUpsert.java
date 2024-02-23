package com.wu.framework.inner.lazy.database.expand.database.persistence.method.dml;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.AbstractLazyOperationMethod;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationParameter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.factory.LazyTableUpsertConverterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : upsert 操作
 * @date : 2020/7/3 下午10:28
 */
@Slf4j
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyOperationMethodUpsert extends AbstractLazyOperationMethod {

    public LazyOperationMethodUpsert(LazyOperationParameter lazyOperationParameter) {
        super(lazyOperationParameter);
    }


    /**
     * @param sourceParams 参数实体对象的任意状态数组或者是集合
     * @return description 通过参数获取持久性存储库对象
     * @author Jia wei Wu
     * @date 2021/4/17 3:38 下午
     **/
    @Override
    public PersistenceRepository doAnalyzePersistenceRepository(Object[] sourceParams) throws Exception {

        PersistenceRepository persistenceRepository = createPersistenceRepository();
        for (Object sourceParam : sourceParams) {
            String sql = LazyTableUpsertConverterFactory.upsert(sourceParam);
            persistenceRepository.setExecutionType(LambdaTableType.UPSERT);
            persistenceRepository.setQueryString(sql);
            return persistenceRepository;
        }

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
    public Object doExecute(Connection connection, Object[] sourceParams) throws Exception {
        Object param = sourceParams[0];
        if (param instanceof Object[]) {
            Object[] upsertList = (Object[]) param;
            for (Object upsert : upsertList) {
                accurateExecution(connection, upsert);
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
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/4/26 5:12 下午
     */
    @Override
    public Object accurateExecution(Connection connection, Object param) throws Exception {
        PersistenceRepository persistenceRepository = analyzePersistenceRepository(new Object[]{param});
        String queryString = persistenceRepository.getQueryString();
        if (connection.isClosed()) {
            log.error("connection is closed");
        }
        PreparedStatement preparedStatement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);
        try {
            final boolean execute = preparedStatement.execute();
//            final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
//            while (generatedKeys.next()) {
//                ResultSetMetaData resultSetMetaData = generatedKeys.getMetaData();
//                //取出总列数
//                int columnCount = resultSetMetaData.getColumnCount();
//                if (columnCount >= 1) {
//                    final String string = generatedKeys.getString(1);
//                    System.out.println(string);
//                }
//
//            }
            return execute;
        } catch (SQLException sqlException) {
            throw new SQLException(queryString, sqlException);
        } finally {
            preparedStatement.close();
        }
    }
}
