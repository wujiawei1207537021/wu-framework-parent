package com.wu.framework.inner.lazy.database.expand.database.persistence.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jia wei Wu
 */
@Getter
@AllArgsConstructor
public enum LambdaTableType {
    NONE("NONE", "NONE"),
    DELETE("DELETE", "删除"),
    CREATE("CREATE", "创建"),
    INSERT("INSERT", "插入"),
    UPSERT("UPSERT", "upsert"),
    UPSERT_REMOVE_NULL("INSERT", "UPSERT_REMOVE_NULL"),
    @Deprecated
    SMART_UPSERT("INSERT", "SMART_UPSERT"),
    UPDATE("UPDATE", "更新"),
    SELECT("SELECT", "查询"),
    BATCH("BATCH", "查询"),
    SCRIPT_RUNNER("SCRIPT_RUNNER", "执行脚本文件"),
    STRING_SCRIPT_RUNNER("STRING_SCRIPT_RUNNER", "执行脚本字符串"),
    DDL("DDL", "DDL"),
    ;


    private String value;
    private String msg;
}