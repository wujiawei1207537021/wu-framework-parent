package com.wu.framework.inner.lazy.factory;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableStructure;
import com.wu.framework.inner.lazy.persistence.util.LazyFieldStrategyUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * LazyTableUpsertConverterFactory 对象转化成 update 工厂
 *
 * @author wujiawei
 */
public class LazyTableUpdateConverterFactory {

    /**
     * 将对象转换成 对应的 update 语句
     *
     * @param bean 对象
     * @return update sql
     */
    public static String updateByPK(Object bean) {
        LazyTableStructure lazyTableStructure = LazyTableStructureConverterFactory.dataStructure(bean);
        LazyTableFieldEndpoint fieldEndpoint = lazyTableStructure.schema()
                .getFieldEndpoints()
                .stream()
                .filter(LazyTableFieldEndpoint::isKey)
                .findFirst()
                .get();

        // 需要设置set的数据
        Collection<LazyTableFieldEndpoint> setLazyTableFieldEndpointList =
                lazyTableStructure
                        .schema()
                        .getFieldEndpoints()
                        .stream()
                        .filter(
                                lazyTableFieldEndpoint ->
                                        LazyFieldStrategyUtil.testUpdate(lazyTableFieldEndpoint.getFieldValue(), lazyTableFieldEndpoint.getUpdateStrategy())
                        )
                        .toList();

        String pk = fieldEndpoint.getColumnName();
        Object pkValue = fieldEndpoint.getSqlValue();
        return String.format("update %s  set %s where %s = %s ",
                lazyTableStructure.schema().getTableName(),
                setLazyTableFieldEndpointList
                        .stream()
                        .map(lazyTableFieldEndpoint ->
                                lazyTableFieldEndpoint.getColumnName() + NormalUsedString.EQUALS + lazyTableFieldEndpoint.getSqlValue())
                        .collect(
                                Collectors.joining(NormalUsedString.COMMA)
                        ),
                pk,
                pkValue
        );
    }

    /**
     * @param bean 对象
     * @return
     */
    public static String updateUniqueKey(Object bean) {
        LazyTableStructure lazyTableStructure = LazyTableStructureConverterFactory.dataStructure(bean);

        // 需要设置set的数据
        Collection<LazyTableFieldEndpoint> setLazyTableFieldEndpointList =
                lazyTableStructure
                        .payload()
                        .get(0)
                        .stream()
                        .filter(
                                lazyTableFieldEndpoint ->
                                        LazyFieldStrategyUtil.testUpdate(lazyTableFieldEndpoint.getFieldValue(), lazyTableFieldEndpoint.getUpdateStrategy())
                        )
                        .filter(lazyTableFieldEndpoint -> {
                            // 过滤非空的字段 并且value 也是null
                            return !(lazyTableFieldEndpoint.isKey() && null == lazyTableFieldEndpoint.getFieldValue());
                        })
                        .toList();


        Collection<LazyTableFieldEndpoint> whereLazyTableFieldEndpointList =
                lazyTableStructure
                        .payload()
                        .get(0)
                        .stream()
                        .filter(lazyTableFieldEndpoint -> {

                            // 根据唯一性索引进行分组，而后根据分组后的数据比对value是否为空
                            return Arrays.stream(
                                            lazyTableFieldEndpoint.getLazyTableIndexEndpoints())
                                    .anyMatch(
                                            // TODO 唯一性索引名称进行分组
                                            lazyTableIndexEndpoint ->
                                                    LayerField.LayerFieldType.UNIQUE.equals(lazyTableIndexEndpoint.getFieldIndexType())
                                    ) && null != lazyTableFieldEndpoint.getFieldValue();
                        })
                        .collect(Collectors.toList());

        String format = String.format("update %s  set %s where %s ",
                lazyTableStructure.schema().getTableName(),
                setLazyTableFieldEndpointList
                        .stream()
                        .map(lazyTableFieldEndpoint ->
                                lazyTableFieldEndpoint.getColumnName() + NormalUsedString.EQUALS + lazyTableFieldEndpoint.getSqlValue()

                        )
                        .collect(
                                Collectors.joining(NormalUsedString.COMMA)
                        ),
                whereLazyTableFieldEndpointList.stream()
                        .map(lazyTableFieldEndpoint -> lazyTableFieldEndpoint.getColumnName() + NormalUsedString.EQUALS + lazyTableFieldEndpoint.getSqlValue())
                        .collect(Collectors.joining(NormalUsedString.SPACE + NormalUsedString.AND + NormalUsedString.SPACE))
        );
        return format;
    }


}

