package com.wu.framework.inner.lazy.source.h2;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableIndexEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazyTableIndexUtil;
import com.wu.framework.inner.lazy.source.advanced.SourceAdvanced;
import com.wu.framework.inner.lazy.source.advanced.SourceAdvancedAbstract;
import com.wu.framework.inner.lazy.source.mysql.JavaClass2MysqlTableLazyTableEndpoint;
import com.wu.framework.inner.lazy.source.mysql.JavaField2MysqlColumnEndpoint;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Slf4j
public abstract class H2SourceAdvancedTargetAbstract extends SourceAdvancedAbstract implements SourceAdvanced {

    private static final ConcurrentMap<Class<?>, LazyTableEndpoint> H2_TABLE_CLASS_ATTR_MAP = new ConcurrentHashMap<>();
    /**
     * 根据class解析出表名称 支持自定义忽略前缀、后缀
     *
     * @param clazz 需要解析的class
     * @return 返回表名称
     * @see LazyDatabaseJsonMessage#ddlIgnoredTablePrefix
     * @see LazyDatabaseJsonMessage#ddlIgnoredTableSuffix
     */
    @Override
    public String analyzeLazyTableName(Class<?> clazz) {
        Assert.notNull(clazz, "表对应的 class 不能是空");
        LazyTable
                lazyTable =
                AnnotatedElementUtils
                        .findMergedAnnotation(clazz, LazyTable.class);
        if (null == lazyTable || ObjectUtils.isEmpty(lazyTable.tableName())) {

            String simpleName = clazz.getSimpleName();
            // 适应前缀过滤
            for (String ddlIgnoredTableSuffix : LazyDatabaseJsonMessage.ddlIgnoredTablePrefix) {
                if (simpleName.startsWith(ddlIgnoredTableSuffix)) {
                    simpleName = simpleName.substring(ddlIgnoredTableSuffix.length());
                    break;
                }
            }
            // 适应后缀过滤
            for (String ddlIgnoredTableSuffix : LazyDatabaseJsonMessage.ddlIgnoredTableSuffix) {
                if (simpleName.endsWith(ddlIgnoredTableSuffix)) {
                    simpleName = simpleName.substring(0, simpleName.length() - ddlIgnoredTableSuffix.length());
                    break;
                }
            }
            return CamelAndUnderLineConverter.humpToLine2(simpleName);
        }
        return lazyTable.tableName();
    }

    /**
     * 通过class 解析出对应的表结构
     *
     * @param clazz 需要解析的class
     * @return 表结构
     */
    @Override
    public LazyTableEndpoint analyzeLazyTableFromClass(Class<?> clazz) {
        if (!H2_TABLE_CLASS_ATTR_MAP.containsKey(clazz)) {
            synchronized (H2_TABLE_CLASS_ATTR_MAP) {
                LazyTable lazyTable = AnnotatedElementUtils.findMergedAnnotation(clazz, LazyTable.class);

                String className = clazz.getName();
                String tableName = analyzeLazyTableName(clazz);
                Package clazzPackage = clazz.getPackage();
                if (null == clazzPackage) {
                    System.out.println(className);
                }
                final String packageName = clazzPackage.getName();
                String comment = "";
                boolean smartFillField = false;
                boolean exist = true;

                JavaClass2H2TableLazyTableEndpoint lazyTableEndpoint = new JavaClass2H2TableLazyTableEndpoint();
                if (null != lazyTable) {
                    lazyTableEndpoint.setSchema(lazyTable.schema());
                    smartFillField = lazyTable.smartFillField();
                    comment = lazyTable.comment();
                    // 判断一手当前实体对象是否被弃用
                    if (AnnotatedElementUtils.hasAnnotation(clazz, Deprecated.class)) {
                        comment += "（即将弃用）";
                    }
                    LazyTable.Engine engine = lazyTable.engine();
                    lazyTableEndpoint.setEngine(engine);
                    exist = lazyTable.exist();
                }
                List<LazyTableFieldEndpoint> analyzeFieldOnAnnotation = analyzeFieldOnAnnotation(clazz, null);
                lazyTableEndpoint.setPackageName(packageName);
                lazyTableEndpoint.setComment(comment);
                lazyTableEndpoint.setClassName(className);
                lazyTableEndpoint.setClazz(clazz);
                lazyTableEndpoint.setTableName(tableName);
                lazyTableEndpoint.setFieldEndpoints(analyzeFieldOnAnnotation);
                lazyTableEndpoint.setSmartFillField(smartFillField);
                lazyTableEndpoint.setExist(exist);
                log.info("Initialize {} annotation parameters  className:[{}],tableName:[{}],comment:[{}]", clazz,
                        className, tableName, comment);
                H2_TABLE_CLASS_ATTR_MAP.put(clazz, lazyTableEndpoint);
            }
        }
        return H2_TABLE_CLASS_ATTR_MAP.get(clazz);
    }

