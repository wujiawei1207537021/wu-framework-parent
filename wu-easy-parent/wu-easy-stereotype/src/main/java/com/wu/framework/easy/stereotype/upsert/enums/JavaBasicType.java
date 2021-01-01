package com.wu.framework.easy.stereotype.upsert.enums;

import com.wu.framework.easy.stereotype.upsert.entity.EasyHashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

/**
 * @author : 吴佳伟
 * @version : 1.0
 * @describe: Java 基础Class 对应默认值
 * @date : 2021/1/1 1:10 下午
 */
@Getter
@AllArgsConstructor
public enum JavaBasicType {
    STRING(String.class, ""),
    BOOLEAN(Boolean.class, false),
    BOOLEAN_M(boolean.class, false),

    Byte(Byte.class, 0),
    Byte_M(byte.class, 0),

    INTEGER(Integer.class, 0),
    INT(int.class, 0),

    LONG(Long.class, 0L),
    LONG_M(long.class, 0L),

    SHORT(Short.class, 0),
    SHORT_M(short.class, 0),

    CHARACTER(Character.class, 0),
    CHAR(char.class, 0),

    FLOAT(Float.class, 0F),
    FLOAT_M(float.class, 0f),

    DOUBLE(Double.class, 0D),
    DOUBLE_M(double.class, 0d),

    BIG_DECIMAL(BigDecimal.class, 0),


    LOCAL_DATE(LocalDate.class, LocalDate.now()),
    LOCAL_DATE_TIME(LocalDateTime.class, LocalDateTime.now()),

    DATE(Date.class, new Date()),
    SQL_DATE(java.sql.Date.class, java.sql.Date.valueOf(LocalDate.now())),

    ;
    public static EasyHashMap DEFAULT_VALUE_HASHMAP = new EasyHashMap<Class, Object>();

    static {
        Arrays.stream(values()).forEach(javaBasicType -> DEFAULT_VALUE_HASHMAP.put(javaBasicType.clazz, javaBasicType.defaultValue));
    }

    private Class clazz;
    private Object defaultValue;
}
