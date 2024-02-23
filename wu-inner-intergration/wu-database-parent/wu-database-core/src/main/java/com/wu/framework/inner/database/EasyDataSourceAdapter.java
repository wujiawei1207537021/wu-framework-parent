package com.wu.framework.inner.database;

import com.wu.framework.inner.database.config.ICustomDatabaseConfiguration;

import javax.sql.DataSource;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : 适配器
 * @date : 2020/8/28 下午11:10
 */
public interface EasyDataSourceAdapter {

    /**
     * 默认数据源
     */
    String DEFAULT_DATA_SOURCE = "dataSource";

    String SIMPLE_DATA_SOURCE = "simpleCustomDataSource";
    /**
     * 主数据源
     */
    String MASTER_DATA_SOURCE = "master";

    /**
     * 创建数据连接对象
     *
     * @param iCustomDatabaseConfiguration
     * @return
     */
    static DataSource createDataSource(ICustomDatabaseConfiguration iCustomDatabaseConfiguration) {
        return new SimpleEasyDataSource(iCustomDatabaseConfiguration);
    }

    DataSource getEasyDataSource();

}
