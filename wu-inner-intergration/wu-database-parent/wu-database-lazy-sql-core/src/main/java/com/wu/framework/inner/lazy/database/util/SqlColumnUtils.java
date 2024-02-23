package com.wu.framework.inner.lazy.database.util;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.config.enums.RowValueType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.Condition;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 字段解析工具
 */
public class SqlColumnUtils {

    /**
     * 获取insert 中的字段
     *
     * @param insertSql insert into table (xx,x) values(1,2)
     * @return
     */
    private static List<String> insertColumn(String insertSql) {
        List<String> columnNames = new ArrayList<>();

        // insert into table (xx,x) values(1,2)
        // 最大命中数
        // 命中截止数
        int hitNum = 0;
        String[] sqlWords = insertSql
                .replaceAll(NormalUsedString.BACKTICK, NormalUsedString.EMPTY)
                .replaceAll(NormalUsedString.NEWLINE, NormalUsedString.SPACE)
                .split(NormalUsedString.SPACE);
        for (String sqlWord : sqlWords) {
            // 空字符串
            if (ObjectUtils.isEmpty(sqlWord)) {
                continue;
            }
            //insert
            if (sqlWord.equalsIgnoreCase("INSERT")) {
                hitNum++;
                continue;
            }
            // into
            if (sqlWord.equalsIgnoreCase("INTO")) {
                hitNum++;
                continue;
            }
            if (sqlWord.equalsIgnoreCase("(")) {
                hitNum++;
                continue;
            }
            if (sqlWord.startsWith("values") || sqlWord.startsWith("VALUES")) {
                return columnNames;
            }
            // 命中两次获取临时表
            if (hitNum == 3) {
                columnNames.add(sqlWord);
                continue;
            }

        }
        return columnNames;
    }

    /**
     * 获取select中的条件字段
     *
     * @param selectSql select name from table where id>1
     * @return
     */
    private static List<Condition> selectConditionColumn(String selectSql) {
        List<Condition> conditionList = new ArrayList<>();
        // 命中截止数
        int hitNum = 0;
        String[] sqlWords = selectSql
                .replaceAll(NormalUsedString.BACKTICK, NormalUsedString.EMPTY)
                .replaceAll(NormalUsedString.NEWLINE, NormalUsedString.SPACE)
                .split(NormalUsedString.SPACE);
        Condition condition = null;
        for (String sqlWord : sqlWords) {
            // 空字符串
            if (ObjectUtils.isEmpty(sqlWord)) {
                continue;
            }
            //select
            if (sqlWord.equalsIgnoreCase("select")) {
                hitNum = 1;
                continue;
            }
            // from
            if (sqlWord.equalsIgnoreCase("from")) {
                hitNum = 2;
                continue;
            }
            // 命中两次遇到(
            if (hitNum == 2 && sqlWord.equalsIgnoreCase("(")) {
                hitNum = 0;
                continue;
            }
            // 命中两次遇到(select
            if (hitNum == 2 && sqlWord.equalsIgnoreCase("(select")) {
                hitNum = 1;
                continue;
            }

            // 遇到where
            if (hitNum == 2 && (sqlWord.equalsIgnoreCase("where") || sqlWord.equalsIgnoreCase("on"))) {
                hitNum = 3;
                continue;
            }
            // 命中三次已经取数据
            if (hitNum == 3 && (sqlWord.equalsIgnoreCase("left") || sqlWord.equalsIgnoreCase("right") || sqlWord.equalsIgnoreCase("join"))) {
                hitNum = 2;
                continue;
            }
            // where 条件 后的字段
            if (hitNum == 3) {
                condition = getConditionColumnName(sqlWord, condition);
                if (condition.satisfyGrammar()) {// 列字段 条件 数据 同时存在
                    conditionList.add(condition);
                    condition = null;
                }

                continue;
            }
        }
        return conditionList;
    }

