package com.wu.framework.inner.lazy.database.util;

import com.wu.framework.inner.layer.data.NormalUsedString;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * description sql 解析工具
 *
 * @author 吴佳伟
 * @date 2023/01/16 23:49
 */
public class SqlUtils {


    /**
     * 通过错误信息 获取表
     *
     * @param errorMessage Table 'acw1.access_token' doesn't exist
     * @return tableName
     */
    private static String errorMessageWithTable(String errorMessage) {
        if (errorMessage.startsWith("Table") && errorMessage.endsWith("doesn't exist")) {
            return errorMessage
                    .replaceFirst("Table", "")
                    .replace("doesn't exist", "")
                    .replaceAll("'", "");
        } else {
            return null;
        }

    }

    /**
     * description: 解析insert sql中的table信息
     *
     * @param insertSql
     * @return
     * @author 吴佳伟
     * @date: 17.1.23 00:03
     */
    private static List<String> insertTable(String insertSql) {
        List<String> tableNames = new ArrayList<>();

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
            if (sqlWord.equalsIgnoreCase("into")) {
                hitNum++;
                continue;
            }
            // 命中两次获取临时表
            if (hitNum == 2) {
                tableNames.add(sqlWord);
                hitNum++;
                continue;
            }
        }
        return tableNames;
    }

    /**
     * description: 解析update sql中table信息
     * <p>
     * UPDATE product p, product_price pp SET pp.price = p.price * 0.8 WHERE p.productid= pp.productId;
     * UPDATE product p INNER JOIN product_price pp ON p.productid= pp.productid SET pp.price = p.price * 0.8;
     * UPDATE product p LEFT JOIN product_price pp ON p.productid= pp.productid SET p.isdelete = 1 WHERE pp.productid IS NULL;
     * </p>
     *
     * @param updateSql
     * @return
     * @author 吴佳伟
     * @date: 17.1.23 00:04
     */
    private static List<String> updateTable(String updateSql) {
        List<String> tableNames = new ArrayList<>();
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

            if (sqlWord.equalsIgnoreCase("ON")) {
                hitIgnoreNum = 1;
                continue;
            }
            if (hitIgnoreNum == 1 && sqlWord.equalsIgnoreCase("DUPLICATE")) {
                hitIgnoreNum = 2;
                continue;
            }
            if (hitIgnoreNum == 2 && sqlWord.equalsIgnoreCase("KEY")) {
                hitIgnoreNum = 3;
                continue;
            }
            if (hitIgnoreNum == 3 && sqlWord.equalsIgnoreCase("UPDATE")) {
                hitIgnoreNum = 0;
                continue;
            }

            //UPDATE
            if (sqlWord.equalsIgnoreCase("UPDATE")) {
                hitNum = 1;
                continue;
            }
            // 命中一次获取临时表
            if (hitNum == 1) {
                tableNames.add(sqlWord);
                hitNum++;
                continue;
            }
            // 命中两次后遇到,
            if (hitNum == 2 && (sqlWord.equalsIgnoreCase(",") || sqlWord.contains(",")) && !sqlWord.contains("=")) {
                hitNum = 1;
                continue;
            }

            // 命中两次后遇到 JOIN
            if (hitNum == 2 && sqlWord.equalsIgnoreCase("JOIN")) {
                hitNum = 1;
                continue;
            }
        }
        return tableNames;
    }

    /**
     * description: 解析select sql中的table信息
     * <p>
     * select * from students,course ;
     * select * from students inner join course;
     * select * from (select * from table) as temp
     *
     * </p>
     *
     * @param selectSql
     * @return
     * @author 吴佳伟
     * @date: 17.1.23 00:05
     */
    private static List<String> selectTable(String selectSql) {
        List<String> tableNames = new ArrayList<>();
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
            // 命中两次获取临时表
            if (hitNum == 2) {
                tableNames.add(sqlWord);
                hitNum = 3;
                continue;
            }
            // 命中三次已经取数据
            if (hitNum == 3 && (sqlWord.equalsIgnoreCase(",") || sqlWord.equalsIgnoreCase("join"))) {
                hitNum = 2;
                continue;
            }
        }
        return tableNames;
    }

    /**
     * description:  根据sql 解析出schema
     *
     * <p>
     * Failed to obtain JDBC Connection; nested exception is java.sql.SQLSyntaxErrorException: Unknown database 'acw'
     * </p>
     *
     * @param sql
     * @return
     * @author 吴佳伟
     * @date: 8.2.23 18:24
     */
    public static List<String> schema(String sql) {
        //show tables from demo
        List<String> schemas = new ArrayList<>();
        // 命中截止数
        int hitNum = 0;
        String[] sqlWords = sql
                .replaceAll(NormalUsedString.BACKTICK, NormalUsedString.EMPTY)
                .replaceAll(NormalUsedString.NEWLINE, NormalUsedString.SPACE)
                .split(NormalUsedString.SPACE);
        for (String sqlWord : sqlWords) {
            // 空字符串
            if (ObjectUtils.isEmpty(sqlWord)) {
                continue;
            }
            // from
            if (sqlWord.equalsIgnoreCase("from")) {
                hitNum = 2;
                continue;
            }
            // Unknown database
            if (sqlWord.equalsIgnoreCase("Unknown")) {
                hitNum = 1;
                continue;
            }
            if (hitNum == 1 && sqlWord.equalsIgnoreCase("database")) {
                hitNum = 2;
                continue;
            }
            if (hitNum == 2) {
                // 添加数据库
                if (sqlWord.contains(NormalUsedString.DOT)) {
                    String[] split = sqlWord.split("\\.");
                    schemas.add(split[0].replaceAll(NormalUsedString.SINGLE_QUOTE, NormalUsedString.EMPTY));
                } else {
                    schemas.add(sqlWord.replaceAll(NormalUsedString.SINGLE_QUOTE, NormalUsedString.EMPTY));
                }

            }
        }
        return schemas;
    }

    /**
     * description: 获取sql中表名称
     *
     * @param sql
     * @return
     * @author 吴佳伟
     * @date: 17.1.23 19:14
     */
    public static List<String> tablesInSql(String sql) {
        List<String> insertTable = insertTable(sql);
        List<String> updateTable = updateTable(sql);
        List<String> selectTable = selectTable(sql);
        String errorTable = errorMessageWithTable(sql);

        List<String> tableNames = new ArrayList<>();
        if (errorTable != null) {
            tableNames.add(errorTable);
        }
        tableNames.addAll(insertTable);
        tableNames.addAll(updateTable);
        tableNames.addAll(selectTable);
        return tableNames;
    }

    /**
     * description: 获取sql中表名称
     *
     * @param sqlList
     * @return
     * @author 吴佳伟
     * @date: 17.1.23 19:14
     */
    public static List<String> tablesInSql(List<String> sqlList) {
        List<String> tableNames = new ArrayList<>();
        for (String sql : sqlList) {
            List<String> tablesInSql = tablesInSql(sql);
            tableNames.addAll(tablesInSql);
        }
        return tableNames;
    }


}

