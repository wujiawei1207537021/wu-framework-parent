package com.wu.framework.inner.layer.data.dictionary.convert;

import com.wu.framework.inner.layer.data.convert.LazyDataFactory;
import com.wu.framework.inner.layer.data.dictionary.ConvertField;
import com.wu.framework.inner.layer.data.dictionary.ConvertFieldBean;
import com.wu.framework.inner.layer.data.dictionary.api.ConvertApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * description 默认字段转换抽象实现
 *
 * @author 吴佳伟
 * @date 2023/08/30 10:26
 */
@Slf4j
public class DefaultLazyDictionaryConvert extends AbstractLazyDictionaryConvert {

    private final ConvertApi convertApi;

    public DefaultLazyDictionaryConvert(ConvertApi convertApi) {
        this.convertApi = convertApi;
    }


    /**
     * rpc 获取转换模版
     *
     * @param convertItemList 字典大项
     * @param order           控制 code 和name 的顺序
     * @return
     */
    @Override
    protected ConcurrentHashMap<String, Map<String, String>> getConversionTemplateMapsByItems(List<String> convertItemList, boolean order) {
        Map<String, Map<String, String>> convertDataByItems = convertApi.getConvertDataByItems(convertItemList, order);
        return new ConcurrentHashMap<>(convertDataByItems);
    }

    @Override
    protected void convert(Object object, Map<String, Map<String, String>> dictionaryDataMap) {
        Class<?> clazz = object.getClass();
        Field[] clazzDeclaredFields = clazz.getDeclaredFields();
        Map<String, Field> fieldMap = Arrays.stream(clazzDeclaredFields).collect(Collectors.toMap(Field::getName, field -> {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return field;
        }));
        for (Field field : clazzDeclaredFields) {
            try {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                String fieldName = field.getName();
                Object fieldVal = field.get(object);
                Class<?> fieldType = field.getType();
                ConvertFieldBean convertFieldBean = field.getAnnotation(ConvertFieldBean.class);
                if (!ObjectUtils.isEmpty(convertFieldBean)) {
                    convertObjects(dictionaryDataMap, fieldVal);
                    continue;
                }

                ConvertField convertField = AnnotatedElementUtils.findMergedAnnotation(field, ConvertField.class);
                if (ObjectUtils.isEmpty(convertField) || ObjectUtils.isEmpty(fieldVal)) {
                    continue;
                }
                ConvertField.ConvertDictionaryWay convertDictionaryWay = convertField.convertDictionaryWay();
                String convertItem = getConvertItem(field);
                Map<String/* 如 ：1*/, String/* 如：男*/> dictionaryItemDataMap = dictionaryDataMap.get(convertItem/*字典 如：sex*/);
                String entryName;
                String entryVal = "";
                String[] keys;
                if (ConvertField.ConvertDictionaryWay.DICTIONARY_TO_CHINESE.equals(convertDictionaryWay)) {   // 字典转换成中文
                    if (!ObjectUtils.isEmpty(dictionaryItemDataMap)) {

                        if (!ObjectUtils.isEmpty(convertField.convertSplitCharacter())) {
                            keys = String.valueOf(fieldVal).split(String.join("|", convertField.convertSplitCharacter()));
                        } else {
                            keys = new String[]{String.valueOf(fieldVal)};
                        }
                        // 取数值
                        entryVal = Arrays.stream(keys).map(s -> dictionaryItemDataMap.getOrDefault(s, convertField.defaultValue())).collect(Collectors.joining(","));
                    }
                    if (ObjectUtils.isEmpty(convertField.chineseNameProperty())) {
                        entryName = fieldName + "Name";
                    } else {
                        // 注解中定义的字典项名称
                        entryName = convertField.chineseNameProperty();
                    }
                } else {   // 中文转字典

                    if (!ObjectUtils.isEmpty(dictionaryItemDataMap)) {
                        // key value 转换
                        Map<String/* 如：男*/, String/* 如 ：1*/> reversalDictionaryItemDataMap = dictionaryItemDataMap.entrySet().stream()
                                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
                        if (!ObjectUtils.isEmpty(convertField.convertSplitCharacter())) {
                            keys = String.valueOf(fieldVal).split(String.join("|", convertField.convertSplitCharacter()));
                        } else {
                            keys = new String[]{String.valueOf(fieldVal)};
                        }
                        // 取数值
                        entryVal = Arrays.stream(keys).map(s -> reversalDictionaryItemDataMap.getOrDefault(s, convertField.defaultValue())).collect(Collectors.joining(","));
                    }

                    if (ObjectUtils.isEmpty(convertField.chineseNameProperty())) {
                        entryName = fieldName.replace("Name", "");
                    } else {
                        // 注解中定义的字典项名称
                        entryName = convertField.chineseNameProperty();
                    }
                }

                // 为字典项name赋值
                if (fieldMap.containsKey(entryName)) {
                    Field entryField = fieldMap.get(entryName);
                    // 将字段转换成 字段类型的数据
                    LazyDataFactory.handler(object,entryField,entryVal);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 支持
     *
     * @param source 原始数据
     * @return 布尔类型
     */
    @Override
    public boolean support(Object source) {
        return source != null;
    }

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
