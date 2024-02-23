package com.wu.framework.inner.lazy.factory;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.persistence.analyze.EasyAnnotationConverter;
import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableStructure;
import com.wu.framework.inner.lazy.persistence.util.LazySQLUtil;
import com.wu.framework.inner.lazy.stereotype.LazyFieldStrategy;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * LazyTableUpsertConverterFactory 对象转化成 upsert 工厂
 *
 * @author wujiawei
 */
public class LazyTableUpsertConverterFactory {

    /**
     * 将对象转换成 对应的 upsert 语句
     *
     * @param bean 对象
     * @return insert 语句
     */
    public static String upsertRemoveNull(Object bean) {
        return insert(bean, "insert into %s (%s) VALUES %s  ON DUPLICATE KEY UPDATE \n %s", true);
    }


    /**
     * 将对象转换成 对应的 insert 语句
     *
     * @param bean 对象
     * @return insert 语句
     */
    public static String insertRemoveNull(Object bean) {
        return insert(bean, "insert into %s (%s) VALUES %s ", true);
    }

    /**
     * 将对象转换成 对应的 upsert 语句
     *
     * @param bean 对象
     * @return insert 语句
     */
    public static String upsert(Object bean) {
        return insert(bean, "insert into %s (%s) VALUES %s  ON DUPLICATE KEY UPDATE \n %s");
    }

    /**
     * 将对象转换成 对应的 insert 语句
     *
     * @param bean 对象
     * @return insert 语句
     */
    public static String insert(Object bean) {
        return insert(bean, "insert into %s (%s) VALUES %s");
    }

    /**
     * 将对象转换成 对应的 insert 语句
     *
     * @param bean       对象
     * @param sqlStencil sql 模版  如：insert into %s (%s) VALUES %s  ON DUPLICATE KEY UPDATE \n %s
     * @return insert 语句
     */
    public static String insert(Object bean, String sqlStencil) {
        return insert(bean, sqlStencil, false);
    }

    /**
     * 将对象转换成 对应的 insert 语句
     *
     * @param bean       对象
     * @param sqlStencil sql 模版  如：insert into %s (%s) VALUES %s  ON DUPLICATE KEY UPDATE \n %s
     * @param ignoreNull 忽略null
     * @return insert 语句
     */
    public static String insert(Object bean, String sqlStencil, boolean ignoreNull) {
        LazyTableStructure lazyTableStructure = LazyTableStructureConverterFactory.dataStructure(bean);

        LazyTableEndpoint schema = lazyTableStructure.schema();

        List<List<LazyTableFieldEndpoint>> payload = lazyTableStructure.payload();

        // 添加字段
        List<LazyTableFieldEndpoint> convertedFieldList = schema.getFieldEndpoints();

        //可以使用的字段
        List<LazyTableFieldEndpoint> canBeUseFields = (ignoreNull ? payload.get(0) : convertedFieldList)
                .stream()
                .filter(LazyTableFieldEndpoint::isExist)
//                filter(fieldLazyTableFieldEndpoint -> !LazyTableFieldId.IdType.AUTOMATIC_ID.equals(fieldLazyTableFieldEndpoint.getIdType())).
                .filter(fieldLazyTableFieldEndpoint -> ((!ignoreNull ||
                        LazyTableFieldId.IdType.AUTOMATIC_ID.equals(fieldLazyTableFieldEndpoint.getIdType())) || Objects.nonNull(fieldLazyTableFieldEndpoint.getFieldValue())))
                .filter(fieldLazyTableFieldEndpoint -> !LazyFieldStrategy.NEVER.equals(fieldLazyTableFieldEndpoint.getUpsertStrategy()))
                .filter(convertedField -> !LazyDatabaseJsonMessage.ignoredFields.contains(convertedField.getName())).toList();
        // 字段
        String column = canBeUseFields.stream().
                map(LazyTableFieldEndpoint::getColumnName).
                collect(Collectors.joining(NormalUsedString.COMMA));
        // 更新后缀字段
        String updateColumn = canBeUseFields.stream()
                .filter(lazyTableFieldEndpoint -> !LazyFieldStrategy.NEVER_JOIN_DUPLICATE_KEY.equals(lazyTableFieldEndpoint.getUpsertStrategy()))
                .map(convertedField -> convertedField.getColumnName() + NormalUsedString.EQUALS + NormalUsedString.VALUES
                        + NormalUsedString.SPACE + NormalUsedString.LEFT_BRACKET + convertedField.getColumnName() +
                        NormalUsedString.RIGHT_BRACKET).
                collect(Collectors.joining(NormalUsedString.COMMA));

        //  数据
        String values = payload.stream().map(lazyTableFieldEndpoints -> NormalUsedString.LEFT_BRACKET + lazyTableFieldEndpoints.stream()
                .filter(fieldLazyTableFieldEndpoint -> ((!ignoreNull ||
                        LazyTableFieldId.IdType.AUTOMATIC_ID.equals(fieldLazyTableFieldEndpoint.getIdType()))
                        || Objects.nonNull(fieldLazyTableFieldEndpoint.getFieldValue())))
                .filter(fieldLazyTableFieldEndpoint -> !LazyFieldStrategy.NEVER.equals(fieldLazyTableFieldEndpoint.getUpsertStrategy()))
                //                    filter(fieldLazyTableFieldEndpoint -> !LazyTableFieldId.IdType.AUTOMATIC_ID.equals(fieldLazyTableFieldEndpoint.getIdType())).
                .map(lazyTableFieldEndpoint -> {
                    Object fieldValue = lazyTableFieldEndpoint.getFieldValue();

                    // 输入的主键添加策略
                    if (ObjectUtils.isEmpty(fieldValue) && LazyTableFieldId.IdType.INPUT_ID.equals(lazyTableFieldEndpoint.getIdType())) {
                        Class<?> clazz = lazyTableFieldEndpoint.getClazz();
                        fieldValue = LazyTableIdFactory.createId(clazz);
                    }

                    // 条件判断
                    LazyFieldStrategy upsertStrategy = lazyTableFieldEndpoint.getUpsertStrategy();
                    if (LazyFieldStrategy.NOT_EMPTY.equals(upsertStrategy) && NormalUsedString.EMPTY.equals(fieldValue)) {
                        throw new RuntimeException(String.format("字段【%s】不能为空字符串", lazyTableFieldEndpoint.getField()));
                    }
                    if (LazyFieldStrategy.NOT_NULL.equals(upsertStrategy) && null == fieldValue) {
                        throw new RuntimeException(String.format("字段【%s】不能为NULL", fieldValue));
                    }
                    fieldValue = LazySQLUtil.valueToSqlValue(fieldValue);
                    if (null != fieldValue) {
                        fieldValue = EasyAnnotationConverter.annotationConvertConversion(lazyTableFieldEndpoint.getField(),
                                fieldValue, schema.getIEnumList());
                        return fieldValue.toString();
                    } else {
                        //                                fieldVal = JavaBasicTypeDefaultValue.DEFAULT_VALUE_HASHMAP.get(field.getType());
                        return null;
                    }
                }).collect(Collectors.joining(NormalUsedString.COMMA)) + NormalUsedString.RIGHT_BRACKET).collect(Collectors.joining(NormalUsedString.COMMA));
        return String.format(sqlStencil, schema.getFullTableName(), column, values, updateColumn);
    }

}
