package com.wu.framework.inner.lazy.source.h2;


import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.clazz.AbstractLazyTableEndpoint;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.text.Collator;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Java class 转化成 h2 表信息
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/23 4:39 下午
 */
@Slf4j
@Data
public class JavaClass2H2TableLazyTableEndpoint extends AbstractLazyTableEndpoint {

    /**
     * 创建表语句
     * <p>
     * -- auto-generated definition
     * create table TABLE_NAME
     * (
     * ID          INTEGER auto_increment,
     * COLUMN_NAME CHARACTER VARYING
     * );
     * <p>
     * comment on table TABLE_NAME is '你好这是表注释';
     * <p>
     * comment on column TABLE_NAME.ID is 'id';
     * </p>
     *
     * @return
     */
    @Override
    public String creatTableSQL() {
        StringBuilder createTableSQLBuffer = new StringBuilder(
                String.format(LazyTableEndpoint.SQL_DESC, getFullTableName(), getComment(), LazyTableEndpoint.AUTHOR, LocalDate.now()));

        if (!ObjectUtils.isEmpty(getSchema())) {
            createTableSQLBuffer.append(String.format(" CREATE DATABASE IF NOT EXISTS %s; \n use %s ; \n ", getSchema(), getSchema()));
        }
//        createTableSQLBuffer.append(String.format(SQLAnalyze.SQL_DROP, getTableName()));


        createTableSQLBuffer.append("CREATE TABLE IF NOT EXISTS `")
                .append(getTableName()).append("` ( \n");
        List<LazyTableFieldEndpoint> fieldEndpoints = getFieldEndpoints();
        // 添加额外的模型字段
        List<LazyTableFieldEndpoint> fieldLazyTableFieldEndpointList = new ArrayList<>(fieldEndpoints);
        fieldLazyTableFieldEndpointList.addAll(LazyDatabaseJsonMessage.extraFields);
        fieldLazyTableFieldEndpointList = fieldLazyTableFieldEndpointList.stream().sorted(
                        (firstLazyTableFieldEndpoint, secondLazyFieldLazyTableFieldEndpoint) ->
                                Collator.getInstance(Locale.CHINA)
                                        .compare(firstLazyTableFieldEndpoint.getColumnName(), secondLazyFieldLazyTableFieldEndpoint.getColumnName()))
                .collect(
                        Collectors.collectingAndThen(Collectors.toCollection(() ->
                                        new TreeSet<>(Comparator.comparing(LazyTableFieldEndpoint::getColumnName))),
                                ArrayList::new));

        // 是否为唯一索引
        List<String> uniqueList = fieldLazyTableFieldEndpointList.stream().
                filter(LazyTableFieldEndpoint::isExist).
                filter(field -> Arrays.stream(field.getLazyTableIndexEndpoints()).anyMatch(index -> LayerField.LayerFieldType.UNIQUE.equals(index.getFieldIndexType()))).
                map(LazyTableFieldEndpoint::getColumnName).toList();

        // 所有的索引
        Map<LayerField.LayerFieldType, Map<String, List<LazyTableFieldEndpoint>>> layerFieldTypeMap = getLayerFieldTypeMap(fieldLazyTableFieldEndpointList);

        // 字段数据
        List<String> columnList = new ArrayList<>();
        // 添加字段

        fieldLazyTableFieldEndpointList.stream().
                filter(LazyTableFieldEndpoint::isExist).
                forEach(field -> columnList.add(field.columnSQL() + NormalUsedString.NEWLINE));
        // 主键 PRIMARY KEY (`id`)
        List<String> idList = fieldLazyTableFieldEndpointList.stream().
                filter(LazyTableFieldEndpoint::isExist).
                filter(LazyTableFieldEndpoint::isKey).
                map(LazyTableFieldEndpoint::getColumnName).collect(Collectors.toList());


        if (!ObjectUtils.isEmpty(idList)) {
            columnList.add(String.format("PRIMARY KEY (%s) ", String.join(NormalUsedString.COMMA, idList)) + NormalUsedString.NEWLINE);
        }


        createTableSQLBuffer.append(String.join(NormalUsedString.COMMA, columnList));

        createTableSQLBuffer.append(")");
//        createTableSQLBuffer.append(") ENGINE=");
//        createTableSQLBuffer.append(getEngine().getName());
//        createTableSQLBuffer.append(" DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='");
//        createTableSQLBuffer.append(getComment());
        createTableSQLBuffer.append(";\n");

        // 索引

        if (!ObjectUtils.isEmpty(layerFieldTypeMap)) {
            // 唯一性索引
            Map<String, List<LazyTableFieldEndpoint>> uniqueIndexMap = layerFieldTypeMap.get(LayerField.LayerFieldType.UNIQUE);
            //        create unique index NETTY_CLIENT_BLACKLIST_CLIENT_ID_IS_DELETED_UNINDEX
            //    on NETTY_CLIENT_BLACKLIST (CLIENT_ID, IS_DELETED);
            if (!ObjectUtils.isEmpty(uniqueIndexMap)) {
                uniqueIndexMap.forEach((uniqueIndexName, lazyTableFieldEndpoints) -> {
                    String uniqueIndexSql = "CREATE UNIQUE INDEX " + uniqueIndexName + NormalUsedString.SPACE + NormalUsedString.ON + NormalUsedString.SPACE + getTableName() + " (" +
                            lazyTableFieldEndpoints.stream().map(LazyTableFieldEndpoint::getColumnName
                            ).collect(Collectors.joining(","))
                            + ");" + NormalUsedString.NEWLINE;

                    createTableSQLBuffer.append(uniqueIndexSql);

                });
            }
            // Normal索引
            Map<String, List<LazyTableFieldEndpoint>> normalIndexMap = layerFieldTypeMap.get(LayerField.LayerFieldType.NORMAL);
            //    create index PRIMARY_KEY_4
            //    on EMPLOYEES (ID, DEPARTMENT_ID);
            if (!ObjectUtils.isEmpty(normalIndexMap)) {
                normalIndexMap.forEach((normalIndexName, lazyTableFieldEndpoints) -> {
                    String normalIndexSql = " CREATE INDEX " + normalIndexName + NormalUsedString.SPACE + NormalUsedString.ON + NormalUsedString.SPACE + getTableName() + " (" +
                            lazyTableFieldEndpoints.stream().map(LazyTableFieldEndpoint::getColumnName
                            ).collect(Collectors.joining(","))
                            + ");" + NormalUsedString.NEWLINE;
                    createTableSQLBuffer.append(normalIndexSql);

                });
            }
            // fulltext 全文索引
            Map<String, List<LazyTableFieldEndpoint>> fulltextIndexMap = layerFieldTypeMap.get(LayerField.LayerFieldType.FULLTEXT);
            //    create index PRIMARY_KEY_4
            //    on EMPLOYEES (ID, DEPARTMENT_ID);
            if (!ObjectUtils.isEmpty(fulltextIndexMap)) {
                fulltextIndexMap.forEach((fulltextIndexName, lazyTableFieldEndpoints) -> {
                    String fulltextIndexSql = " CREATE INDEX " + fulltextIndexName + NormalUsedString.SPACE + NormalUsedString.ON + NormalUsedString.SPACE + getTableName() + " (" +
                            lazyTableFieldEndpoints.stream().map(LazyTableFieldEndpoint::getColumnName
                            ).collect(Collectors.joining(","))
                            + ");" + NormalUsedString.NEWLINE;
                    createTableSQLBuffer.append(fulltextIndexSql);
                });
            }
        }
        // 空间索引
        Map<String, List<LazyTableFieldEndpoint>> spatialIndexMap = layerFieldTypeMap.get(LayerField.LayerFieldType.SPATIAL);
        //    create index PRIMARY_KEY_4
        //    on EMPLOYEES (ID, DEPARTMENT_ID);
        if (!ObjectUtils.isEmpty(spatialIndexMap)) {
            spatialIndexMap.forEach((spatialIndexName, lazyTableFieldEndpoints) -> {
                String spatialIndexSql = " CREATE INDEX " + spatialIndexName + NormalUsedString.SPACE + NormalUsedString.ON + NormalUsedString.SPACE + getTableName() + " (" +
                        lazyTableFieldEndpoints.stream().map(LazyTableFieldEndpoint::getColumnName
                        ).collect(Collectors.joining(","))
                        + ");" + NormalUsedString.NEWLINE;
                createTableSQLBuffer.append(spatialIndexSql);
            });
        }

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
     *
     * @param currentColumnNameList
     * @param dropColumn
     */
    @Override
    public String alterTableSQL(List<LazyTableFieldEndpoint> currentColumnNameList, boolean dropColumn) {
        currentColumnNameList = currentColumnNameList.stream().sorted((fieldLazyTableFieldEndpoint1, fieldLazyTableFieldEndpoint2) -> Collator.getInstance(Locale.CHINA).compare(fieldLazyTableFieldEndpoint1.getColumnName(), fieldLazyTableFieldEndpoint2.getColumnName())).collect(Collectors.toList());
        String ALTER_TABLE = "ALTER TABLE %s ";

        // 最终的两段sql  添加字sql和更改sql
        List<String> sql = alterTableColumnSQLPartList(currentColumnNameList, dropColumn);

        if (ObjectUtils.isEmpty(sql)) {
            return null;
        }
        final String alterSQL = String.format(ALTER_TABLE, getFullTableName()) +
                sql.stream().filter(s -> !ObjectUtils.isEmpty(s)).collect(Collectors.joining(NormalUsedString.COMMA));
        log.debug("更新表结构语句:{}", alterSQL);
        return alterSQL;
    }

    /**
     * 创建每个字段修改的语句
     *
     * @param currentColumnNameList
     * @param dropColumn
     * @return
     */
    @Override
    public List<String> alterTableColumnSQL(List<LazyTableFieldEndpoint> currentColumnNameList, boolean dropColumn) {
        String ALTER_TABLE = "ALTER TABLE %s ";
        List<String> sql = alterTableColumnSQLPartList(currentColumnNameList, dropColumn);

        List<String> alterSQLList = sql.stream().filter(columnSql -> !ObjectUtils.isEmpty(columnSql)).map(columnSql -> String.format(ALTER_TABLE, getFullTableName()) + columnSql).collect(Collectors.toList());

        return alterSQLList;
    }


    /**
     * 获取指定注解的字段
     *
     * @param fieldType
     * @return
     */
    @Override
    public List<LazyTableFieldEndpoint> specifiedFieldAnnotation(LayerField.LayerFieldType fieldType) {
        List<LazyTableFieldEndpoint> lazyTableFieldEndpoints = new ArrayList<>();
        final List<LazyTableFieldEndpoint> fieldEndpoints = getFieldEndpoints();
        if (ObjectUtils.isEmpty(fieldEndpoints)) {
            return lazyTableFieldEndpoints;
        }
        lazyTableFieldEndpoints =
                fieldEndpoints.stream().filter(lazyTableFieldEndpoint ->
                        Arrays.stream(lazyTableFieldEndpoint.getLazyTableIndexEndpoints()).
                                anyMatch(lazyTableIndexEndpoint -> lazyTableIndexEndpoint.getFieldIndexType().equals(fieldType))
                ).collect(Collectors.toList());
        return lazyTableFieldEndpoints;
    }


//    public static class FieldLazyTableFieldEndpoint extends AbstractLazyTableFieldEndpoint {
//
//    }
}


