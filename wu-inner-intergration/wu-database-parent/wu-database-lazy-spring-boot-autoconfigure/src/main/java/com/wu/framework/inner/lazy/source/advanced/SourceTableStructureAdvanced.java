package com.wu.framework.inner.lazy.source.advanced;

import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * 表结构适配者
 *
 * @see SourceAdvanced
 */
public interface SourceTableStructureAdvanced {


    /**
     * 根据class解析出表名称 支持自定义忽略前缀、后缀
     *
     * @param clazz 需要解析的class
     * @return 返回表名称
     * @see LazyDatabaseJsonMessage#ddlIgnoredTablePrefix
     * @see LazyDatabaseJsonMessage#ddlIgnoredTableSuffix
     */
    String analyzeLazyTableName(Class<?> clazz);

    /**
     * 通过class 解析出对应的表结构
     *
     * @param clazz 需要解析的class
     * @return 表结构
     */
    LazyTableEndpoint analyzeLazyTableFromClass(Class<?> clazz);

    /**
     * 当前数据类型缓存的表结构
     *
     * @return 当前数据类型缓存的表结构
     */
    ConcurrentMap<Class<?>, LazyTableEndpoint> getTableCache();

    /**
     * 解析 class 表结构中的字段
     *
     * @param clazz              需要解析的class
     * @param tableFileIndexType 过滤字段索引类型
     * @param <T>                范型
     * @return 返回表结构中字段
     */
    <T> List<LazyTableFieldEndpoint> analyzeFieldOnAnnotation(Class<T> clazz, LayerField.LayerFieldType tableFileIndexType);

}
