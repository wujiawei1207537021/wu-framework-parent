package com.wu.framework.inner.lazy.database.expand.database.persistence.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 */
@Getter
@AllArgsConstructor
public enum LambdaTableType {
    DELETE("delete", "删除"),
    INSERT("insert", "插入"),
    UPDATE("update", "更新"),
    SELECT("select", "查询");
    private String value;
    private String msg;
}