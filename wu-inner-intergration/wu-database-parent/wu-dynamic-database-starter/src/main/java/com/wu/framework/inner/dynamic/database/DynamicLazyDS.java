package com.wu.framework.inner.dynamic.database;

import javax.sql.DataSource;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe : 动态数据源适配器
 * @date : 2021/7/4 6:18 下午
 */
public interface DynamicLazyDS {

    /**
    * @describe 确定数据源
    * @param
    * @return
    * @author Jia wei Wu
    * @date 2021/7/4 6:19 下午
    **/
    DataSource determineDataSource();

}
