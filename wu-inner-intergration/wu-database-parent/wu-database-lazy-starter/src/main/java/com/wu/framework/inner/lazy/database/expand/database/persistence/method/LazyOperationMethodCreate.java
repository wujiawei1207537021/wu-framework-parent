package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/2 7:56 下午
 */
@Component
public class LazyOperationMethodCreate extends AbstractLazyOperationMethod {
    /**
     * @param param
     * @return description 通过参数获取持久性存储库对象
     * @author Jia wei Wu
     * @date 2021/4/17 3:38 下午
     **/
    @Override
    public PersistenceRepository analyzePersistenceRepository(Object param) throws Exception {
        return null;
    }

    /**
     * describe  执行SQL 语句
     *
     * @param connection
     * @param sourceParams
     * @return
     * @author Jia wei Wu
     * @date 2022/1/2 8:10 下午
     **/
    @Override
    public Object execute(Connection connection, Object[] sourceParams) throws Exception {
        // 创建表
        final Object param = sourceParams[0];
        final Statement statement = connection.createStatement();
        if (param instanceof Object[]) {
            Object[] objects = (Object[]) param;
            for (Object o : objects) {
                Class<?> entityClass = (Class<?>) o;
                ClassLazyTableEndpoint classLazyTableEndpoint = LazyTableUtil.analyzeLazyTable(entityClass);
                final String schema = ObjectUtils.isEmpty(classLazyTableEndpoint.getSchema()) ? connection.getCatalog() : classLazyTableEndpoint.getSchema();

                ResultSet resultSet = statement.executeQuery(loadSqlParameters("select * from information_schema.tables where table_schema='%s' and table_name='%s' ",
                        schema, classLazyTableEndpoint.getTableName()));

                List<LazyTableInfo> lazyTableInfos = resultSetConverter(resultSet, LazyTableInfo.class);

                if (!ObjectUtils.isEmpty(lazyTableInfos)) {
                    String createTableSQL = classLazyTableEndpoint.creatTableSQL();
                    for (String sql : createTableSQL.split(NormalUsedString.SEMICOLON)) {
                        statement.execute(sql);
                    }
                    log.info("create table {} success", classLazyTableEndpoint.getTableName());
                }


            }
        } else {
            Class<?> entityClass = (Class<?>) param;
            ClassLazyTableEndpoint classLazyTableEndpoint = LazyTableUtil.analyzeLazyTable(entityClass);
            final String schema = ObjectUtils.isEmpty(classLazyTableEndpoint.getSchema()) ? connection.getCatalog() : classLazyTableEndpoint.getSchema();
            ResultSet resultSet = statement.executeQuery(loadSqlParameters("select * from information_schema.tables where table_schema='%s' and table_name='%s' ",
                    schema, classLazyTableEndpoint.getTableName()));

            List<LazyTableInfo> lazyTableInfos = resultSetConverter(resultSet, LazyTableInfo.class);

            if (!ObjectUtils.isEmpty(lazyTableInfos)) {
                String createTableSQL = classLazyTableEndpoint.creatTableSQL();
                for (String sql : createTableSQL.split(NormalUsedString.SEMICOLON)) {
                    statement.execute(sql);
                }
                log.info("create table {} success", classLazyTableEndpoint.getTableName());
            }
        }
        return true;
    }
}
