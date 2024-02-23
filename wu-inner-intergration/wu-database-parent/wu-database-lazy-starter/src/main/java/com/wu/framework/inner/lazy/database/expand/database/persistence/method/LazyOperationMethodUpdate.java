package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.FieldLazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/2 7:56 下午
 */
@Component
public class LazyOperationMethodUpdate extends AbstractLazyOperationMethod {
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

        if (param instanceof Object[]) {
            Object[] objects = (Object[]) param;
            for (Object o : objects) {
                Class<?> entityClass = (Class<?>) o;
                update(entityClass, connection);
            }
        } else {
            Class<?> entityClass = (Class<?>) param;
            update(entityClass, connection);
        }
        return true;
    }

    public void update(Class entityClass, Connection connection) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        final Statement statement = connection.createStatement();
        ClassLazyTableEndpoint classLazyTableEndpoint = LazyTableUtil.analyzeLazyTable(entityClass);
        final String schema = ObjectUtils.isEmpty(classLazyTableEndpoint.getSchema()) ? connection.getCatalog() : classLazyTableEndpoint.getSchema();
        // 更新表数据
        ResultSet resultSetLazyColumn = statement.executeQuery(
                loadSqlParameters("select * from information_schema.COLUMNS where table_schema='%s' and table_name='%s' ",
                        schema, classLazyTableEndpoint.getTableName()));


        List<LazyColumn> lazyColumnCollection = resultSetConverter(resultSetLazyColumn, LazyColumn.class);

        List<FieldLazyTableFieldEndpoint> currentColumnNameList = new ArrayList<>();
        for (LazyColumn lazyColumn : lazyColumnCollection) {
            FieldLazyTableFieldEndpoint convertedField = new FieldLazyTableFieldEndpoint();
            convertedField.setColumnName(lazyColumn.getColumnName());
            convertedField.setColumnType(lazyColumn.getColumnType());
            currentColumnNameList.add(convertedField);
        }
        String alterTableSQL = classLazyTableEndpoint.alterTableSQL(currentColumnNameList);
        if (!ObjectUtils.isEmpty(alterTableSQL)) {
            boolean execute = statement.execute(alterTableSQL);
        }
        statement.close();

        log.info("create table {} success", classLazyTableEndpoint.getTableName());
    }
}
