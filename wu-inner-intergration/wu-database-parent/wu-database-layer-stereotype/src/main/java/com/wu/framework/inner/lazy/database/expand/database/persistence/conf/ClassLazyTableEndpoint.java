package com.wu.framework.inner.lazy.database.expand.database.persistence.conf;


import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.ConvertedField;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.wu.framework.inner.lazy.database.expand.database.persistence.analyze.SQLAnalyze.*;

public class ClassLazyTableEndpoint extends AbstractLazyTableEndpoint {


    public String creatTableSQL() {
        StringBuilder createTableSQLBuffer = new StringBuilder(
                String.format(SQL_DESC, getTableName(), getComment(), AUTHOR, LocalDate.now()));
        createTableSQLBuffer.append(String.format(SQL_DROP, getTableName()));
        createTableSQLBuffer.append("CREATE TABLE `").append(getTableName()).append("` ( \n");
        // 是否为唯一索引
        List<String> uniqueList = getFieldEndpoints().stream().
                filter(LazyTableFieldEndpoint::isExist).
                filter(field -> LayerField.LayerFieldType.UNIQUE.equals(field.getFieldIndexType())).
                map(LazyTableFieldEndpoint::getName).collect(Collectors.toList());

        // 添加字段
        getFieldEndpoints().stream().
                filter(LazyTableFieldEndpoint::isExist).
                filter(field -> !SQL_DEFAULT_FIELD.contains(field.getName())).
                forEach(field -> createTableSQLBuffer.append(field.createColumn()));
        createTableSQLBuffer.append(SQL_DEFAULT_FIELD);
//        UNIQUE KEY `plate_num_color` (`plate_num`,`plate_color`),
        if (!ObjectUtils.isEmpty(uniqueList)) {
            createTableSQLBuffer.append(" , UNIQUE KEY `");
            createTableSQLBuffer.append(String.join("_", uniqueList));
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
    public String alterTableSQL(List<ConvertedField> currentColumnNameList) {
        String ALTER_TABLE = "ALTER TABLE %s ";
        String ADD_FIELD = " ADD %s %s comment '%s' "; // 字段名 字段类型 字段备注
        Map<String, ConvertedField> map = currentColumnNameList.stream().
                collect(Collectors.toMap(ConvertedField::getConvertedFieldName, convertedField -> convertedField));

        String ADD_SQL = getFieldEndpoints().stream().
                filter(field -> !map.containsKey(field.getName().replaceAll("`", ""))).
                map(convertedField ->
                        String.format(ADD_FIELD,
                                convertedField.getName(),
                                convertedField.getType(),
                                convertedField.getComment())).
                collect(Collectors.joining(","));
        if (ObjectUtils.isEmpty(ADD_SQL)) {
            return null;
        }
        // 更改列
        //        modify columnName varchar2(255)
        String MODIFY_FIELD = " MODIFY %s %s %s "; // 字段名 字段类型 字段备注
        return String.join("", String.format(ALTER_TABLE, getTableName()), ADD_SQL, "");
    }


    public static class ClassLazyTableFieldEndpoint extends AbstractLazyTableFieldEndpoint {

    }
}


