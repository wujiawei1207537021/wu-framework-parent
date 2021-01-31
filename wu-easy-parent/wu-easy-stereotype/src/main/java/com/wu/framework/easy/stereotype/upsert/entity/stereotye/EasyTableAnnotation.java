package com.wu.framework.easy.stereotype.upsert.entity.stereotye;

import com.wu.framework.easy.stereotype.upsert.EasySmartField;
import com.wu.framework.easy.stereotype.upsert.entity.ConvertedField;
import com.wu.framework.easy.stereotype.upsert.entity.UpsertJsonMessage;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.wu.framework.easy.stereotype.upsert.converter.SQLConverter.*;

/**
 * description 表注解属性
 *
 * @author Jia wei Wu
 * @date 2020/9/3 上午9:52
 */
@Data
public class EasyTableAnnotation {

    /**
     * 类名
     */
    private String className;
    /**
     * 类
     */
    private Class clazz;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 描述信息
     */
    private String comment;

    /**
     * kafka  schema 名称
     *
     * @return
     */
    private String kafkaSchemaName;

    /**
     * kafka 主题 为空使用类名
     *
     * @return
     */
    private String kafkaTopicName;
    /**
     * kafka code 编码
     */
    private String kafkaCode;

    /**
     * 字段
     */
    private List<ConvertedField> convertedFieldList;

    /**
     * 字典枚举
     */
    private Map<String, Map<String, String>> iEnumList;


    /**
     * @return
     * @params 使用建表语句
     * @author Jiawei Wu
     * @date 2020/12/31 9:00 下午
     **/
    public String creatTableSQL() {
        StringBuilder createTableSQLBuffer = new StringBuilder(
                String.format(SQL_DESC, tableName, comment, AUTHOR, LocalDate.now()));
        createTableSQLBuffer.append(String.format(SQL_DROP, tableName));
        createTableSQLBuffer.append("CREATE TABLE `").append(tableName).append("` ( \n");
        // 是否为唯一索引
        List<String> uniqueList = convertedFieldList.stream().
                filter(ConvertedField::isExist).
                filter(convertedField -> EasySmartField.TableFileIndexType.UNIQUE.equals(convertedField.getFieldIndexType())).
                map(ConvertedField::getConvertedFieldName).collect(Collectors.toList());

        // 添加字段
        convertedFieldList.stream().
                filter(ConvertedField::isExist).
                filter(convertedField -> !UpsertJsonMessage.ignoredFields.contains(convertedField.getConvertedFieldName())).forEach(convertedField -> {
            createTableSQLBuffer.append(convertedField.createColumn());
        });
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
        createTableSQLBuffer.append(comment).append("';\n");
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
     * @return
     * @param currentColumnNameList 当前字段
     * @author Jiawei Wu
     * @date 2020/12/31 9:32 下午
     **/
    public String alterTableSQL(List<ConvertedField> currentColumnNameList) {
        String ALTER_TABLE = "ALTER TABLE %s ";
        String ADD_FIELD = " ADD %s %s %s "; // 字段名 字段类型 字段备注
         Map<String, ConvertedField> map = currentColumnNameList.stream().
                 collect(Collectors.toMap(ConvertedField::getConvertedFieldName, convertedField -> convertedField));

        String ADD_SQL = convertedFieldList.stream().
                filter(convertedField -> !map.containsKey(convertedField.getConvertedFieldName().replaceAll("`", ""))).
                map(convertedField ->
                        String.format(ADD_FIELD,
                                convertedField.getConvertedFieldName(),
                                convertedField.getType(),
                                convertedField.getComment())).
                collect(Collectors.joining(","));
        if (ObjectUtils.isEmpty(ADD_SQL)) return null;
        // 更改列
        //        modify columnName varchar2(255)
        String MODIFY_FIELD = " MODIFY %s %s %s "; // 字段名 字段类型 字段备注
        return String.join("", String.format(ALTER_TABLE, tableName), ADD_SQL, "");
    }
}
