package com.wu.framework.inner.lazy.database.sqlsession.defaults;

import com.wu.framework.inner.lazy.database.sqlsession.SqlSession;
import com.wu.framework.inner.lazy.database.sqlsession.SqlSessionFactory;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/6/25 下午11:10
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final SqlSession sqlSession;

    public DefaultSqlSessionFactory(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public SqlSession getSession() {
        return sqlSession;
    }
}
