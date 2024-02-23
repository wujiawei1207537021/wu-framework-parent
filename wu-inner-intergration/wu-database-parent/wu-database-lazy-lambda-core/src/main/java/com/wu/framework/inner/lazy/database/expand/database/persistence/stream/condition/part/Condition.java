package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part;

import com.wu.framework.inner.lazy.config.enums.RowValueType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

@Accessors(chain = true)
@Data
public class Condition {
    public static List<String> TYPE_LIST = Arrays.asList(">=", "<=", "=", ">", "<", "in", "IN"); // sql 条件
    public static List<String> SQL_SPLICING_SYMBOLS_LIST = Arrays.asList("AND", "and", "OR", "or"); // sql 拼接符号
    /**
     * 模型对应字段名称
     */
    private String fieldName;
    // id
    private Object rowName;

    // >、 =、 <、 orderByDesc、 orderByAsc
    private String type;
    /**
     * 字段类型
     */
    private RowValueType rowValueType = RowValueType.STRING;
    // 1
    private Object rowValue;
    /**
     * EXPRESSION 方式下对应的模型对应字段名称
     *
     * @see Condition#rowValue
     */
    @Deprecated
    private String fieldValueName;
    /**
     * and or
     */
    private AndOr andOr = AndOr.AND;

    public Condition() {
    }

    public Condition(String type) {
        this.type = type;
    }

    public Condition copy() {
        Condition condition = new Condition();
        condition.setRowName(this.rowName);
        condition.setType(this.type);
        condition.setRowValue(this.rowValueType);
        condition.setRowValue(this.rowValue);
        condition.setFieldName(this.fieldName);
        condition.setFieldValueName(this.fieldValueName);
        return condition;
    }

    /**
     * 满足语法
     *
     * @return boolean
     */
    public boolean satisfyGrammar() {
        // 列 、 类型 、value 同时存在
        return this.rowName != null && this.type != null && this.rowValue != null;
    }
    public enum AndOr {
        AND,
        OR;
    }

}