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
}
