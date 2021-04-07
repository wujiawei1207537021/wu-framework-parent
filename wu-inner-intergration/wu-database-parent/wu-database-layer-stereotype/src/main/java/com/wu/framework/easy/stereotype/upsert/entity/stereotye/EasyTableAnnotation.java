package com.wu.framework.easy.stereotype.upsert.entity.stereotye;

import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.stereotype.upsert.entity.ConvertedField;
import com.wu.framework.easy.stereotype.upsert.entity.UpsertJsonMessage;
import com.wu.framework.inner.layer.stereotype.LayerField;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.wu.framework.easy.stereotype.upsert.converter.SQLConverter.*;

/**
 * description 表注解属性
 * 弃用原因 使用注解匿名实现类
 *
 * @author Jia wei Wu
 * @date 2020/9/3 上午9:52
 */
//@Deprecated
@Data
public class EasyTableAnnotation implements EasySmart {

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
     * 智能填字段
     */
    private boolean smartFillField;
    /**
     * 完善表
     */
    private boolean perfectTable;
    /**
     * 数据向下钻取
     */
    private boolean dataDrillDown;
    /**
     * Hbase 行名称
     */
    private String hBaseRow;
    /**
     * redis key
     */
    private String redisKey;
    /**
     * Elasticsearch 索引类型
     */
    private String indexType;
    /**
     * Elasticsearch 索引后缀
     */
    private String indexSuffix;
    /**
     * Elasticsearch 索引时间格式
     */
    private String indexFormat;
    /**
     * Elasticsearch 索引前缀
     */
    private String indexPrefix;
    /**
     * 数据库名 schema
     */
    private String schema;
    /**
     * 列族
     */
    private String columnFamily;


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
                filter(convertedField -> LayerField.LayerFieldType.UNIQUE.equals(convertedField.getFieldIndexType())).
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

    /**
     * 表名
     *
     * @return
     */
    @Override
    public String value() {
        return tableName;
    }

    @Override
    public String tableName() {
        return tableName;
    }

    /**
     * 完善表
     *
     * @return
     */
    @Override
    public boolean perfectTable() {
        return perfectTable;
    }

    @Override
    public boolean dataDrillDown() {
        return dataDrillDown;
    }

    /**
     * 表注释
     *
     * @return String
     */
    @Override
    public String comment() {
        return comment;
    }

    /**
     * kafka  schema 名称
     *
     * @return String
     */
    @Override
    public String kafkaSchemaName() {
        return kafkaSchemaName;
    }

    /**
     * kafka 主题 为空使用类名
     *
     * @return String
     */
    @Override
    public String kafkaTopicName() {
        return kafkaTopicName;
    }

    /**
     * kafka code编码
     *
     * @return String
     */
    @Override
    public String kafkaCode() {
        return kafkaCode;
    }

    /**
     * 数据库名 schema
     *
     * @return String
     */
    @Override
    public String schema() {
        return schema;
    }

    /**
     * Elasticsearch 索引前缀
     *
     * @return String
     */
    @Override
    public String indexPrefix() {
        return indexPrefix;
    }

    /**
     * Elasticsearch 索引时间格式
     *
     * @return String
     */
    @Override
    public String indexFormat() {
        return indexFormat;
    }

    /**
     * Elasticsearch 索引后缀
     */
    @Override
    public String indexSuffix() {
        return indexSuffix;
    }

    /**
     * Elasticsearch 索引类型
     */
    @Override
    public String indexType() {
        return indexType;
    }

    /**
     * redis key
     */
    @Override
    public String redisKey() {
        return redisKey;
    }

    /**
     * Hbase 行名称
     *
     * @return
     */
    @Override
    public String hBaseRow() {
        return hBaseRow;
    }

    /**
     * 列族
     *
     * @return
     */
    @Override
    public String columnFamily() {
        return columnFamily;
    }

    /**
     * 智能填充bean属性
     * 针对数据源 如mysql查询结果、http请求结果中包含的数据字段不再当前对象中
     */
    @Override
    public boolean smartFillField() {
        return smartFillField;
    }

    /**
     * Returns the annotation type of this annotation.
     *
     * @return the annotation type of this annotation
     */
    @Override
    public Class<? extends Annotation> annotationType() {
        return EasySmart.class;
    }
}
