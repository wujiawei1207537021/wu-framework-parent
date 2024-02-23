package com.wu.framework.inner.lazy.config.enums;

import com.wu.framework.inner.lazy.source.adapter.SourceFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * describe : 数据库类型兼容建表
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/7/11 22:07
 * @see SourceFactory#getLazyDataSourceType(String)
 */
@Getter
@AllArgsConstructor
public enum LazyDataSourceType {
    MySQL("MySQL8.0", "mysql", "com.mysql.cj.jdbc.Driver", "com.mysql.cj.jdbc.MysqlDataSource"),
    H2("H2", "sql", "org.h2.Driver", "org.h2.jdbcx.JdbcDataSource"),
    SQLITE("SQLITE", "sql", "org.sqlite.JDBC", "org.sqlite.SQLiteDataSource"),
    CLICK_HOUSE("CLICK_HOUSE", "sql", "ru.yandex.clickhouse.ClickHouseDriver", "ru.yandex.clickhouse.ClickHouseDataSource"),
    POSTGRESQL("POSTGRESQL", "sql", "org.postgresql.Driver", "org.postgresql.ds.PGSimpleDataSource"),
    ;


    private final String typeName;

    /**
     * 类型
     */
    private final String type;
    /**
     * 驱动
     */
    private final String driver;
    /**
     * 数据源类型
     */
    private final String dataSourceClass;
}
