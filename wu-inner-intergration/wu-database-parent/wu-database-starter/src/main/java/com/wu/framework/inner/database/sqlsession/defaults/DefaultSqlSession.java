package com.wu.framework.inner.database.sqlsession.defaults;

import com.wu.framework.inner.database.CustomDataSourceAdapter;
import com.wu.framework.inner.database.proxy.RepositoryProxy;
import com.wu.framework.inner.database.sqlsession.SqlSession;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2020/6/25 下午11:09
 */
public class DefaultSqlSession implements SqlSession {

    private final Connection connection;
    private final RepositoryProxy repositoryProxy;

    public DefaultSqlSession(CustomDataSourceAdapter customDataSourceAdapter, RepositoryProxy repositoryProxy) throws SQLException {
        this.connection = customDataSourceAdapter.getCustomDataSource().getConnection();
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
