package com.wu.framework.inner.layer.data.dictionary;

import com.wu.framework.inner.layer.data.translation.TranslationField;
import com.wu.framework.inner.layer.stereotype.LayerField;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 转换字段注解 需要对象中包含属性值为name
 *
 * @see TranslationField
 */
@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@LayerField
public @interface ConvertField {

    /**
     * 字典项类型 树状 或列表
     *
     * @return
     */
    TranslationField.ConvertEntryType type() default TranslationField.ConvertEntryType.LIST;

    /**
     * 字典项转换中文名称后的属性
     * <p>
     * 如果是字典转换成中文 默认是字典项+Name
     * </p>
     * <p>
     * 如果是中文转换成字典 默认当前属性-Name
     * </p>
     */
    @AliasFor(attribute = "afterConvertNameProperty")
    String chineseNameProperty() default "";

    /**
     * 字典项转换后的属性
     * <p>
     * 如果是字典转换成中文 默认是字典项+Name
     * </p>
     * <p>
     * 如果是中文转换成字典 默认当前属性-Name
     * </p>
     */
    @AliasFor(attribute = "chineseNameProperty")
    String afterConvertNameProperty() default "";
    /**
     * 字典
     */
    String convertItem() default "";

    /**
     * 默认值
     */
    String defaultValue() default "";


    /**
     * 分割字符
     *
     * @return String[]
     */
    String[] convertSplitCharacter() default ",";

    /**
     * 字典转译方式
     * <p>
     * 默认字典转中文
     * </p>
     *
     * @return ConvertDictionaryWay
     */
    ConvertDictionaryWay convertDictionaryWay() default ConvertDictionaryWay.DICTIONARY_TO_CHINESE;

    /**
     * 字典转译方式
     */
    enum ConvertDictionaryWay {
        /**
         * 中文转字典
         */
        CHINESE_TO_DICTIONARY,
        /**
         * 字典转中文
         */
        DICTIONARY_TO_CHINESE;

    }
}
