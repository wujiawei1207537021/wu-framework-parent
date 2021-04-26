package com.wu.framework.inner.lazy.database.expand.database.persistence.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.InputStream;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : Persistence  持久化SQL
 * @date : 2020/11/21 下午9:02
 */
@Accessors(chain = true)
@Data
public class Persistence {
    /**
     * 执行类型
     */
    private ExecutionEnum executionEnum;

    /**
     * 返回字段/插入字段/更新字段
     */
    private List<String> columnList;

    /**
     * 表名  查询(可以是临时表SQL)/更新/插入
     */
    private String tableName;

    /**
     * 条件 更新/查询/插入
     */
    private String condition;
    /**
     * 预留字段
     */
    private String reservedField;

    /**
     * 二进制数据
     */
    private List<InputStream> binaryList;

    /**
     * @author : Jia wei Wu
     * @version : 1.0
     * @describe: 执行类型
     * @date : 2020/11/21 下午9:06
     */
    @Getter
    @AllArgsConstructor
    public enum ExecutionEnum {
        SELECT("SELECT"),
        UPDATE("UPDATE"),
        DELETE("DELETE"),
        INSERT("INSERT INTO ");

        private String execution;
    }
}
