package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.database.domain.Page;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepositoryFactory;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * describe: 根据ID更新  自定义数据库持久层操作方法我选择列表
 * @date : 2020/7/4 下午7:22
 */
@Component
public class LazyOperationMethodPage extends AbstractLazyOperationMethod {

    public static final String COUNT_SQL = "select count(1)  from (%s) as derived_table ";
    public static final String LIMIT_SQL = "select * from (%s) derived_table limit %s,%s ";
    public static final String DELIMITER = ";";
    private final LazyOperationConfig operationConfig;
    private String countSql;
    private String limitSql;
    private Page page;

    public LazyOperationMethodPage(LazyOperationConfig operationConfig) {
        this.operationConfig = operationConfig;
    }

    /**
     * @param param
     * @return description 通过参数获取持久性存储库对象
     * @author Jia wei Wu
     * @date 2021/4/17 3:38 下午
     **/
    @Override
    public PersistenceRepository analyzePersistenceRepository(Object param) throws IllegalArgumentException {
        String queryString = "";
        Object[] p = (Object[]) param;
        page = (Page) p[0];
        Class clazz = (Class) p[1];
        String sqlFormat = (String) p[2];
        Object[] params = (Object[]) p[3];

        if (null == sqlFormat) {
            ClassLazyTableEndpoint lazyTableAnnotation = LazyTableUtil.analyzeLazyTable(clazz);
            sqlFormat = String.format("select * from %s ", lazyTableAnnotation.getFullTableName());
        }
        String listSql = loadSqlParameters(sqlFormat, params);
        countSql = loadSqlParameters(COUNT_SQL, listSql);
        limitSql = loadSqlParameters(LIMIT_SQL, listSql, (page.getCurrent() - 1) * page.getSize(), page.getSize());

        queryString = countSql + DELIMITER + limitSql;
        PersistenceRepository persistenceRepository = PersistenceRepositoryFactory.create(operationConfig);
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
    public Object execute(Connection connection, Object[] sourceParams) throws SQLException {
        Statement statement = null;
        PersistenceRepository persistenceRepository = analyzePersistenceRepository(sourceParams);
        try {
            count(connection);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(limitSql);
            List result = resultSetConverter(resultSet, persistenceRepository.getResultType());
            page.setRecord(result);
            return page;
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            assert statement != null;
            statement.close();
        }
        return page;
    }

    /**
     * 获取分页数据总量
     *
     * @param connection
     * @return
     * @author Jiawei Wu
     * @date 2021/1/24 9:27 下午
     **/
    private void count(Connection connection) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        PreparedStatement countPreparedStatement = connection.prepareStatement(countSql);
        ResultSet resultSet = countPreparedStatement.executeQuery();
        final List<Long> objects = resultSetConverter(resultSet, Long.class);
        long total = objects.get(0);
        long pages = (total + page.getSize() + 1) / page.getSize();
        page.setTotal(total);
        page.setPages(pages);
        countPreparedStatement.close();
    }

}