    /**
     * 获取select中的字段
     *
     * @param selectSql select name from table where id>1
     * @return 查询select 中的字段名称
     */
    private static List<String> selectColumn(String selectSql) {
        List<String> columnNames = new ArrayList<>();
        // 命中截止数
        int hitNum = 0;
        String[] sqlWords = selectSql
                .replaceAll(NormalUsedString.BACKTICK, NormalUsedString.EMPTY)
                .replaceAll(NormalUsedString.NEWLINE, NormalUsedString.SPACE)
                .split(NormalUsedString.SPACE);
        for (String sqlWord : sqlWords) {
            // 空字符串
            if (ObjectUtils.isEmpty(sqlWord)) {
                continue;
            }
            //select
            if (sqlWord.equalsIgnoreCase("select")) {
                hitNum = 1;
                continue;
            }
            // from
            if (sqlWord.equalsIgnoreCase("from")) {
                hitNum = 2;
                continue;
            }
            // 命中两次遇到(
            if (hitNum == 2 && sqlWord.equalsIgnoreCase("(")) {
                hitNum = 0;
                continue;
            }
            // 命中两次遇到(select
            if (hitNum == 2 && sqlWord.equalsIgnoreCase("(select")) {
                hitNum = 1;
                continue;
            }
            // select  后的下一个字段 查询字段
            if (hitNum == 1) {
                columnNames.add(sqlWord);
                continue;
            }
        }
        return columnNames;
    }


    /**
     * 获取条件中的字段
     *
     * @param sqlWord 条件片段
     * @return
     */
    private static Condition getConditionColumnName(String sqlWord, Condition condition) {
        if (ObjectUtils.isEmpty(condition)) {
            condition = new Condition();
        }
        // 如果是 and 、or 丢弃
        if (Condition.SQL_SPLICING_SYMBOLS_LIST.contains(sqlWord)) {
            return condition;
        }
        // 设置 条件大于、小于、等于
        if (Condition.TYPE_LIST.contains(sqlWord)) {
            condition.setType(sqlWord);
            return condition;
        }
        // 看左边数据是否存在
        if (condition.getRowName() == null) {
            condition.setRowName(sqlWord);
            return condition;
        }
        // 右边数据
        if (condition.getRowValue() == null) {
            RowValueType rowValueType = RowValueType.STRING;
            if (sqlWord.startsWith("$")) {
                rowValueType = RowValueType.EXPRESSION;
                sqlWord = sqlWord.replaceFirst(NormalUsedString.DOLLAR_LEFT_BRACE, NormalUsedString.EMPTY).replace("}", NormalUsedString.EMPTY);
            }
            condition.setRowValue(sqlWord);
            condition.setRowValueType(rowValueType);
            return condition;
        }
        return condition;
    }

    /**
     * 获取update 中的条件字段字段
     *
     * @param updateSql update table set name='new_name' where id>1
     * @return name='new_name' id>1
     */
    private static List<Condition> updateConditionColumn(String updateSql) {
        List<Condition> columnNames = new ArrayList<>();
        // 命中截止数
        int hitNum = 0;
        // 命中忽略数
        int hitIgnoreNum = 0;
        String[] sqlWords = updateSql
                .replaceAll(NormalUsedString.BACKTICK, NormalUsedString.EMPTY)
                .replaceAll(NormalUsedString.NEWLINE, NormalUsedString.SPACE)
                .split(NormalUsedString.SPACE);
        Condition conditionColumnName = null;
        for (String sqlWord : sqlWords) {
            // 空字符串
            if (ObjectUtils.isEmpty(sqlWord)) {
                continue;
            }

            //UPDATE
            if (sqlWord.equalsIgnoreCase("UPDATE")) {
                hitNum = 1;
                continue;
            }
            // 命中一次表
            if (hitNum == 1) {
                hitNum = 2;
                continue;
            }
            // 命中 set
            if (hitNum == 2 && sqlWord.equalsIgnoreCase("set")) {
                hitNum = 3;
                continue;
            }

            //  where 后的条件字段
            if (hitNum == 3 && sqlWord.equalsIgnoreCase("where")) {
                hitNum = 4;
                continue;
            }
            if (hitNum == 3 || hitNum == 4) {
                conditionColumnName = getConditionColumnName(sqlWord, conditionColumnName);
                if (conditionColumnName.satisfyGrammar()) {
                    columnNames.add(conditionColumnName);
                    conditionColumnName = null;
                }
                continue;
            }

        }
        return columnNames;
    }

