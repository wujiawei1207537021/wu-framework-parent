package com.wu.framework.inner.lazy.database.expand.database.persistence.method.dql;


import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationParameter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;

import java.sql.*;
import java.util.List;

@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyOperationMethodExecutePage extends AbstractLazyDQLOperationMethod {
    public static final String COUNT_SQL = "select count(1)  from ( {0} ) as derived_table ";
    public static final String LIMIT_SQL = "select * from ( {0} ) derived_table limit {1},{2} ";
    public LazyOperationMethodExecutePage(LazyOperationParameter lazyOperationParameter) {
        super(lazyOperationParameter);
    }

    /**
     * @param sourceParams 原始参数
     * @return description 通过参数获取持久性存储库对象
     * @author Jia wei Wu
     * @date 2021/4/17 3:38 下午
     **/
    @Override
    public PersistenceRepository doAnalyzePersistenceRepository(Object[] sourceParams) throws Exception {
        return (PersistenceRepository) sourceParams[0];
    }

    @Override
    public Object doExecute(Connection connection, Object[] sourceParams) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        LazyPage<?> lazyPage = (LazyPage<?>) sourceParams[1];

        Statement statement = null;
        PersistenceRepository persistenceRepository = null;
        try {
            persistenceRepository = analyzePersistenceRepository(sourceParams);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String listSql = persistenceRepository.getQueryString();
        String countSql = loadSqlParameters(COUNT_SQL, listSql);
        String limitSql = loadSqlParameters(LIMIT_SQL, listSql, (lazyPage.getCurrent() - 1) * lazyPage.getSize(), lazyPage.getSize());

        try {
            count(connection, countSql, lazyPage);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(limitSql);
            List result = resultSetConverter(resultSet, persistenceRepository.getResultType());
            lazyPage.setRecord(result);
            return lazyPage;
        } catch (SQLException | NoSuchFieldException | InstantiationException | IllegalAccessException exception) {
            throw new SQLException(listSql, exception);
        } finally {
            if (null != statement) {
                statement.close();
            }
        }
    }

    /**
     * 获取分页数据总量
     *
     * @param connection
     * @return
     * @author Jiawei Wu
     * @date 2021/1/24 9:27 下午
     **/
    private void count(Connection connection, String countSql, LazyPage<?> lazyPage) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        PreparedStatement countPreparedStatement = connection.prepareStatement(countSql);
        ResultSet resultSet = countPreparedStatement.executeQuery();
        final List<Long> objects = resultSetConverter(resultSet, Long.class);
        long total = objects.get(0);
        long pages = (total + lazyPage.getSize() + 1) / lazyPage.getSize();
        lazyPage.setTotal(total);
        lazyPage.setPages(pages);
        countPreparedStatement.close();
    }

}
