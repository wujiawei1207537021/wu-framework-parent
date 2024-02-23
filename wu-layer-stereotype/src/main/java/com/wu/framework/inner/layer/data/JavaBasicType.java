package com.wu.framework.inner.layer.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Jiawei Wu
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
    Byte_S(Byte[].class, 0),
    Byte_M_S(byte[].class, 0),

    INTEGER(Integer.class, 0),
    INT(int.class, 0),
    BigInteger(java.math.BigInteger.class, 0),

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

    TIMESTAMP(Timestamp.class, Timestamp.valueOf(LocalDateTime.now())),
    TIME(Time.class, Time.valueOf(LocalTime.now())),

    DATE(Date.class, new Date()),
    SQL_DATE(java.sql.Date.class, java.sql.Date.valueOf(LocalDate.now())),


    ;

    public static Map<Class, Object> DEFAULT_VALUE_HASHMAP = new HashMap<Class, Object>();
    public static Map<String, Object> DEFAULT_CLASS_NAME_VALUE_HASHMAP = new HashMap<String, Object>();

    static {
        Arrays.stream(values()).forEach(javaBasicType -> DEFAULT_VALUE_HASHMAP.put(javaBasicType.clazz, javaBasicType.defaultValue));
        Arrays.stream(values()).forEach(javaBasicType -> DEFAULT_CLASS_NAME_VALUE_HASHMAP.put(javaBasicType.clazz.getName(), javaBasicType.defaultValue));
    }

    private Class clazz;
    private Object defaultValue;


    /**
     * 转换为基本数据类型对象
     *
     * @param
     * @return
     * @author Jiawei Wu
     * @date 2021/1/3 1:03 下午
     **/
    public static Object convertBasicTypeBean(Class clazz, Object obj) {
        if (null == obj) return JavaBasicType.DEFAULT_VALUE_HASHMAP.get(clazz);
        if (clazz.equals(Integer.class) || clazz.equals(int.class)) {
            return Integer.valueOf(obj.toString());
        } else if (clazz.equals(String.class)) {
            return obj.toString();
        } else if (clazz.equals(Long.class) || clazz.equals(long.class)) {
            return Long.valueOf(obj.toString());
        } else if (clazz.equals(Short.class) || clazz.equals(short.class)) {
            return Short.valueOf(obj.toString());
        } else if (clazz.equals(Double.class) || clazz.equals(double.class)) {
            return Double.valueOf(obj.toString());
        } else if (clazz.equals(Float.class) || clazz.equals(float.class)) {
            return Float.valueOf(obj.toString());
        } else if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
            return Boolean.valueOf(obj.toString());
        } else if (clazz.equals(Byte.class) || clazz.equals(byte.class)) {
            return Byte.valueOf(obj.toString());
        } else if (clazz.equals(Character.class) || clazz.equals(char.class)) {
            return obj;
        }
        return obj;
    }
}
