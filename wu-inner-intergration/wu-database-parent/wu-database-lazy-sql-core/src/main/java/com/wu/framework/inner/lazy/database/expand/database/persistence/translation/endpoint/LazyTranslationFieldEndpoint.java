package com.wu.framework.inner.lazy.database.expand.database.persistence.translation.endpoint;

import com.wu.framework.inner.layer.data.translation.TranslationField;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.api.LazyTranslationAPI;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * description TranslationField注解对应的实体
 *
 * @author 吴佳伟
 * @date 2023/09/22 14:40
 * @see TranslationField
 */
@Data
public class LazyTranslationFieldEndpoint {


    private String translationSourceName;
    private String transferDataName;


    /**
     * 是否深度转译 true ： 当前字段含有@TranslationBean
     * false 普通字段转译 当前字段含有 @TranslationField
     */
    private Boolean deep;

    /**
     * translationTargetName 目标数据名称（默认是当前字段名称）
     */
    private String translationTargetName;

    /**
     * 转换目标表名称
     */
    private String translationTargetTableName;

    /**
     * 转换目标表class 如果字符串存在以字符串为准
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
    private LazyTranslationTableEndpoint.Type type;
    /**
     * 映射使用的API
     */
    private Class<? extends LazyTranslationAPI> lazyTranslationAPIClass;

    /**
     * 当前字段中包含多个对象需要转换
     */
    private List<LazyTranslationClassEndpoint> multipleLazyTranslationClassEndpointList = new ArrayList<>();

    private String[] convertSplitCharacter;


    /**
     * 当前class
     */
    private Class<?> targetClass;
    /**
     * 当前字段
     */
    private Field targetField;
    /**
     * 目标字段
     */
    private Field sourceField;

    /**
     * sourceValues
     */
    private Set<Object> sourceValues = new HashSet<>();

}
