package com.wu.framework.inner.lazy.database.expand.database.persistence.method.dql;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationParameter;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.inner.lazy.source.SqlSourceClass;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.util.ObjectUtils;

import java.sql.*;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * describe: 分页查询
 * @date : 2020/7/4 下午7:22
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyOperationMethodPage extends AbstractLazyDQLOperationMethod {

    public static final String COUNT_SQL = "select count(1)  from ( {0} ) as derived_table ";
    public static final String LIMIT_SQL = "select * from ( {0} ) derived_table limit {1},{2} ";
    private String countSql;
    private String limitSql;
    private LazyPage lazyPage;

    public LazyOperationMethodPage(LazyOperationParameter lazyOperationParameter) {
        super(lazyOperationParameter);
    }

    /**
     * @param sourceParams 参数
     * @return description 通过参数获取持久性存储库对象
     * @author Jia wei Wu
     * @date 2021/4/17 3:38 下午
     **/
    @Override
    public PersistenceRepository doAnalyzePersistenceRepository(Object[] sourceParams) throws Exception {
        String queryString = "";
        Object[] p = sourceParams;
        lazyPage = (LazyPage) p[0];
        Class clazz = (Class) p[1];
        String sqlFormat = (String) p[2];
        Object[] params = (Object[]) p[3];

        if (null == sqlFormat) {
            SqlSourceClass sqlSourceClass =  SqlSourceClass.getInstance(clazz);
            LazyTableEndpoint lazyTableEndpoint = sqlSourceClass.getLazyTableEndpoint();
            sqlFormat = String.format("select * from %s ", lazyTableEndpoint.getFullTableName());
        }
        String listSql = loadSqlParameters(sqlFormat, params);
        countSql = loadSqlParameters(COUNT_SQL, listSql);
        limitSql = loadSqlParameters(LIMIT_SQL, listSql, (lazyPage.getCurrent() - 1) * lazyPage.getSize(), lazyPage.getSize());

        queryString = countSql + NormalUsedString.SEMICOLON + limitSql;
        List<String> ascs = lazyPage.getAscs();
        List<String> descs = lazyPage.getDescs();

        if (!ObjectUtils.isEmpty(ascs) || !ObjectUtils.isEmpty(descs)) {
            queryString = queryString + NormalUsedString.SPACE + " order by ";
        }
        if (!ObjectUtils.isEmpty(ascs)) {
            queryString = queryString + String.join(NormalUsedString.COMMA, ascs) + NormalUsedString.SPACE + NormalUsedString.ASC;
        }
        if (!ObjectUtils.isEmpty(descs)) {
            if (!ObjectUtils.isEmpty(ascs)) {
                queryString = NormalUsedString.COMMA + queryString + String.join(NormalUsedString.COMMA, ascs) + NormalUsedString.SPACE + NormalUsedString.ASC;
            } else {
                queryString = queryString + String.join(NormalUsedString.COMMA, ascs) + NormalUsedString.SPACE + NormalUsedString.ASC;
            }
        }


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
        Statement statement = null;
        PersistenceRepository persistenceRepository = null;
        try {
            persistenceRepository = analyzePersistenceRepository(sourceParams);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String queryString = persistenceRepository.getQueryString();
        try {
            count(connection);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(limitSql);
            List result = resultSetConverter(resultSet, persistenceRepository.getResultType());
            lazyPage.setRecord(result);
            return lazyPage;
        } catch (SQLException | NoSuchFieldException | InstantiationException | IllegalAccessException exception) {
            throw new SQLException(queryString, exception);
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
    private void count(Connection connection) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
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
