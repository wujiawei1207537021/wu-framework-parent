package com.wu.framework.inner.database;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 适配器
 * @date : 2020/8/28 下午11:10
 */
public interface CustomDataSourceAdapter {

    /**
     * 默认数据源
     */
    String DEFAULT_DATA_SOURCE = "simpleCustomDataSource";

    /**
     * 主数据源
     */
    String MASTER_DATA_SOURCE = "master";

    CustomDataSource getCustomDataSource();

}
