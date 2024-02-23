package com.wu.framework.inner.lazy.persistence.conf;

import com.wu.framework.inner.lazy.config.enums.LazyDataSourceType;
import com.wu.framework.inner.lazy.persistence.conf.mysql.FieldLazyTableFieldEndpoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * description 更新插入json数据
 *
 * @author Jia wei Wu
 * @date 2020/9/7 下午2:19
 */
public class LazyDatabaseJsonMessage {
    /**
     * 忽略的字段
     */
    public static List<String> ignoredFields = Arrays.asList("serialVersionUID");

    /**
     * 特殊字段
     */
    public static List<String> specialFields = new ArrayList<String>() {
        {
            add("DESCRIBE");
            add("SCHEMA");
            add("SELECT");
            add("INSERT");
            add("UPDATE");
            add("DELETE");
            add("WHERE");
            add("ALTER");
            add("DESC");
            add("CURRENT_TIME");
            add("RANGE");
            add("CONTENT");
            add("SCHEMA");
            add("DESCRIBE");
            add("CURRENT_TIMESTAMP");
            add("CONDITION");
            add("SHOW");
            add("SYSTEM");
            add("FROM");
            add("TO");
        }
    };


    /**
     * 特殊schema
     */
    public static List<String> specialSchema = new ArrayList<String>() {
        {
            add("information_schema");
            add("mysql");
            add("sys");
            add("performance_schema");
        }
    };

    /**
     * 本地缓存实体class
     */
    public static List<Class<?>> localCacheEntityClass = new ArrayList<>();

    /**
     * DDL 忽略字段
     */
    public static List<String> ddlIgnoredFields = Arrays.asList("id");
    /**
     * DDL 忽略表前缀
     */
    public static List<String> ddlIgnoredTablePrefix = Arrays.asList();

    /**
     * DDL 忽略表后缀
     */
    public static List<String> ddlIgnoredTableSuffix = Arrays.asList("Uo", "DO", "UO");
    /**
     * 额外的字段
     */
    public static List<FieldLazyTableFieldEndpoint> extraFields = new ArrayList();
    /**
     * 数据库类型兼容建表
     */
    public static LazyDataSourceType lazyDataSourceType;

}
