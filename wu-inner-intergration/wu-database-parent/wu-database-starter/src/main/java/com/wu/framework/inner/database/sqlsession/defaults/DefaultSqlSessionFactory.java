package com.wu.framework.inner.database.sqlsession.defaults;

import com.wu.framework.inner.database.sqlsession.SqlSession;
import com.wu.framework.inner.database.sqlsession.SqlSessionFactory;

/**
 * @author : 吴佳伟
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
