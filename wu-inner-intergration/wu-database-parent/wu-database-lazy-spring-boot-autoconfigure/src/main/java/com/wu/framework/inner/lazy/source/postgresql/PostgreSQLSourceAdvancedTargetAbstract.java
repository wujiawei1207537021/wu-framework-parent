package com.wu.framework.inner.lazy.source.postgresql;

import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.inner.lazy.source.advanced.SourceAdvanced;
import com.wu.framework.inner.lazy.source.advanced.SourceAdvancedAbstract;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * @see PostgreSQLSourceAdvancedTarget
 * @see SourceAdvancedAbstract
 * @see SourceAdvanced
 */
public abstract class PostgreSQLSourceAdvancedTargetAbstract extends SourceAdvancedAbstract implements SourceAdvanced {
    /**
     * 根据class解析出表名称 支持自定义忽略前缀、后缀
     *
     * @param clazz 需要解析的class
     * @return 返回表名称
     * @see LazyDatabaseJsonMessage#ddlIgnoredTablePrefix
     * @see LazyDatabaseJsonMessage#ddlIgnoredTableSuffix
     */
    @Override
    public String analyzeLazyTableName(Class<?> clazz) {
        return null;
    }

    /**
     * 通过class 解析出对应的表结构
     *
     * @param clazz 需要解析的class
     * @return 表结构
     */
    @Override
    public LazyTableEndpoint analyzeLazyTableFromClass(Class<?> clazz) {
        return null;
    }

    /**
     * 当前数据类型缓存的表结构
     *
     * @return 当前数据类型缓存的表结构
     */
    @Override
    public ConcurrentMap<Class<?>, LazyTableEndpoint> getTableCache() {
        return null;
    }

    /**
     * 解析 class 表结构中的字段
     *
     * @param clazz              需要解析的class
     * @param tableFileIndexType 过滤字段索引类型
     * @return 返回表结构中字段
     */
    @Override
    public <T> List<LazyTableFieldEndpoint> analyzeFieldOnAnnotation(Class<T> clazz, LayerField.LayerFieldType tableFileIndexType) {
        return null;
    }
}
