package com.wu.framework.inner.lazy.persistence.util;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableIndexEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.field.AbstractLazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/8/8 2:35 下午
 */
public class LazyTableFieldUtil {

    public static final Logger log = LoggerFactory.getLogger(LazyTableFieldUtil.class);


    /**
     * 解析 class 中字段为数据schema
     *
     * @param clazz
     * @param tableFileIndexType
     * @param <T>
     * @return
     */
    public static <T> List<LazyTableFieldEndpoint> analyzeFieldOnAnnotation(Class<T> clazz, LayerField.LayerFieldType tableFileIndexType) {
        // 有主键
        boolean haveId = false;
        List<LazyTableFieldEndpoint> convertedFieldList = new ArrayList<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
//            if (!Modifier.isFinal(declaredField.getModifiers()) &&!declaredField.isAccessible()) {
//                declaredField.setAccessible(true);
//            }
            LazyTableField lazyTableField = AnnotatedElementUtils.findMergedAnnotation(declaredField, LazyTableField.class);
            String columnName = CamelAndUnderLineConverter.humpToLine2(declaredField.getName());
            AbstractLazyTableFieldEndpoint lazyTableFieldEndpoint = AbstractLazyTableFieldEndpoint.getInstance();
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
                // 判断一手当前实体对象是否被弃用
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

    /**
     * 清洗特殊列字段
     *
     * @return
     */
    public static String cleanSpecialColumn(String javaColumn) {
        Assert.notNull(javaColumn, "列字段不能为空");
        if (LazyDatabaseJsonMessage.specialFields.contains(javaColumn.toUpperCase(Locale.ROOT))) {
            return NormalUsedString.BACKTICK + javaColumn + NormalUsedString.BACKTICK;
        }
        return javaColumn;
    }
}
