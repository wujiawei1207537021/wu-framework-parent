package com.wu.kafka.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DataType {
    STRING(String.class, "string"),
    INT(Integer.class, "int32");

    private Class clazz;
    private String alias;

    public static String getAlias(Class clazz){
        for (DataType value : values()) {
            if(value.getClazz() == clazz){
                return value.getAlias();
            }
        }
        return STRING.getAlias();
    }
}
