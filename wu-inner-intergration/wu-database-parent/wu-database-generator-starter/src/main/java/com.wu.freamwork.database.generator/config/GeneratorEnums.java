package com.wu.freamwork.database.generator.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum GeneratorEnums {
    /**
     * #\u4EE3\u7801\u751F\u6210\u5668\uFF0C\u914D\u7F6E\u4FE1\u606F
     * <p>
     * mainPath=
     * #\u5305\u540D
     * package=org.hwj
     * moduleName=generator
     * #\u4F5C\u8005
     * author=huangwenjun
     * #Email
     * email=
     * #\u8868\u524D\u7F00(\u7C7B\u540D\u4E0D\u4F1A\u5305\u542B\u8868\u524D\u7F00)
     * tablePrefix=tb_
     */

    tinyint("tinyint", Integer.class),
    smallint("smallint", Integer.class),
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
    longtext("longtext", String.class),


    date("date", Date.class),
    datetime("datetime", Date.class),
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
    NVARCHAR2("DATE", String.class),
    CLOB("CLOB",String.class),
    BLOB("BLOB", String.class),


    DATE("DATE", Date.class),
    DATETIME("DATETIME", Date.class),
    TIMESTAMP("TIMESTAMP", Date.class),
    TIMESTAMP6("TIMESTAMP(6)", Date.class),


    INT8("int8", Long.class),
    INT4("int4", Integer.class),
    INT2("int2", Integer.class),
    NUMERIC("numeric", BigDecimal.class);

    private String type;
    private Class clazz;

    public static Class typeClazz(String type){
        for (GeneratorEnums generatorEnums : values()) {
            if(generatorEnums.type.equals(type)){
                return generatorEnums.clazz;
            }
        }
        return String.class;
    }
    @AllArgsConstructor
    @Getter
    public enum GeneratorTypeEnums {
        MySQL,ORACLE,SQL_SERVER, POSTGRES_SQL;

    }

}