    /**
     * 当前数据类型缓存的表结构
     *
     * @return 当前数据类型缓存的表结构
     */
    @Override
    public ConcurrentMap<Class<?>, LazyTableEndpoint> getTableCache() {
        return H2_TABLE_CLASS_ATTR_MAP;
    }

    /**
     * 解析 class 表结构中的字段
     *
     * @param clazz              需要解析的class
     * @param tableFileIndexType 过滤字段索引类型
     * @return 返回表结构中字段
     */
    @Override
    public <T> List<LazyTableFieldEndpoint> analyzeFieldOnAnnotation(Class<T> clazz, LayerField.LayerFieldType tableFileIndexType) {
        // 有主键
        boolean haveId = false;
        List<LazyTableFieldEndpoint> convertedFieldList = new ArrayList<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
//            if (!Modifier.isFinal(declaredField.getModifiers()) &&!declaredField.isAccessible()) {
//                declaredField.setAccessible(true);
//            }
            LazyTableField lazyTableField = AnnotatedElementUtils.findMergedAnnotation(declaredField, LazyTableField.class);
            String columnName = CamelAndUnderLineConverter.humpToLine2(declaredField.getName());
            JavaField2H2ColumnEndpoint lazyTableFieldEndpoint = new JavaField2H2ColumnEndpoint();
            String alias = columnName;

            boolean notNull = false;
            boolean key = false;
            String defaultValue = null;
            if (!ObjectUtils.isEmpty(lazyTableField)) {
                lazyTableFieldEndpoint.setExist(lazyTableField.exist());
                if (!lazyTableField.exist()) {
                    continue;
                }
                String comment = lazyTableField.comment();
                // 判断一手当前实体对象字段是否被弃用
                if (AnnotatedElementUtils.hasAnnotation(declaredField, Deprecated.class)) {
                    comment += "（即将弃用）";
                }
                LazyTableIndexEndpoint[] lazyTableIndexEndpoints = LazyTableIndexUtil.analyzeFieldIndex(lazyTableField);
                // 判断是否是我想要的类型
                if (!ObjectUtils.isEmpty(tableFileIndexType)
                        && !ObjectUtils.isEmpty(lazyTableIndexEndpoints)
                        && Arrays.stream(lazyTableIndexEndpoints).noneMatch(lazyTableIndexEndpoint -> tableFileIndexType.equals(lazyTableIndexEndpoint.getFieldIndexType()))
                ) {
                    continue;
                }
                if (!ObjectUtils.isEmpty(lazyTableField.value())) {
                    columnName = lazyTableField.value();
                }
                notNull = lazyTableField.notNull();
                key = lazyTableField.key();
                defaultValue = ObjectUtils.isEmpty(lazyTableField.defaultValue()) ? null : lazyTableField.defaultValue();
                lazyTableFieldEndpoint.setComment(comment);
                lazyTableFieldEndpoint.setColumnType(lazyTableField.columnType());
                if (!ObjectUtils.isEmpty(lazyTableField.alias())) {
                    alias = lazyTableField.alias();
                }
                lazyTableFieldEndpoint.setUpsertStrategy(lazyTableField.upsertStrategy());
                lazyTableFieldEndpoint.setUpdateStrategy(lazyTableField.updateStrategy());
                lazyTableFieldEndpoint.setExtra(lazyTableField.extra());
                lazyTableFieldEndpoint.setLazyTableIndexEndpoints(lazyTableIndexEndpoints);
                lazyTableFieldEndpoint.setSerialNumber(lazyTableField.serialNumber());
            } else {
                lazyTableFieldEndpoint.setExist(true);
            }
            // 判断并获取主键ID
            if (!haveId) {
                final LazyTableFieldId lazyTableFieldId = AnnotatedElementUtils.findMergedAnnotation(declaredField, LazyTableFieldId.class);
                if (!ObjectUtils.isEmpty(lazyTableFieldId)) {
                    haveId = true;
                    LazyTableFieldId.IdType idType = lazyTableFieldId.idType();
                    lazyTableFieldEndpoint.setIdType(idType);
                    if (LazyTableFieldId.IdType.INPUT_ID.equals(idType)) {
                        lazyTableFieldEndpoint.setExtra("");
                    }
                }
            }
            lazyTableFieldEndpoint.setField(declaredField);
            lazyTableFieldEndpoint.setColumnName(columnName);
            lazyTableFieldEndpoint.setName(declaredField.getName());
            lazyTableFieldEndpoint.setClazz(declaredField.getType());
            lazyTableFieldEndpoint.setAlias(alias);
            lazyTableFieldEndpoint.setNotNull(notNull);
            lazyTableFieldEndpoint.setKey(key);
            lazyTableFieldEndpoint.setDefaultValue(defaultValue);
            convertedFieldList.add(lazyTableFieldEndpoint);
        }
        convertedFieldList =
                convertedFieldList.stream().sorted(Comparator.comparing(LazyTableFieldEndpoint::getSerialNumber)).collect(Collectors.toList());

        return convertedFieldList;
    }
}
