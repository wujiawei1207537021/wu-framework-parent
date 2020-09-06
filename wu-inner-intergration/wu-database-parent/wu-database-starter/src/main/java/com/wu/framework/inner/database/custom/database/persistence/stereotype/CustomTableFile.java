package com.wu.framework.inner.database.custom.database.persistence.stereotype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface CustomTableFile {

    @AliasFor(attribute = "name")
    String value() default "";

    /**
     * 数据库name
     *
     * @return
     */
    @AliasFor(attribute = "value")
    String name() default "";

    /**
     * 数据格式
     *
     * @return
     */
    String dataType() default "";

    String type() default "";

    /***
     * 数据库字段注释
     * @return
     */
    String comment() default "";

    /**
     * 是否存在
     *
     * @return
     */
    boolean exist() default true;


    /**
     * 索引类型
     *
     * @return
     */
    CustomTableFileIndexType indexType() default CustomTableFileIndexType.FILE_TYPE;


    /**
     * @author : 吴佳伟
     * @version : 1.0
     * @describe: 索引类型
     * @date : 2020/7/31 下午9:19
     */
    enum CustomTableFileIndexType {
        FILE_TYPE,
        ID,
        UNIQUE,
        AUTOMATIC;
    }


    @Getter
    @AllArgsConstructor
    enum FileType {
        STRING(Arrays.asList(String.class), " varchar(255) "),
        BYTE(Arrays.asList(Byte.class, byte.class), " varbinary(1024) "),
        INTEGER(Arrays.asList(Integer.class, int.class), " int(11) "),
        LONG(Arrays.asList(Long.class, long.class), " bigint "),
        LOCAL_DATE_TIME(Arrays.asList(LocalDateTime.class, Timestamp.class), " datetime "),
        LOCAL_DATE(Arrays.asList(LocalDate.class, Date.class), " date "),
        DOUBLE(Arrays.asList(Double.class, double.class), " double "),
        FLOAT(Arrays.asList(Float.class, float.class), " float ");
        private List<Class> clazz;
        private String type;

        public static String getTypeByClass(Class clazz) {
            for (FileType fileType : values()) {
                if (fileType.clazz.contains(clazz)) {
                    return fileType.type;
                }
            }
            return STRING.type;
        }
    }
}
