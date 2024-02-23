package com.wu.framework.inner.lazy.persistence.conf;

import lombok.Data;

/**
 * describe : 获取数据源名称
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/29 23:04
 */
@Data
public abstract class AbstractLazyDynamicEndpoint implements LazyDynamicEndpoint {
    /**
     * 数据源名称
     */
    private String name;

    /**
     * 数据库
     */
    private String schema;

}
