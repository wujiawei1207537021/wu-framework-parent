package com.wu.framework.inner.dynamic.database.config;

import com.wu.framework.inner.database.EasyDataSource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : 多数据源
 * @date : 2020/9/13 下午5:14
 */
public class MultiDataSource {

    //    所有数据源
    public final Map<String, EasyDataSource> CUSTOM_DATA_SOURCE_MAP = new HashMap<>();
    //     主数据源
    private String master;
}
