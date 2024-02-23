package com.wu.framework.inner.lazy.database.expand.database.persistence.translation.advanced;

import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.api.LazyTranslationAPI;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.endpoint.LazyTransferDataFieldEndpoint;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.endpoint.LazyTranslationFieldEndpoint;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.endpoint.LazyTranslationTableEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * description 转换适配者目标
 *
 * @author 吴佳伟
 * @date 2023/09/25 14:05
 */
@Slf4j
public class LazyTranslationAdvancedTarget extends LazyTranslationAdvancedAbstract {

    private final List<LazyTranslationAPI> lazyTranslationAPIList;

    public LazyTranslationAdvancedTarget(List<LazyTranslationAPI> lazyTranslationAPIList) {
        this.lazyTranslationAPIList = lazyTranslationAPIList;
    }

    /**
     * 支持
     *
     * @param source 原始数据
     * @return 布尔类型
     */
    @Override
    public boolean support(Object source) {
        return !ObjectUtils.isEmpty(source);
    }

    /**
     * rpc 获取转换模版
     *
     * @param classTranslationAPIListSourceConcurrentMap@return 转换模版
     */
    @Override
    ConcurrentMap<LazyTranslationTableEndpoint, ConcurrentMap<String, Object>> getTransformationTemplateMapsByItems(
            ConcurrentMap<LazyTranslationTableEndpoint, Set<Object>> classTranslationAPIListSourceConcurrentMap) {
        // 获取需要转换的数据

        ConcurrentMap<? extends Class<? extends LazyTranslationAPI>, LazyTranslationAPI> classTranslationAPIConcurrentMap = lazyTranslationAPIList.stream().collect(Collectors.toConcurrentMap(LazyTranslationAPI::getClass, translationAPI -> translationAPI));


        ConcurrentMap<LazyTranslationTableEndpoint, ConcurrentMap<String, Object>> concurrentMapConcurrentMap = classTranslationAPIListSourceConcurrentMap
                .entrySet()
                .stream()
                .filter(translationAPIEntry -> classTranslationAPIConcurrentMap.containsKey(translationAPIEntry.getKey().getLazyTranslationAPIClass()))
                .collect(Collectors.toConcurrentMap(Map.Entry::getKey, (translationAPIEntry) -> {

                    LazyTranslationTableEndpoint lazyTranslationTableEndpoint = translationAPIEntry.getKey();
                    String tableName = lazyTranslationTableEndpoint.getTranslationTargetTableName();
                    String[] columnList = lazyTranslationTableEndpoint.getColumnList();
                    LazyTranslationTableEndpoint.Type type = lazyTranslationTableEndpoint.getType();

                    Class<? extends LazyTranslationAPI> TranslationAPIClass = lazyTranslationTableEndpoint.getLazyTranslationAPIClass();
                    LazyTranslationAPI lazyTranslationAPI = classTranslationAPIConcurrentMap.get(TranslationAPIClass);

                    Set<Object> sourceValues = translationAPIEntry.getValue();

                    lazyTranslationTableEndpoint.setSourceValues(sourceValues);

                    // 获取主那换数据
                    try {
                        return lazyTranslationAPI.translation(lazyTranslationTableEndpoint);
                        // 返回数据
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException("无法通过API：" + lazyTranslationAPI + " 获取数据 ");
                    }

                }));
        return concurrentMapConcurrentMap;
    }

    /**
     * 单个对象转换需要配置枚举
     *
     * @param source                        单个对象
     * @param transferDataFieldEndpointList rpc请求过来的数据
     */
    @Override
    void doTransformation(Object source, List<LazyTransferDataFieldEndpoint> transferDataFieldEndpointList) {
        Class<?> sourceClass = source.getClass();
        for (LazyTransferDataFieldEndpoint transferDataFieldEndpoint : transferDataFieldEndpointList) {
            LazyTranslationFieldEndpoint lazyTranslationFieldEndpoint = transferDataFieldEndpoint.getLazyTranslationFieldEndpoint();
            ConcurrentMap<String, Object> transferDataMap = transferDataFieldEndpoint.getTransferDataMap();
            if (sourceClass.equals(lazyTranslationFieldEndpoint.getTargetClass())) {
                Field targetField = lazyTranslationFieldEndpoint.getTargetField();
                Field sourceField = lazyTranslationFieldEndpoint.getSourceField();
                // 数据赋值
                try {

                    if (transferDataFieldEndpoint.getLazyTranslationFieldEndpoint().getDeep()) {
                        Object targetFieldSource = targetField.get(source);
                        // 深度递归
                        List<LazyTransferDataFieldEndpoint> transferDataFieldEndpointList1 = transferDataFieldEndpoint.getTransferDataFieldEndpointList();
                        transformationObjects(transferDataFieldEndpointList1, targetFieldSource);
                    } else {
                        if (!ObjectUtils.isEmpty(transferDataMap)) {
                            Object o = sourceField.get(source);
                            targetField.setAccessible(true);
                            Object targetValue = transferDataMap.getOrDefault(o.toString(), null);
                            targetField.set(source, targetValue);
                        } else {
                            //
                            log.warn("transferDataMap is null");
                        }

                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            } else {

            }
        }


    }

    @Override
    public int getOrder() {
        return 0;
    }
}
