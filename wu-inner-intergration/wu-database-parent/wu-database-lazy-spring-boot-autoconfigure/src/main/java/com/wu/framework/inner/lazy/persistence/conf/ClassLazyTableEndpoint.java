package com.wu.framework.inner.lazy.persistence.conf;


import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.persistence.analyze.SQLAnalyze;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 类懒表端点
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/23 4:39 下午
 */
@Data
public class ClassLazyTableEndpoint extends AbstractLazyTableEndpoint {

    /**
     * 包名 com.wu.framework.inner.lazy.persistence.conf
     */
    private String packageName;

    public String creatTableSQL() {
        StringBuilder createTableSQLBuffer = new StringBuilder(
                String.format(SQLAnalyze.SQL_DESC, getFullTableName(), getComment(), SQLAnalyze.AUTHOR, LocalDate.now()));

        if (!ObjectUtils.isEmpty(getSchema())) {
            createTableSQLBuffer.append(String.format(" CREATE DATABASE IF NOT EXISTS %s; \n use %s ; \n ", getSchema(), getSchema()));
        }
//        createTableSQLBuffer.append(String.format(SQLAnalyze.SQL_DROP, getTableName()));


        createTableSQLBuffer.append("CREATE TABLE `")
                .append(getTableName()).append("` ( \n");
        List<FieldLazyTableFieldEndpoint> fieldEndpoints = getFieldEndpoints();
        // 是否为唯一索引
        List<String> uniqueList = fieldEndpoints.stream().
                filter(LazyTableFieldEndpoint::isExist).
                filter(field -> LayerField.LayerFieldType.UNIQUE.equals(field.getFieldIndexType())).
                map(LazyTableFieldEndpoint::getColumnName).collect(Collectors.toList());

        // 添加字段

        fieldEndpoints.stream().
                filter(LazyTableFieldEndpoint::isExist).
                filter(field -> !SQLAnalyze.SQL_DEFAULT_FIELD.contains(field.getName())).
                forEach(field -> createTableSQLBuffer.append(field.createColumn()));
        createTableSQLBuffer.append(SQLAnalyze.SQL_DEFAULT_FIELD_STATEMENT);
//        UNIQUE KEY `plate_num_color` (`plate_num`,`plate_color`),
        if (!ObjectUtils.isEmpty(uniqueList)) {
            createTableSQLBuffer.append(" , UNIQUE KEY `");
            createTableSQLBuffer.append(String.join("_", uniqueList).replaceAll("`", ""));
            createTableSQLBuffer.append("` (`");
            createTableSQLBuffer.append(String.join("`,`", uniqueList));
            createTableSQLBuffer.append("`)");
        }
        createTableSQLBuffer.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='");
        createTableSQLBuffer.append(getComment()).append("';\n");
        createTableSQLBuffer.append("-- ------end \n" +
                "-- ——————————————————————————\n");
        System.out.println(createTableSQLBuffer);
        return createTableSQLBuffer.toString();
    }

    /**
     * // 添加列
     * //        ALTER TABLE tableName
     * //      ADD columnName VARCHAR(255) 'comment'
     *
     * @param currentColumnNameList 当前字段
     * @return
     * @author Jiawei Wu
     * @date 2020/12/31 9:32 下午
     **/
    /**
     * ♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬
     * ♬                                                                                                                                              ♬
     * ♬                                                                                                                                              ♬
     * ♬                                                                                                                                               ♬
     * ♬                                                                                                                                              ♬
     * ♬                                                                                                                                              ♬
     * ♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬
     */
    public String alterTableSQL(List<FieldLazyTableFieldEndpoint> currentColumnNameList) {
        String ALTER_TABLE = "ALTER TABLE %s ";
        // 字段名 字段类型 字段备注
        String ADD_FIELD = " ADD %s %s comment '%s' ";
        Map<String, FieldLazyTableFieldEndpoint> map = currentColumnNameList.stream().
                collect(Collectors.toMap(FieldLazyTableFieldEndpoint::getColumnName, convertedField -> convertedField, (A, B) -> A));

        String ADD_SQL = getFieldEndpoints().stream().
                filter(field -> !map.containsKey(field.getColumnName().replaceAll("`", ""))).
                map(convertedField ->
                        String.format(ADD_FIELD,
                                convertedField.getColumnName(),
                                convertedField.getColumnType(),
                                convertedField.getComment())).
                collect(Collectors.joining(","));
        if (ObjectUtils.isEmpty(ADD_SQL)) {
            return null;
        }
        // 更改列
        //        modify columnName varchar2(255)
        // 字段名 字段类型 字段备注
        String MODIFY_FIELD = " MODIFY %s %s %s ";

        final String alterSQL = String.join("", String.format(ALTER_TABLE, getFullTableName()), ADD_SQL, "");
        System.out.printf(
                "♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬\n" +
                        "     ♬                                                                                                                                              \n" +
                        "     ♬                                                                                                                                              \n" +
                        "     ♬                                                       %s                                                                                        \n" +
                        "     ♬                                                                                                                                              \n" +
                        "     ♬                                                                                                                                              \n" +
                        "♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬♬%n",
                alterSQL);
        return alterSQL;
    }

    /**
     * 获取指定注解的字段
     *
     * @param fieldType
     */
    @Override
    public List<FieldLazyTableFieldEndpoint> specifiedFieldAnnotation(LayerField.LayerFieldType fieldType) {
        List<FieldLazyTableFieldEndpoint> lazyTableFieldEndpoints = new ArrayList<>();
        final List<FieldLazyTableFieldEndpoint> fieldEndpoints = getFieldEndpoints();
        if (ObjectUtils.isEmpty(fieldEndpoints)) {
            return lazyTableFieldEndpoints;
        }
        lazyTableFieldEndpoints =
                fieldEndpoints.stream().filter(lazyTableFieldEndpoint -> fieldType.equals(lazyTableFieldEndpoint.getFieldIndexType())).collect(Collectors.toList());
        return lazyTableFieldEndpoints;
    }


//    public static class FieldLazyTableFieldEndpoint extends AbstractLazyTableFieldEndpoint {
//
//    }
}


