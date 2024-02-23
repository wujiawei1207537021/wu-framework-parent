package com.wu.framework.inner.lazy.persistence.conf;


import com.wu.framework.inner.lazy.stereotype.LazyDS;

/**
 * 获取数据源名称
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/29 23:06
 * @see LazyDS
 */
public interface LazyDynamicEndpoint {

    /**
     * 获取数据源名称
     *
     * @return String 数据源名称
     */
    String getName();

    /**
     * 数据库
     * <p>
     * url设置样例：jdbc:mysql://${MAIN_DB_HOST}/upms?allowMultiQueries=true&useUnicode=true&autoReconnect=true&useAffectedRows=true&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&databaseTerm=SCHEMA
     * <p>
     * 必须含有：databaseTerm=SCHEMA
     * </p>
     *
     * @return
     */
    String getSchema();
}
