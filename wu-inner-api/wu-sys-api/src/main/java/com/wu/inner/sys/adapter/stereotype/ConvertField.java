package com.wu.inner.sys.adapter.stereotype;

import java.lang.annotation.*;

/**
 * 转换字典项字段注解 需要对象中包含属性值为name
 */
@Documented
@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ConvertField {

    /**
     * 字典项类型 树状 或列表
     *
     * @return
     */
    DictionaryEntryType type() default DictionaryEntryType.LIST;

    /**
     * 字典项转换中文名称后的属性 默认是字典项+Name
     */
    String chineseNameProperty() default "";

    /**
     * 字典
     */
    String dictionaryItem() default "";

    /**
     *
     * 默认值
     */
    String defaultValue() default "";


    /**
     * 分割字符
     * @return
     */
    String [] dictionarySplitCharacter() default ",";


    /**
     * 字典项类型
     */
    enum DictionaryEntryType {
        /**
         * 树状
         */
        TREE,
        /**
         * 列表
         */
        LIST;

    }
}
