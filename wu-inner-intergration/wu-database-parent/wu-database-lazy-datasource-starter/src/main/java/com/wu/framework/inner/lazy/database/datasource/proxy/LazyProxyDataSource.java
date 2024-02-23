package com.wu.framework.inner.lazy.database.datasource.proxy;

import com.wu.framework.inner.lazy.database.datasource.proxy.connection.LazyDataSourceConnection;
import wu.framework.bean.wu2020.ReflexUtils;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;


public class LazyProxyDataSource implements DataSource {
    private final DataSource targetDataSource;

    public LazyProxyDataSource(DataSource dataSource) {
        this.targetDataSource = dataSource;
    }


    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = targetDataSource.getConnection();
        return new LazyDataSourceConnection(connection);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Connection connection = targetDataSource.getConnection(username, password);
        return new LazyDataSourceConnection(connection);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return targetDataSource.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return targetDataSource.isWrapperFor(iface);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return targetDataSource.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        targetDataSource.setLogWriter(out);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return targetDataSource.getLoginTimeout();
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        targetDataSource.setLoginTimeout(seconds);
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return targetDataSource.getParentLogger();
    }

    /**
     * 关闭数据源
     */
    public void close(){
        Method declaredMethod = ReflexUtils.findDeclaredMethod(targetDataSource.getClass(), "close");
        ReflexUtils.invokeDeclaredMethod(targetDataSource,declaredMethod);
    }
}
