package com.wu.framework.easy.upsert.autoconfigure;

import com.wu.framework.inner.layer.data.DefaultIEnum;
import com.wu.framework.inner.layer.data.IEnum;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * description 数据库字段注解  支持兼容 @JsonProperty
 *
 * @author Jia wei Wu
 * @date 2020/7/16 上午9:17
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@LayerField
@LazyTableField
public @interface EasySmartField {

    /**
     * 字段名
     * 删除原因 多个容易模糊啊
     */
    @Deprecated
    @AliasFor(attribute = "name", annotation = LayerField.class)
    String value() default "";

    @AliasFor(attribute = "name", annotation = LayerField.class)
    String name() default "";

    /**
     * 字段类型
     */
    // 数据库字段 当前支持 varchar number  int 默认是 varchar
    @AliasFor(attribute = "columnType", annotation = LazyTableField.class)
    String columnType() default "";

    /**
     * 字段描述
     */
    String comment() default "";

    /**
     * 是否存在
     */
    boolean exist() default true;


    /**
     * 字段版本
     */
    int version() default 1;

    int scale() default 0;

    /**
     * 参数
     */
    String parameters() default "";

    /**
     * 可选的
     */
    boolean optional() default true;

    /**
     * 数据为空的时候使用字段默认值
     */
    @AliasFor(annotation = LazyTableField.class, attribute = "fieldDefaultValue")
    String fieldDefaultValue() default "";

    /**
     * 转换指定类型的枚举 DefaultIEnum 不转换  转换失败默认是-1
     */
    @AliasFor(annotation = LazyTableField.class, attribute = "iEnum")
    Class<? extends IEnum> iEnum() default DefaultIEnum.class;

    /**
     * 数据格式
     */
    String dataType() default "";

    /**
     * 转换内容分隔符
     *
     * @return
     * @see IEnum#getConvertContentSeparator()
     */
    @Deprecated
    String[] convertContentSeparator() default {","};

    /**
     * 通过获取字典类型转换
     *
     * @return
     */
    String convert() default "";


    /**
     * 字段索引类型(数据库)
     */
    @AliasFor(attribute = "indexType", annotation = LayerField.class)
    LayerField.LayerFieldType indexType() default LayerField.LayerFieldType.NONE;


    @AllArgsConstructor
    @Getter
    enum JavaSchemaDataType {
        /**
         * 字符串
         */
        STRING(String.class, "string"),
        /**
         * 数字 int
         */
        INT(Integer.class, "int32"),
        /**
         * 数字 long
         */
        LONG(Long.class, "int64"),
        /**
         * 但精度
         */
        FLOAT(Float.class, "float32"),
        /**
         * 双精度
         */
        DOUBLE(Double.class, "double"),
        /**
         * 布尔类型
         */
        BOOLEAN(Boolean.class, "boolean"),
        /**
         * 时间格式 LocalDateTime
         */
        LOCAL_DATETIME(LocalDateTime.class, "boolean"),
        /**
         * 日期格式 LocalDate
         */
        LOCAL_DATE(LocalDate.class, "LocalDate"),
        DATE(Date.class, "Date"),
        TIMESTAMP(Timestamp.class, "Timestamp"),
        TIME(Time.class, "time");

        private static final Map<Class, String> JAVA_SCHEMA_TYPE =
                Arrays.stream(values()).
                        collect(Collectors.toMap(JavaSchemaDataType::getClazz, JavaSchemaDataType::getAlias));
        private Class clazz;
        private String alias;

        public static String getAlias(Class clazz) {
            return JAVA_SCHEMA_TYPE.getOrDefault(clazz, STRING.alias);
        }

        public static String getESAlias(Class clazz) {
            for (JavaSchemaDataType value : values()) {
                if (value.getClazz() == clazz) {
                    return value.getAlias();
                }
            }
            return STRING.getAlias();
        }
    }

}