    /**
     * 获取update 中的字段
     *
     * @param updateSql update table set name='new_name' where id>1
     * @return
     */
    private static List<String> updateColumn(String updateSql) {
        List<String> columnNames = new ArrayList<>();
        // 命中截止数
        int hitNum = 0;
        // 命中忽略数
        int hitIgnoreNum = 0;
        String[] sqlWords = updateSql
                .replaceAll(NormalUsedString.BACKTICK, NormalUsedString.EMPTY)
                .replaceAll(NormalUsedString.NEWLINE, NormalUsedString.SPACE)
                .split(NormalUsedString.SPACE);
        for (String sqlWord : sqlWords) {
            // 空字符串
            if (ObjectUtils.isEmpty(sqlWord)) {
                continue;
            }

            //UPDATE
            if (sqlWord.equalsIgnoreCase("UPDATE")) {
                hitNum = 1;
                continue;
            }
            // 命中一次表
            if (hitNum == 1) {
                hitNum = 2;
                continue;
            }
            // 命中 set
            if (hitNum == 2 && sqlWord.equalsIgnoreCase("set")) {
                hitNum = 3;
                continue;
            }

            //  where 后的条件字段
            if (hitNum == 3 && sqlWord.equalsIgnoreCase("where")) {
                hitNum = 4;
                continue;
            }
            if (hitNum == 3) {
                columnNames.add(sqlWord);
                continue;
            }

        }
        return columnNames;
    }


    /**
     * description: 获取sql中条件
     *
     * @param sql 执行的sql
     * @return sql中的条件
     * @author 吴佳伟
     * @date: 17.1.23 19:14
     */
    public static List<Condition> columnConditionInSql(String sql) {
        List<Condition> updateConditionColumn = updateConditionColumn(sql);
        List<Condition> selectConditionColumn = selectConditionColumn(sql);
        List<Condition> tableColumns = new ArrayList<>();
        tableColumns.addAll(updateConditionColumn);
        tableColumns.addAll(selectConditionColumn);
        return tableColumns;
    }

    /**
     * description: 获取sql 类型
     *
     * @param sql 执行的sql
     * @return 执行sql类型
     * @author 吴佳伟
     * @date: 17.1.23 19:14
     */
    public static LambdaTableType executeTypeInSql(String sql) {
        sql = sql.trim();
        if (StringUtils.startsWithIgnoreCase(sql, LambdaTableType.DELETE.getValue())) {
            return LambdaTableType.DELETE;
        } else if (StringUtils.startsWithIgnoreCase(sql, LambdaTableType.INSERT.getValue())) {
            return LambdaTableType.INSERT;
        } else if (StringUtils.startsWithIgnoreCase(sql, LambdaTableType.UPDATE.getValue())) {
            return LambdaTableType.UPDATE;
        } else if (StringUtils.startsWithIgnoreCase(sql, LambdaTableType.CREATE.getValue())) {
            return LambdaTableType.CREATE;
        } else if (StringUtils.startsWithIgnoreCase(sql, LambdaTableType.SELECT.getValue())
                || StringUtils.startsWithIgnoreCase(sql, "show")
                || StringUtils.startsWithIgnoreCase(sql, "describe")
        ) {
            return LambdaTableType.SELECT;
        } else if (StringUtils.startsWithIgnoreCase(sql, "alert")) {
            return LambdaTableType.DDL;
        }
        return LambdaTableType.NONE;
    }

    /**
     * description: 获取sql中字段
     *
     * @param sql 执行的sql
     * @return sql中的字段
     * @author 吴佳伟
     * @date: 17.1.23 19:14
     */
    public static List<String> columnInSql(String sql) {
        List<String> insertColumn = insertColumn(sql);
        List<String> updateColumn = updateColumn(sql);
        List<String> selectColumn = selectColumn(sql);
        List<String> tableColumns = new ArrayList<>();
        tableColumns.addAll(insertColumn);
        tableColumns.addAll(updateColumn);
        tableColumns.addAll(selectColumn);
        return tableColumns;
    }


    /**
     * description: 获取sql中字段
     *
     * @param sqlList 执行的多个sql
     * @return 获取sql中字段
     * @author 吴佳伟
     * @date: 17.1.23 19:14
     */
    public static List<String> columnInSql(List<String> sqlList) {
        List<String> columnNames = new ArrayList<>();
        for (String sql : sqlList) {
            List<String> columnInSql = columnInSql(sql);
            columnNames.addAll(columnInSql);
        }
        return columnNames;
    }

}
