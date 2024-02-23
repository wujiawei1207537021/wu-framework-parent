package com.wu.framework.inner.lazy.config.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据库字段类型枚举
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2021/11/14 8:00 下午
 */
@Getter
@AllArgsConstructor
public enum MysqlColumnTypeEnum {

    // -127~127
    tinyint("tinyint", Boolean.class, -128, 127),
    smallint("smallint", Integer.class, -32768, 32767),
    mediumint("mediumint", Integer.class),
    int1("int", Integer.class),
    integer("integer", Integer.class),
    bigint("bigint", Long.class),
    float1("float", Float.class),
    double1("double", Double.class),
    decimal("decimal", BigDecimal.class),
    bit("bit", Boolean.class),


    CHAR1("char", String.class),
    varchar("varchar", String.class),
    tinytext("tinytext", String.class),
    text("text", String.class),
    mediumtext("mediumtext", String.class),
    LONG_TEXT("longtext", String.class),


    date("date", Date.class),
    datetime("datetime", LocalDateTime.class),
    timestamp("timestamp", Date.class),
    NUMBER("NUMBER", Integer.class),

    INT("INT", Integer.class),
    INTEGER("INTEGER", Integer.class),
    BINARY_INTEGER("BINARY_INTEGER", Integer.class),


    LONG("LONG", String.class),
    FLOAT("FLOAT", Float.class),
    BINARY_FLOAT("BINARY_FLOAT", Float.class),


    DOUBLE("DOUBLE", Double.class),
    BINARY_DOUBLE("BINARY_DOUBLE", Double.class),
    DECIMAL("DECIMAL", BigDecimal.class),


    CHAR("CHAR", String.class),
    VARCHAR("VARCHAR", String.class),
    VARCHAR2("VARCHAR2", String.class),

    NVARCHAR("NVARCHAR", String.class),
    //    NVARCHAR2("DATE", String.class),
    CLOB("CLOB", String.class),
    BLOB("BLOB", String.class),


    DATE("DATE", Date.class),
    DATETIME("DATETIME", LocalDateTime.class),
    TIMESTAMP("TIMESTAMP", Date.class),
    TIMESTAMP6("TIMESTAMP(6)", Date.class),


    INT8("int8", Long.class),
    INT4("int4", Integer.class),
    INT2("int2", Integer.class),
    NUMERIC("numeric", BigDecimal.class),
    JSON("json", Map.class),
    blob("blob", File.class),

    ;
    /**
     * 序号	名称	描述	定义方式	格式	范围
     * 1	date	日期	date	YYYY-MM-DD	'1000-01-01' to '9999-12-31'
     * 2	time	时间	time[.fraction]	hh:mm:ss[.000000]	'-838:59:59.000000' to '838:59:59.000000'
     * 3	datetime	日期+时间	datetime[.fraction]	YYYY-MM-DD hh:mm:ss[.000000]	'1000-01-01 00:00:00' to '9999-12-31 23:59:59'
     * 4	timestamp	时间戳	timestamp[.fraction]	YYYY-MM-DD hh:mm:ss[.000000]	'1970-01-01 00:00:01' UTC to '2038-01-19 03:14:07' UTC
     * 5	year	年	year	YYYY	'1901' to '2155'
     */
    public static Map<String, MysqlColumnTypeEnum> MYSQL_COLUMN_TYPE_ENUM_MAP =
            Arrays.stream(values()).collect(Collectors.toMap(MysqlColumnTypeEnum::getDataType, mysqlColumnTypeEnum -> mysqlColumnTypeEnum));
    // 字段类型
    private String dataType;
    // 对应的java类型
    private Class javaType;
    // 起源
    private Integer origin;
    // 边界
    private Integer bound;

    MysqlColumnTypeEnum(String dataType, Class javaType) {
        this.dataType = dataType;
        this.javaType = javaType;
        this.origin = 1;
        this.bound = 50;
    }

}
