package com.wu.framework.inner.lazy.database.expand.database.persistence.method.dql;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationParameter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.database.util.SqlColumnUtils;
import com.wu.framework.inner.lazy.persistence.util.SqlMessageFormatUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.util.ObjectUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :  自定义数据库持久层操作方法执行SQL
 * @date : 2020/7/3 下午10:28
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyOperationMethodExecuteSQL extends AbstractLazyDQLOperationMethod {


    public LazyOperationMethodExecuteSQL(LazyOperationParameter lazyOperationParameter) {
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
        // 第一个参数 SQL
        Object[] p = sourceParams;
        String sourceSql = (String) p[0];
        Class clazz = (Class) p[1];

        Object[] params = (Object[]) p[2];
        String sql;
        if (ObjectUtils.isEmpty(params)) {
            sql = sourceSql;
        } else {
            sql = SqlMessageFormatUtil.format(sourceSql, params);
        }
        // 去掉字符串前面的空格
        sql = sql.trim();

        PersistenceRepository persistenceRepository = createPersistenceRepository();
        LambdaTableType lambdaTableType = SqlColumnUtils.executeTypeInSql(sql);
        persistenceRepository.setExecutionType(lambdaTableType);
        persistenceRepository.setQueryString(sql);
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
    public Object doExecute(Connection connection, Object[] sourceParams) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        PersistenceRepository persistenceRepository = null;
        try {
            persistenceRepository = analyzePersistenceRepository(sourceParams);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String queryString = persistenceRepository.getQueryString();
        PreparedStatement preparedStatement = connection.prepareStatement(queryString);
        try {
            if (persistenceRepository.getExecutionType().equals(LambdaTableType.SELECT)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSetConverter(resultSet, persistenceRepository.getResultType());
            } else if (persistenceRepository.getExecutionType().equals(LambdaTableType.UPDATE) ||
                    persistenceRepository.getExecutionType().equals(LambdaTableType.DELETE)) {
                int update = preparedStatement.executeUpdate();
                return List.of(update);
            } else if (persistenceRepository.getExecutionType().equals(LambdaTableType.INSERT)) {
                int update = preparedStatement.executeUpdate();
                return List.of(update);
            } else {
                for (String s : queryString.split(";")) {
                    preparedStatement.addBatch(s);
                }
                preparedStatement.executeLargeBatch();
                return true;
            }
        } catch (SQLException sqlException) {
            throw new SQLException(queryString, sqlException);
        } finally {
            preparedStatement.close();
        }
    }
}
