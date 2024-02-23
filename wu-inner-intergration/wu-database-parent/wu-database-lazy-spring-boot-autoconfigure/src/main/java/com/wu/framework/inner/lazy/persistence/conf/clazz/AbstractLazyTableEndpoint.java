package com.wu.framework.inner.lazy.persistence.conf.clazz;


import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableIndexEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.mysql.ClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

@Data
public abstract class AbstractLazyTableEndpoint implements LazyTableEndpoint {

    /**
     * 是否存在
     */
    boolean exist;
    /**
     * 表引擎
     */
    private LazyTable.Engine engine = LazyTable.Engine.InnoDB;
    /**
     * 类名
     */
    private String className;
    /**
     * 类
     */
    private Class<?> clazz;
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 描述信息
     */
    private String comment;
    /**
     * 字段
     */
    private List<LazyTableFieldEndpoint> fieldEndpoints;
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
     * 数据库名 schema
     */
    private String schema;
    /**
     * 包名 com.wu.framework.inner.lazy.persistence.conf
     */
    private String packageName;

    public static AbstractLazyTableEndpoint getInstance() {
        return new ClassLazyTableEndpoint();
    }

    /**
     * 获取表全名
     * mysql.user
     */
    @Override
    public String getFullTableName() {
        if (ObjectUtils.isEmpty(schema)) {
            return tableName;
        }
        return schema + NormalUsedString.DOT + tableName;
    }


    /**
     * 获取当前表索引数据
     *
     * @param javaFieldLazyTableFieldEndpointList
     */
    protected Map<LayerField.LayerFieldType, Map<String, List<LazyTableFieldEndpoint>>> getLayerFieldTypeMap(List<LazyTableFieldEndpoint> javaFieldLazyTableFieldEndpointList) {
        // 当前Java对应索引数据结构
        Map<LayerField.LayerFieldType, Map<String, List<LazyTableFieldEndpoint>>> javaLayerFieldTypeMap = new HashMap<>();

        javaFieldLazyTableFieldEndpointList.forEach(lazyTableFieldEndpoint -> {
            LazyTableIndexEndpoint[] lazyTableIndexEndpoints = lazyTableFieldEndpoint.getLazyTableIndexEndpoints();
            if (!ObjectUtils.isEmpty(lazyTableIndexEndpoints)) {
                Arrays.stream(lazyTableIndexEndpoints).forEach(lazyTableIndexEndpoint -> {
                    Map<String, List<LazyTableFieldEndpoint>> layerFieldTypeKeyMap =
                            javaLayerFieldTypeMap.getOrDefault(lazyTableIndexEndpoint.getFieldIndexType(), new HashMap<String, List<LazyTableFieldEndpoint>>());
                    javaLayerFieldTypeMap.put(lazyTableIndexEndpoint.getFieldIndexType(), layerFieldTypeKeyMap);
                    List<LazyTableFieldEndpoint> lazyTableFieldEndpointList = layerFieldTypeKeyMap.getOrDefault(lazyTableIndexEndpoint.getIndexName(),
                            new ArrayList<LazyTableFieldEndpoint>());
                    lazyTableFieldEndpointList.add(lazyTableFieldEndpoint);
                    layerFieldTypeKeyMap.put(lazyTableIndexEndpoint.getIndexName(), lazyTableFieldEndpointList);
                });
            }
        });
        // 处理索引name为空的问题
        Map<LayerField.LayerFieldType, Map<String, List<LazyTableFieldEndpoint>>> layerFieldTypeMap = new HashMap<>();
        javaLayerFieldTypeMap.forEach((layerFieldType, stringListMap) -> {
                    Map<String, List<LazyTableFieldEndpoint>> indexNameLazyTableFieldEndpointMap = new HashMap<>();
                    stringListMap.forEach((indexName, lazyTableFieldEndpoints) -> {
                        if (ObjectUtils.isEmpty(indexName)) {
                            indexName = lazyTableFieldEndpoints.stream()
                                    .map(lazyTableFieldEndpoint ->
                                            lazyTableFieldEndpoint.getColumnName().replaceAll(NormalUsedString.BACKTICK, NormalUsedString.EMPTY).substring(0, 1))
                                    .collect(Collectors.joining(NormalUsedString.UNDERSCORE));
                        }
                        indexNameLazyTableFieldEndpointMap.put(indexName, lazyTableFieldEndpoints);
                    });
                    layerFieldTypeMap.put(layerFieldType, indexNameLazyTableFieldEndpointMap);
                }
        );
        return layerFieldTypeMap;
    }

