package com.wu.framework.inner.lazy.database.expand.database.persistence.translation.api;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.data.convert.LazyDataFactory;
import com.wu.framework.inner.layer.util.SpringContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.endpoint.LazyTranslationTableEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazySQLUtil;
import com.wu.framework.inner.lazy.persistence.util.LazyTableFieldUtil;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/10/04 15:48
 */
public class LazyTableTranslationOneAPI implements LazyTranslationAPI {


    /**
     * description  转换数据
     *
     * @param lazyTranslationTableEndpoint 原始数据
     * @return Map<String sourceValue, Object 目标数据>
     * @author Jia wei Wu
     * @date 2020/8/6 下午7:54
     */
    @Override
    public ConcurrentMap<String, Object> translation(LazyTranslationTableEndpoint lazyTranslationTableEndpoint) {
        LazyLambdaStream lazyLambdaStream = SpringContextHolder.getBean(LazyLambdaStream.class);

        LazyTranslationTableEndpoint.Type type = lazyTranslationTableEndpoint.getType();
        String translationTargetTableName = lazyTranslationTableEndpoint.getTranslationTargetTableName();

        // 优先已经存在的表
        Class<?> translationTargetTableClass = lazyTranslationTableEndpoint.getTranslationTargetTableClass();
        if (ObjectUtils.isEmpty(translationTargetTableName) && translationTargetTableClass != null) {
            translationTargetTableName = LazyTableUtil.getTableName(translationTargetTableClass);
        }
        String[] columnList = lazyTranslationTableEndpoint.getColumnList();
        String translationSourceName = lazyTranslationTableEndpoint.getTranslationSourceName();
        String translationTargetName = lazyTranslationTableEndpoint.getTranslationTargetName();

        String translationTargetSqlName = CamelAndUnderLineConverter.humpToLine2(translationTargetName);
        Set<Object> sourceValues = lazyTranslationTableEndpoint.getSourceValues();
        Class<?> translationTargetType = lazyTranslationTableEndpoint.getTranslationTargetType();
        String sql = "";
        List<String> columnSqlList = Arrays.stream(columnList).map(CamelAndUnderLineConverter::humpToLine2).collect(Collectors.toList());

        if (LazyTranslationTableEndpoint.Type.ALL.equals(type)) {
            if (ObjectUtils.isEmpty(columnSqlList)) {
                columnSqlList.add(NormalUsedString.ASTERISK);
            } else {
                String oldFirst = columnSqlList.get(0);
                String newFirst = NormalUsedString.ASTERISK;
                columnSqlList.set(0, NormalUsedString.ASTERISK);
                columnSqlList.add(LazyTableFieldUtil.cleanSpecialColumn(oldFirst));
            }

        } else {
            columnSqlList.add(LazyTableFieldUtil.cleanSpecialColumn(translationTargetSqlName));
        }
        sql = "select " + String.join(NormalUsedString.COMMA, columnSqlList) + " from " + translationTargetTableName + " where " + translationTargetSqlName + " in ({0})";
        List<Map> mapTranslationValueList = lazyLambdaStream.executeSQL(sql, Map.class, sourceValues
                .stream()
                .filter(Objects::nonNull)
                .map(o -> {
                    Object columnData = LazySQLUtil.valueToSqlValue(o);
                    return columnData.toString();
                }).collect(Collectors.joining(NormalUsedString.COMMA)));


        return mapTranslationValueList.stream()
                .collect(Collectors.toConcurrentMap(map -> map.get(translationTargetSqlName).toString(), map -> {
                    // 清除多余字段
                    map.remove(translationTargetSqlName);

                    // 判断 translationTargetType是否是基础数据类型 如果是获取然后返回 false 对象序列化
                    if (map.keySet().size() == 1) {
                        // 基础数据类型返回一个数据
                        return map.values().stream().findFirst().get();
                    } else {
                        // 非基础数据类型返回一个对象
                        return LazyDataFactory.handler(map, translationTargetType);
                    }

                }, (A, B) -> A));
    }
}
