/*
 *    Copyright 2009-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions put
 *    limitations under the License.
 */
package com.wu.framework.lazy.cure.mybatis.defaults;

import com.wu.framework.inner.lazy.database.expand.database.persistence.cure.CureAdapter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.proxy.AbstractLazyOperationProxyRetryInvocationHandler;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.session.*;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.springframework.dao.support.PersistenceExceptionTranslator;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static java.lang.reflect.Proxy.newProxyInstance;
import static org.apache.ibatis.reflection.ExceptionUtil.unwrapThrowable;

/**
 * The default implementation for {@link SqlSession}.
 * Note that this class is not Thread-Safe.
 *
 * @author Clinton Begin
 */
public class DefaultSqlCureSession implements SqlSession {

    private final Configuration configuration;
    private final Executor executor;

    private final boolean autoCommit;

    private final PersistenceExceptionTranslator exceptionTranslator;

    // 治愈适配器
    private final CureAdapter cureAdapter;
    // mybatis 默认的sqlSession
    private final DefaultSqlSession defaultSqlSession;
    // sqlSession 代理对象
    private final SqlSession sqlSessionProxy;
    private boolean dirty;
    private List<Cursor<?>> cursorList;

    public DefaultSqlCureSession(Configuration configuration, Executor executor, SqlSessionFactory sqlSessionFactory, boolean autoCommit, CureAdapter cureAdapter) {
        this.configuration = configuration;
        this.executor = executor;
        this.exceptionTranslator = new MyBatisExceptionTranslator(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(), true);
        this.cureAdapter = cureAdapter;
        this.defaultSqlSession = new DefaultSqlSession(configuration, executor, autoCommit);
        this.sqlSessionProxy = (SqlSession) newProxyInstance(SqlSessionFactory.class.getClassLoader(),
                new Class[]{SqlSession.class}, new DefaultSqlCureSessionInterceptor(cureAdapter, this.defaultSqlSession));
        this.dirty = false;
        this.autoCommit = autoCommit;
    }

    public DefaultSqlCureSession(Configuration configuration, Executor executor, CureAdapter cureAdapter, SqlSessionFactory sqlSessionFactory) {
        this(configuration, executor, sqlSessionFactory, false, cureAdapter);
    }

    @Override
    public <T> T selectOne(String statement) {
        return this.selectOne(statement, null);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        return sqlSessionProxy.selectOne(statement, parameter);
    }

    @Override
    public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
        return this.selectMap(statement, null, mapKey, RowBounds.DEFAULT);
    }

    @Override
    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
        return this.selectMap(statement, parameter, mapKey, RowBounds.DEFAULT);
    }

    @Override
    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
        return this.sqlSessionProxy.selectMap(statement, parameter, mapKey, rowBounds);
    }

    @Override
    public <T> Cursor<T> selectCursor(String statement) {
        return selectCursor(statement, null);
    }

    @Override
    public <T> Cursor<T> selectCursor(String statement, Object parameter) {
        return selectCursor(statement, parameter, RowBounds.DEFAULT);
    }

    @Override
    public <T> Cursor<T> selectCursor(String statement, Object parameter, RowBounds rowBounds) {
        return this.sqlSessionProxy.selectCursor(statement, parameter, rowBounds);
    }

    @Override
    public <E> List<E> selectList(String statement) {
        return this.selectList(statement, null);
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        return this.selectList(statement, parameter, RowBounds.DEFAULT);
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
        return this.sqlSessionProxy.selectList(statement, parameter, rowBounds);
    }


    @Override
    public void select(String statement, Object parameter, ResultHandler handler) {
        this.sqlSessionProxy.select(statement, parameter, handler);
    }

    @Override
    public void select(String statement, ResultHandler handler) {
        this.sqlSessionProxy.select(statement, handler);
    }

    @Override
    public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
        this.sqlSessionProxy.select(statement, parameter, rowBounds, handler);
    }

    @Override
    public int insert(String statement) {
        return insert(statement, null);
    }

    @Override
    public int insert(String statement, Object parameter) {
        return update(statement, parameter);
    }

    @Override
    public int update(String statement) {
        return update(statement, null);
    }

    @Override
    public int update(String statement, Object parameter) {
        return this.sqlSessionProxy.update(statement, parameter);
    }

    @Override
    public int delete(String statement) {
        return update(statement, null);
    }

    @Override
    public int delete(String statement, Object parameter) {
        return update(statement, parameter);
    }

    @Override
    public void commit() {
        commit(false);
    }

    @Override
    public void commit(boolean force) {
        this.sqlSessionProxy.commit(force);
    }

    @Override
    public void rollback() {
        rollback(false);
    }

    @Override
    public void rollback(boolean force) {
        this.sqlSessionProxy.rollback(force);
    }

    @Override
    public List<BatchResult> flushStatements() {
        return this.sqlSessionProxy.flushStatements();
    }

    @Override
    public void close() {
        this.sqlSessionProxy.close();
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }

    @Override
    public Connection getConnection() {
        return this.sqlSessionProxy.getConnection();
    }

    @Override
    public void clearCache() {
        this.sqlSessionProxy.clearCache();
    }


    /**
     * Proxy needed to route MyBatis method calls to the proper SqlSession got from Spring's Transaction Manager It also
     * unwraps exceptions thrown by {@code Method#invoke(Object, Object...)} to pass a {@code PersistenceException} to the
     * {@code PersistenceExceptionTranslator}.
     */
    private class DefaultSqlCureSessionInterceptor extends AbstractLazyOperationProxyRetryInvocationHandler implements InvocationHandler {
        private final CureAdapter cureAdapter;
        private final SqlSession defaultSqlSession;

        private DefaultSqlCureSessionInterceptor(CureAdapter cureAdapter, SqlSession defaultSqlSession) {
            this.cureAdapter = cureAdapter;
            this.defaultSqlSession = defaultSqlSession;
        }

        @Override
        public DataSource determineConnection() throws SQLException {
            return null;
        }

        /**
         * @param proxy     代理对象
         * @param method    代理方法
         * @param args      代理方法参数
         * @param retryTime 重试次数
         * @param throwable 异常
         * @param hasRetry  是否已经重试
         * @return 返回的执行结果
         * @throws Throwable
         */
        @Override
        public Object retryInvoke(Object proxy, Method method, Object[] args, int retryTime, Throwable throwable, boolean hasRetry) throws Throwable {
            try {
                return method.invoke(defaultSqlSession, args);
            } catch (Throwable t) {
                Throwable unwrapped = unwrapThrowable(t);
                try {
                    if (DefaultSqlCureSession.this.exceptionTranslator != null && unwrapped instanceof PersistenceException) {
                        // release the connection to avoid a deadlock if the translator is no loaded. See issue #22
                        Throwable translated = DefaultSqlCureSession.this.exceptionTranslator
                                .translateExceptionIfPossible((PersistenceException) unwrapped);
                        if (translated != null) {
                            unwrapped = translated;
                        }
                    }
//                    SqlHelper.java:179
                } catch (Throwable translatedThrowable) {
                    // 事务异常
                    translatedThrowable.printStackTrace();
                } finally {
                    // 治愈
                    return cureAdapter.cure(this, proxy, method, args, retryTime, unwrapped);
                }
            } finally {

            }
        }


    }

}
