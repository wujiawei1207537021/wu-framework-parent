package com.wu.framework.inner.layer.data.translation.endpoint;

import com.wu.framework.inner.layer.data.translation.TranslationField;
import com.wu.framework.inner.layer.data.translation.api.TranslationAPI;
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
public class TranslationFieldEndpoint {

    //    private TranslationField.ConvertEntryType type;
    private String defaultValue;
//    private String chineseNameProperty;

    private String translationSourceName;
    private String transferDataName;


    /**
     * 是否深度转译 true ： 当前字段含有@TranslationBean
     * false 普通字段转译 当前字段含有 @TranslationField
     */
    private Boolean deep;

    private String translationTargetName;

    private Class<?> translationTargetType;
    /**
     * 注解字段使用的转换适配器API
     */
    private Class<? extends TranslationAPI> translationAPI;

    /**
     * 当前字段中包含多个对象需要转换
     */
    private List<TranslationClassEndpoint> multipleTranslationClassEndpointList = new ArrayList<>();

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
