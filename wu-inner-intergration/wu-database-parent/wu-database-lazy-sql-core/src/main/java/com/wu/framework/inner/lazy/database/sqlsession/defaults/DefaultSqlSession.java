package com.wu.framework.inner.lazy.database.sqlsession.defaults;

import com.wu.framework.inner.lazy.database.proxy.RepositoryProxy;
import com.wu.framework.inner.lazy.database.sqlsession.SqlSession;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2020/6/25 下午11:09
 */
@Deprecated
public class DefaultSqlSession implements SqlSession {

    private final Connection connection;
    private final RepositoryProxy repositoryProxy;

    public DefaultSqlSession(DataSource dataSource, RepositoryProxy repositoryProxy) throws SQLException {
        this.connection = dataSource.getConnection();
        this.repositoryProxy = repositoryProxy;
    }


    @Override
    public <T> T getRepository(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class[]{interfaceClass}, repositoryProxy);
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
