package com.wu.framework.inner.lazy.database.util;

import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDataSourceContextHolder;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.lang.Nullable;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * description lazy 数据源管理工具
 *
 * @author 吴佳伟
 * @date 2023/05/13 10:58
 * @see DataSourceUtils
 */
public class LazyDataSourceUtils {


    /**
     * 通过datasource 获取当前数据源链接对象
     *
     * @param dataSource 数据源
     * @return
     * @throws CannotGetJdbcConnectionException
     */
    public static Connection getConnection(DataSource dataSource) throws CannotGetJdbcConnectionException {
        Connection connection = DataSourceUtils.getConnection(dataSource);

        // TODO connection is close
        DynamicLazyDataSourceContextHolder.push(dataSource);
        return connection;
    }

    /**
     * 判断当前链接是否在当前数据源中存在事务
     *
     * @param con
     * @param dataSource
     * @return
     */
    public static boolean isConnectionTransactional(Connection con, @Nullable DataSource dataSource) {
        return DataSourceUtils.isConnectionTransactional(con, dataSource);
    }

    /**
     * 从当前数据源释放 链接
     *
     * @param connection
     * @param dataSource
     */
    public static void releaseConnection(Connection connection, DataSource dataSource) {
        DynamicLazyDataSourceContextHolder.clear();
        DataSourceUtils.releaseConnection(connection, dataSource);
    }
}
