package com.wu.framework.inner.layer.data.translation.advanced;

import com.wu.framework.inner.layer.data.translation.api.TranslationAPI;
import com.wu.framework.inner.layer.data.translation.endpoint.TransferDataFieldEndpoint;
import com.wu.framework.inner.layer.data.translation.endpoint.TranslationFieldEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * description 转换适配者目标
 *
 * @author 吴佳伟
 * @date 2023/09/25 14:05
 */
@Slf4j
public class TranslationAdvancedTarget extends TranslationAdvancedAbstract {

    private final List<TranslationAPI> translationAPIList;

    public TranslationAdvancedTarget(List<TranslationAPI> translationAPIList) {
        this.translationAPIList = translationAPIList;
    }

    /**
     * 支持
     *
     * @param source 原始数据
     * @return 布尔类型
     */
    @Override
    public boolean support(Object source) {
        return true;
    }

    /**
     * rpc 获取转换模版
     *
     * @param classTranslationAPIListSourceConcurrentMap@return 转换模版
     */
    @Override
    ConcurrentMap<? extends Class<? extends TranslationAPI>, ConcurrentMap<String, Object>> getTransformationTemplateMapsByItems(
            ConcurrentMap<Class<? extends TranslationAPI>, Set<Object>> classTranslationAPIListSourceConcurrentMap) {
        // 获取需要转换的数据

        ConcurrentMap<? extends Class<? extends TranslationAPI>, TranslationAPI> classTranslationAPIConcurrentMap =
                translationAPIList.stream().collect(Collectors.toConcurrentMap(translationAPI -> {
                    if (Proxy.isProxyClass(translationAPI.getClass())) {
                        return (Class<? extends TranslationAPI>) translationAPI.getClass().getInterfaces()[0];// Feign 代理问题 不存在AOP代理对象
                    } else {
                        return translationAPI.getClass();
                    }

                }, translationAPI -> translationAPI));


        ConcurrentMap<? extends Class<? extends TranslationAPI>, ConcurrentMap<String, Object>> concurrentMapConcurrentMap =
                classTranslationAPIConcurrentMap
                        .entrySet()
                        .stream()
                        .filter(translationAPIEntry -> classTranslationAPIListSourceConcurrentMap.containsKey(translationAPIEntry.getKey()))
                        .collect(Collectors.toConcurrentMap(Map.Entry::getKey, (translationAPIEntry) -> {
                            Class<? extends TranslationAPI> TranslationAPIClass = translationAPIEntry.getKey();
                            TranslationAPI translationAPI = translationAPIEntry.getValue();

                            Set<Object> sourceValues = classTranslationAPIListSourceConcurrentMap.get(TranslationAPIClass);
                            // 获取主那换数据
                            try {
                                return translationAPI.translation(sourceValues);
                                // 返回数据
                            } catch (Exception e) {
                                e.printStackTrace();
                                log.error("无法通过API：" + translationAPI + " 获取数据 ");
                                return new ConcurrentHashMap<>();
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
    void doTransformation(Object source, List<TransferDataFieldEndpoint> transferDataFieldEndpointList) {
        Class<?> sourceClass = source.getClass();
        for (TransferDataFieldEndpoint transferDataFieldEndpoint : transferDataFieldEndpointList) {
            TranslationFieldEndpoint translationFieldEndpoint = transferDataFieldEndpoint.getTranslationFieldEndpoint();
            ConcurrentMap<String, Object> transferDataMap = transferDataFieldEndpoint.getTransferDataMap();
            if (sourceClass.equals(translationFieldEndpoint.getTargetClass())) {
                Field targetField = translationFieldEndpoint.getTargetField();
                Field sourceField = translationFieldEndpoint.getSourceField();
                // 数据赋值
                try {

                    if (transferDataFieldEndpoint.getTranslationFieldEndpoint().getDeep()) {
                        Object targetFieldSource = targetField.get(source);
                        // 深度递归
                        List<TransferDataFieldEndpoint> transferDataFieldEndpointList1 = transferDataFieldEndpoint.getTransferDataFieldEndpointList();
                        transformationObjects(transferDataFieldEndpointList1, targetFieldSource);
                    } else {
                        if (!ObjectUtils.isEmpty(transferDataMap)) {
                            Object o = sourceField.get(source);
                            if (ObjectUtils.isEmpty(o)) {
                                return;
                            }
                            targetField.setAccessible(true);
                            Object targetValue = transferDataMap.getOrDefault(o.toString(), null);
                            targetField.set(source, targetValue);
                        } else {
                            //
                            log.warn("transferDataMap is null so i can not translation this field with name " + translationFieldEndpoint.getTargetClass().getName() + "#" + targetField.getName());
                        }


                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {

            }
        }


    }

    @Override
    public int getOrder() {
        return 1024;
    }
}
