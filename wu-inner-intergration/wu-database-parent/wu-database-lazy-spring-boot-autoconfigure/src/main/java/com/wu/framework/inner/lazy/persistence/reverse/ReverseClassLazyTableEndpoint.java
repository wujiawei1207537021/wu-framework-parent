package com.wu.framework.inner.lazy.persistence.reverse;

import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import lombok.Data;

import java.util.List;

/**
 * 逆向工程 表信息
 */
@Data
public class ReverseClassLazyTableEndpoint {
    /**
     * 类名
     */
    private String className;
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 描述信息
     */
    private String comment;
    /**
     * 数据库名 schema
     */
    private String schema;
    /**
     * 包名 com.wu.framework.inner.lazy.persistence.conf
     */
    private String packageName;

    /**
     * 输出字段
     */
    private List<LazyTableFieldEndpoint> inLazyTableFieldEndpoints;

    /**
     * 输入字段
     */
    private List<LazyTableFieldEndpoint> outLazyTableFieldEndpoints;

}
