package com.wu.framework.inner.lazy.database.expand.database.persistence.translation.endpoint;

import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.api.LazyTranslationAPI;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * description  字段对应的映射表信息
 *
 * @author 吴佳伟
 * @date 2023/09/22 14:40
 * @see LazyTableTranslationOneField
 */
@Data
public class LazyTranslationTableEndpoint {


    /**
     * 表名称
     */
    private String translationTargetTableName;


    /**
     * 表名称 class 如果表名存在 优先
     */
    private Class<?> translationTargetTableClass;

    /**
     * translationTargetType 转换目标字段类型
     */
    private Class<?> translationTargetType;
    /**
     * 需要映射的字段
     */
    private String[] columnList;
    /**
     * 查询类型 字段、ALL
     */
    private Type type;

    /**
     * 映射使用的API
     */
    private Class<? extends LazyTranslationAPI> lazyTranslationAPIClass;

    /**
     * 原始数据名称
     */
    private String translationSourceName;


    /**
     * translationTargetName 目标数据名称（默认是当前字段名称）
     */
    private String translationTargetName;
    /**
     * sourceValues
     */
    private Set<Object> sourceValues = new HashSet<>();

    public enum Type {
        /**
         * 查询所有数据
         */
        ALL,
        /**
         * 查询指定字段
         */
        COLUMN,
    }

}
