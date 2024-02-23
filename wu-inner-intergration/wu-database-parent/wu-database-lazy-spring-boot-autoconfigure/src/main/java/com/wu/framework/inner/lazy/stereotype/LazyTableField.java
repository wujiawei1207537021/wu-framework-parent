package com.wu.framework.inner.lazy.stereotype;

import com.wu.framework.inner.layer.data.DefaultIEnum;
import com.wu.framework.inner.layer.data.IEnum;
import com.wu.framework.inner.layer.stereotype.LayerField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.io.File;
import java.io.InputStream;
import java.lang.annotation.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * description 数据库字段注解  支持兼容 @JsonProperty
 *
 * @author Jia wei Wu
 * @date 2020/7/16 上午9:17
 * @see com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
@LayerField
public @interface LazyTableField {

    /**
     * 字段名
     */
    @AliasFor(attribute = "name", annotation = LayerField.class)
    String value() default "";

    @AliasFor(attribute = "value", annotation = LayerField.class)
    String name() default "";

    /**
     * 字段别名
     *
     * @see LambdaBasicComparison#as()
     */
    @Deprecated
    String alias() default "";

    /**
     * 数据库字段类型
     */
    String columnType() default "";

    /**
     * 字段描述
     */
    String comment() default "";

    /**
     * 不是空
     *
     * @return boolean
     */
    boolean notNull() default false;

    /**
     * 字段默认数值
     */
    String defaultValue() default "";

    /**
     * 是否为主键
     *
     * @return boolean
     */
    boolean key() default false;

    /**
     * extra 额外的数据 如 auto_increment、DEFAULT_GENERATED on update CURRENT_TIMESTAMP
     *
     * @return String
     */
    String extra() default "";

    /**
     * 特权 如 select,insert,update,references
     *
     * @return Privilege[]
     */
    Privilege[] privileges() default {Privilege.select, Privilege.insert, Privilege.update, Privilege.references};

    /**
     * 是否存在
     */
    boolean exist() default true;

    /**
     * upsert 时候字段策略，对应生成 upsert 的sql
     *
     * @return LazyFieldStrategy
     */
    LazyFieldStrategy upsertStrategy() default LazyFieldStrategy.NO_VERIFY;

    /**
     * update 时候字段策略，对应生成 update  的sql
     *
     * @return LazyFieldStrategy
     */
    LazyFieldStrategy updateStrategy() default LazyFieldStrategy.IGNORED_NULL;

    /**
     * 字段版本
     */
    int version() default 1;

    /**
     * 字段规模 对应的column_scale
     *
     * @return long
     */
    long scale() default 0L;

    /**
     * 序号 数值越大越靠前
     */
    int serialNumber() default 0;

    /**
     * 参数
     */
    String parameters() default "";

    /**
     * 可选的
     */
    boolean optional() default true;

    /**
     * 数据为空的时候使用字段默认值（插入数据时使用 即将弃用）
     */
    @Deprecated
    String fieldDefaultValue() default "";

    /**
     * 转换指定类型的枚举 DefaultIEnum 不转换  转换失败默认是-1
     */
    Class<? extends IEnum> iEnum() default DefaultIEnum.class;

    /**
     * 数据格式
     */
    String dataType() default "";

    /**
     * 转换内容分隔符
     *
     * @return String[]
     * @see IEnum#getConvertContentSeparator()
     */
    @Deprecated
    String[] convertContentSeparator() default {","};

    /**
     * 通过获取字典类型转换
     *
     * @return String
     */
    String convert() default "";


//    /**
//     * 字段索引类型(数据库)
//     */
//    @AliasFor(attribute = "indexType", annotation = LayerField.class)
//    LayerField.LayerFieldType indexType() default LayerField.LayerFieldType.NONE;
//
//    /**
//     * 索引名称 当索引类型不是NONE 时有效
//     */
//    @AliasFor(attribute = "indexName", annotation = LayerField.class)
//    String indexName() default "";

    /**
     * 数据库索引
     *
     * @return LazyTableIndex[]
     */
    LazyTableIndex[] lazyTableIndexs() default {@LazyTableIndex};

    @Getter
    @AllArgsConstructor
    enum FieldType {
        STRING(Collections.singletonList(String.class), " varchar(255) "),
        BYTE(Arrays.asList(Byte.class, byte.class), " varbinary(1024) "),
        BYTE_ARRAYS(Arrays.asList(Byte[].class, byte[].class), " varbinary(1024) "),
        INTEGER(Arrays.asList(Integer.class, int.class), " int(11) "),
        LONG(Arrays.asList(Long.class, long.class), " bigint "),
        BOOLEAN(Arrays.asList(Boolean.class, boolean.class), " tinyint(1) "),
        LOCAL_DATE_TIME(Arrays.asList(LocalDateTime.class, Timestamp.class), " datetime "),
        TIME(Arrays.asList(LocalTime.class), " time "),
        LOCAL_DATE(Arrays.asList(LocalDate.class, java.sql.Date.class, java.util.Date.class), " date "),
        DOUBLE(Arrays.asList(Double.class, double.class), " double "),
        FLOAT(Arrays.asList(Float.class, float.class), " float "),
        BINARY(Arrays.asList(File.class, InputStream.class), " Blob "),
        JSON(Arrays.asList(List.class, Arrays.class, Collection.class, Object.class), " json "),

        ;
        private static final Map<Class, String> TYPE_MAP = new HashMap<>();

        static {
            for (FieldType fieldType : values()) {
                Map<Class, String> classStringMap =
                        fieldType.clazz.stream().collect(Collectors.toMap(Function.identity(), c -> fieldType.type));
                TYPE_MAP.putAll(classStringMap);
            }
        }

        private List<Class> clazz;
        private String type;

        public static String getTypeByClass(Class clazz) {
            for (FieldType fieldType : values()) {
                if (fieldType.clazz.contains(clazz)) {
                    return fieldType.type;
                }
            }
            return STRING.type;
        }
    }

    /**
     * 字段权限
     */
    @Getter
    @AllArgsConstructor
    enum Privilege {
        // select,insert,update,references
        select, insert, update, references;


    }


}
