package com.wu.framework.inner.database.sqlsession;

public interface SqlSession {


    <T> T getRepository(Class<T> interfaceClass);

    /**
     * 释放资源
     */
    void close();
}