    /**
     * 获取 更改的表字段 如 MODIFY COLUMN is_deleted TINYINT ( 1 ) DEFAULT '0' COMMENT '是否删除',
     *
     * @param currentColumnNameList
     * @param dropColumn
     * @return
     */
    protected List<String> alterTableColumnSQLPartList(List<LazyTableFieldEndpoint> currentColumnNameList, boolean dropColumn) {
        currentColumnNameList = currentColumnNameList.stream().sorted((fieldLazyTableFieldEndpoint1, fieldLazyTableFieldEndpoint2) -> Collator.getInstance(Locale.CHINA).compare(fieldLazyTableFieldEndpoint1.getColumnName(), fieldLazyTableFieldEndpoint2.getColumnName())).collect(Collectors.toList());
        String ALTER_TABLE = "ALTER TABLE %s ";
        //  添加字段 字段名 字段类型 字段备注
        String ADD_FIELD = " ADD %s %s comment '%s' ";

        // 实体对应的模型字段
        // 添加额外的模型字段
        List<LazyTableFieldEndpoint> javaFieldLazyTableFieldEndpointList = getFieldEndpoints().stream().sorted((fieldLazyTableFieldEndpoint1, fieldLazyTableFieldEndpoint2) -> Collator.getInstance(Locale.CHINA).compare(fieldLazyTableFieldEndpoint1.getColumnName(), fieldLazyTableFieldEndpoint2.getColumnName())).collect(Collectors.toList());
        javaFieldLazyTableFieldEndpointList.addAll(LazyDatabaseJsonMessage.extraFields);
        javaFieldLazyTableFieldEndpointList = javaFieldLazyTableFieldEndpointList.stream().collect(
                Collectors.collectingAndThen(Collectors.toCollection(() ->
                                new TreeSet<>(Comparator.comparing(LazyTableFieldEndpoint::getColumnName))),
                        ArrayList::new));

        // 当前数据库包含的字段
        Map<String, LazyTableFieldEndpoint> currentColumnNameMap = currentColumnNameList.stream().
                collect(Collectors.toMap(lazyTableFieldEndpoint -> lazyTableFieldEndpoint.getColumnName().toLowerCase(Locale.ROOT), convertedField -> convertedField, (A, B) -> A));

        List<String> ADD_SQL_LIST = javaFieldLazyTableFieldEndpointList.stream().
                filter(field -> !currentColumnNameMap.containsKey(field.getColumnName().toLowerCase(Locale.ROOT))).
                map(convertedField -> String.format(ADD_FIELD,
                        convertedField.getColumnName(),
                        convertedField.getColumnType(),
                        convertedField.getComment())
                ).
                collect(Collectors.toList());
        // 更改列
        //        modify columnName varchar2(255)
        //        MODIFY COLUMN `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除`;
        // 字段名 字段类型 字段备注
//        String MODIFY_FIELD = " MODIFY COLUMN %s ";

        List<String> modifySqlList = new ArrayList<>();

        for (LazyTableFieldEndpoint fieldEndpoint : javaFieldLazyTableFieldEndpointList) {
            // 数据库字段
            String currentColumnName = fieldEndpoint.getColumnName().replaceAll(NormalUsedString.BACKTICK, NormalUsedString.EMPTY);
            // 数据库没有的字段不修改
            if (!currentColumnNameMap.containsKey(currentColumnName)) {
                continue;
            }
            LazyTableFieldEndpoint mysqlFieldEndpoint = currentColumnNameMap.get(currentColumnName);
            String comment = fieldEndpoint.getComment();
            // 判断数据不一致
            if (
                // 非空比较
                    mysqlFieldEndpoint.isNotNull() == fieldEndpoint.isNotNull()
                            // 默认值比较
                            && Objects.equals(mysqlFieldEndpoint.getDefaultValue(), fieldEndpoint.getDefaultValue())
                            // 描述比较
                            && Objects.equals(mysqlFieldEndpoint.getComment(), comment)
                            // 主键比较
                            && fieldEndpoint.isKey() == mysqlFieldEndpoint.isKey()
                            // 额外字段比较
                            && Objects.equals(fieldEndpoint.getExtra(), mysqlFieldEndpoint.getExtra())
                            // 字段类型比较
                            && Objects.equals(fieldEndpoint.getColumnType().replaceAll(NormalUsedString.SPACE, NormalUsedString.EMPTY), mysqlFieldEndpoint.getColumnType().replaceAll(NormalUsedString.SPACE, NormalUsedString.EMPTY)) // varchar(255 ) 与varchar(255)
            ) {
                continue;
            }
//            String format = String.format(MODIFY_FIELD, fieldEndpoint.createColumn());
            modifySqlList.add(fieldEndpoint.modifyColumnSQL());
        }

        // 删除列
        if (dropColumn) {
            // DROP COLUMN `request_methods`
            List<LazyTableFieldEndpoint> finalJavaFieldLazyTableFieldEndpointList = javaFieldLazyTableFieldEndpointList;
            currentColumnNameList.stream().
                    filter(lazyTableFieldEndpoint -> !finalJavaFieldLazyTableFieldEndpointList.
                            stream().
                            map(LazyTableFieldEndpoint::getColumnName).toList()
                            .contains(lazyTableFieldEndpoint.getColumnName())).forEach(lazyTableFieldEndpoint -> modifySqlList.add(lazyTableFieldEndpoint.dropColumnSQL()));
        }
        // 主键索引比较

        String currentColumnPkIndex = currentColumnNameList.stream().filter(LazyTableFieldEndpoint::isKey).
                map(LazyTableFieldEndpoint::getColumnName).
                collect(Collectors.joining(NormalUsedString.UNDERSCORE));

        String javaColumnPkIndex = javaFieldLazyTableFieldEndpointList.stream().filter(LazyTableFieldEndpoint::isKey).
                map(LazyTableFieldEndpoint::getColumnName).
                collect(Collectors.joining(NormalUsedString.UNDERSCORE));
        // 默认主键不删除
        if (!Objects.equals(currentColumnPkIndex, javaColumnPkIndex)) {
            //DROP PRIMARY KEY,
            //ADD PRIMARY KEY (`application_id`, `database_schema_id`, `is_deleted`) USING BTREE;

            // 有主键且更改、删除原主键
            if (currentColumnNameList.stream().anyMatch(LazyTableFieldEndpoint::isKey)) {
                modifySqlList.add("DROP PRIMARY KEY");
            }
            if (!ObjectUtils.isEmpty(javaColumnPkIndex)) {
                String pk = javaFieldLazyTableFieldEndpointList.stream().filter(LazyTableFieldEndpoint::isKey).
                        map(LazyTableFieldEndpoint::getColumnName).
                        collect(Collectors.joining(NormalUsedString.COMMA));
                modifySqlList.add(String.format("ADD PRIMARY KEY (%s) USING BTREE", pk));
            }

        }
        // 当前数据库对应索引数据结构
        Map<LayerField.LayerFieldType, Map<String, List<LazyTableFieldEndpoint>>> currentLayerFieldTypeMap =
                getLayerFieldTypeMap(currentColumnNameList);

        // 当前Java对应索引数据结构
        Map<LayerField.LayerFieldType, Map<String, List<LazyTableFieldEndpoint>>> javaLayerFieldTypeMap
                = getLayerFieldTypeMap(javaFieldLazyTableFieldEndpointList);


        // 索引比较
        // 唯一性索引

        Map<String, List<LazyTableFieldEndpoint>> currentColumnUniqueIndex = currentLayerFieldTypeMap.get(LayerField.LayerFieldType.UNIQUE);

        Map<String, List<LazyTableFieldEndpoint>> javaColumnUniqueIndex = javaLayerFieldTypeMap.get(LayerField.LayerFieldType.UNIQUE);
        if (!ObjectUtils.isEmpty(javaColumnUniqueIndex)) {
            javaColumnUniqueIndex.forEach((indexName, lazyTableFieldEndpoints) -> {
                indexName = ObjectUtils.isEmpty(indexName) ?
                        lazyTableFieldEndpoints.stream().map(lazyTableFieldEndpoint -> lazyTableFieldEndpoint.getColumnName().replaceAll(NormalUsedString.BACKTICK, NormalUsedString.EMPTY).substring(0, 1))
                                .collect(Collectors.joining(NormalUsedString.UNDERSCORE)) :
                        indexName;
                if (!ObjectUtils.isEmpty(currentColumnUniqueIndex) && currentColumnUniqueIndex.containsKey(indexName)) {
                    // 判断是否修改索引
                    List<LazyTableFieldEndpoint> indexLazyTableFieldEndpoints = currentColumnUniqueIndex.get(indexName);

                    // 重复数据
                    ArrayList<LazyTableFieldEndpoint> duplicateList = new ArrayList<>(lazyTableFieldEndpoints);
                    duplicateList.addAll(indexLazyTableFieldEndpoints);
                    ArrayList<LazyTableFieldEndpoint> tableFieldEndpointArrayList = duplicateList.stream().collect(
                            Collectors.collectingAndThen(Collectors.toCollection(() ->
                                            new TreeSet<>(Comparator.comparing(LazyTableFieldEndpoint::getColumnName))),
                                    ArrayList::new));
                    // 不完全重复
                    if (!ObjectUtils.isEmpty(tableFieldEndpointArrayList)) {
                        // 删除索引
                        //DROP INDEX `indexName`,
                        //ADD UNIQUE INDEX `indexName`(`application_id`, `is_deleted`),
                        // 添加索引
                        String indexColumns = lazyTableFieldEndpoints.stream().
                                map(LazyTableFieldEndpoint::getColumnName).
                                collect(Collectors.joining(NormalUsedString.COMMA));
                        modifySqlList.add(String.format("DROP INDEX `%s`", indexName));
                        modifySqlList.add(String.format("ADD UNIQUE INDEX `%s` (%s)", indexName, indexColumns));
                    }
                } else {
                    // 添加索引
                    String indexColumns = lazyTableFieldEndpoints.stream().
                            map(LazyTableFieldEndpoint::getColumnName).
                            collect(Collectors.joining(NormalUsedString.COMMA));
                    modifySqlList.add(String.format("ADD UNIQUE INDEX `%s` (%s)", indexName, indexColumns));
                }
            });
        }


        // 常用索引
        Map<String, List<LazyTableFieldEndpoint>> currentColumnNormalIndex = currentLayerFieldTypeMap.get(LayerField.LayerFieldType.NORMAL);

        Map<String, List<LazyTableFieldEndpoint>> javaColumnNormalIndex = javaLayerFieldTypeMap.get(LayerField.LayerFieldType.NORMAL);
        if (!ObjectUtils.isEmpty(javaColumnNormalIndex)) {
            javaColumnNormalIndex.forEach((indexName, lazyTableFieldEndpoints) -> {
                indexName = ObjectUtils.isEmpty(indexName) ?
                        lazyTableFieldEndpoints.stream().sorted(Comparator.comparing(LazyTableFieldEndpoint::getColumnName)).map(lazyTableFieldEndpoint -> lazyTableFieldEndpoint.getColumnName().replaceAll(NormalUsedString.BACKTICK, NormalUsedString.EMPTY).substring(0, 1))
                                .collect(Collectors.joining(NormalUsedString.UNDERSCORE)) :
                        indexName;
                if (!ObjectUtils.isEmpty(currentColumnNormalIndex) && currentColumnNormalIndex.containsKey(indexName)) {
                    // 判断是否修改索引
                    List<LazyTableFieldEndpoint> indexLazyTableFieldEndpoints = currentColumnNormalIndex.get(indexName);

                    // 重复数据
                    ArrayList<LazyTableFieldEndpoint> duplicateList = new ArrayList<>(lazyTableFieldEndpoints);
                    duplicateList.addAll(indexLazyTableFieldEndpoints);
                    ArrayList<LazyTableFieldEndpoint> tableFieldEndpointArrayList = duplicateList.stream().collect(
                            Collectors.collectingAndThen(Collectors.toCollection(() ->
                                            new TreeSet<>(Comparator.comparing(LazyTableFieldEndpoint::getColumnName))),
                                    ArrayList::new));
                    // 不完全重复
                    if (!ObjectUtils.isEmpty(tableFieldEndpointArrayList)) {
                        // 删除索引
                        //DROP INDEX `indexName`,
                        //ADD  INDEX `indexName`(`application_id`, `is_deleted`),
                        // 添加索引
                        String indexColumns = lazyTableFieldEndpoints.stream().
                                map(lazyTableFieldEndpoint -> NormalUsedString.BACKTICK + lazyTableFieldEndpoint.getColumnName() + NormalUsedString.BACKTICK).
                                collect(Collectors.joining(NormalUsedString.COMMA));
                        modifySqlList.add(String.format("DROP INDEX `%s`", indexName));
                        modifySqlList.add(String.format("ADD  INDEX `%s` (%s)", indexName, indexColumns));
                    }
                } else {
                    // 添加索引
                    String indexColumns = lazyTableFieldEndpoints.stream().
                            map(LazyTableFieldEndpoint::getColumnName).
                            collect(Collectors.joining(NormalUsedString.COMMA));
                    modifySqlList.add(String.format("ADD  INDEX `%s` (%s)", indexName, indexColumns));
                }
            });
        }


        if (ObjectUtils.isEmpty(ADD_SQL_LIST) && ObjectUtils.isEmpty(modifySqlList)) {
            return modifySqlList;
        }
        // 最终的两段sql  添加字sql和更改sql
        modifySqlList.addAll(ADD_SQL_LIST);
        return modifySqlList;
    }

    /**
     * describe 获取指定注解的字段
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2022/1/1 6:40 下午
     **/
    @Override
    public abstract List<LazyTableFieldEndpoint> specifiedFieldAnnotation(LayerField.LayerFieldType fieldType);
}
