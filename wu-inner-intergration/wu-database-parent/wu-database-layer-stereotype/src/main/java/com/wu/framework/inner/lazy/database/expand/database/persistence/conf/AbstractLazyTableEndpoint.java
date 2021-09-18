package com.wu.framework.inner.lazy.database.expand.database.persistence.conf;


import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.ConvertedField;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public abstract class AbstractLazyTableEndpoint implements LazyTableEndpoint {

    /**
     * 类名
     */
    private String className;
    /**
     * 类
     */
    private Class clazz;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 描述信息
     */
    private String comment;


    /**
     * 字段
     */
    private List<LazyTableFieldEndpoint> fieldEndpoints;

    /**
     * 字典枚举
     */
    private Map<String, Map<String, String>> iEnumList;

    /**
     * 智能填字段
     */
    private boolean smartFillField;
    /**
     * 完善表
     */
    private boolean perfectTable;
    /**
     * 数据向下钻取
     */
    private boolean dataDrillDown;
    /**
     * 数据库名 schema
     */
    private String schema;



}
