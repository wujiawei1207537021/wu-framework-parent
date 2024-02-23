package com.wu.inner.sys.adapter;


import com.wu.inner.sys.adapter.stereotype.ConvertField;
import com.wu.inner.sys.adapter.stereotype.ConvertFieldBean;
import com.wu.inner.sys.api.ConvertApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 适配器
 * 根据注解进行数据转换
 * 当前仅仅支持继承或注入使用
 * 注意事项 使用此适配器前请确认当前枚举值是否存在于字典中
 */
@ConditionalOnBean(ConvertAdapterAbstract.class)
public class DefaultConvertAdapterService extends ConvertAdapterAbstract {

    private final ConvertApi convertApi;

    /**
     * 定义公共数据字典转换map类
     */
    Map<String, Map<String, String>> map = new HashMap<>();

    public DefaultConvertAdapterService(ConvertApi convertApi) {
        this.convertApi = convertApi;
    }


    void init(Object... objects) {
        Class[] classes = Arrays.stream(objects).filter(Objects::nonNull).map(o -> {
            if (o instanceof Collection && !ObjectUtils.isEmpty(o)) {
                return ((Collection) o).iterator().next().getClass();
            }
            return o.getClass();
        }).toArray(Class[]::new);
        List<String> convertItemList = getConvertItemByClass(classes);
        if (ObjectUtils.isEmpty(convertItemList)) {
            return;
        }
        try {
            map = convertApi.getConvertDataByItems(convertItemList, true);
        } catch (Exception e) {
            log.error("fail to init api:{}", e.getMessage());
        }
    }


    void convert(Object object) {
        Class clazz = object.getClass();
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
                Class fieldType = field.getType();
                ConvertFieldBean convertFieldBean = field.getAnnotation(ConvertFieldBean.class);
                if (!ObjectUtils.isEmpty(convertFieldBean)) {
                    convertObjects(fieldVal);
                    continue;
                }
                ConvertField convertField = AnnotatedElementUtils.findMergedAnnotation(field, ConvertField.class);
                if (ObjectUtils.isEmpty(convertField) || ObjectUtils.isEmpty(fieldVal)) {
                    continue;
                }
                String convertItem = getConvertItem(field, convertField);
                // TODO 是否判断树形 或列表
                String entryName;
                String entryVal = "";
                if (!ObjectUtils.isEmpty(map.get(convertItem))) {
                    String[] keys;
                    if (!ObjectUtils.isEmpty(convertField.convertSplitCharacter())) {
                        keys = String.valueOf(fieldVal).split(String.join("|", convertField.convertSplitCharacter()));
                    } else {
                        keys = new String[]{String.valueOf(fieldVal)};
                    }
                    // 取数值
                    entryVal = Arrays.stream(keys).map(s -> map.get(convertItem).getOrDefault(s, convertField.defaultValue())).collect(Collectors.joining(","));
                }
                if (ObjectUtils.isEmpty(convertField.chineseNameProperty())) {
                    entryName = fieldName + "Name";
                } else {
                    // 注解中定义的字典项名称
                    entryName = convertField.chineseNameProperty();
                }
                // 为字典项赋值
                if (fieldType.equals(String.class)) {
//                    log.info("the field:{} type is  String ", fieldName);
//                    String combinedValue = String.format("%s,%s:%s", fieldVal, entryName, entryVal);
//                    field.set(object, combinedValue);
                }
                // 为字典项name赋值
                if (!ObjectUtils.isEmpty(fieldMap.get(entryName))) {
//                    log.info("find Attributes:{} in class:{}", entryName, clazz);
                    fieldMap.get(entryName).set(object, entryVal);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private List<String> getConvertItemByClass(Class... classes) {
        List<String> convertItemList = new ArrayList<>();
        if (ObjectUtils.isEmpty(classes)) {
            return convertItemList;
        }
        for (Class aClass : classes) {
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                ConvertField convertField = AnnotationUtils.findAnnotation(field, ConvertField.class);
                String convertItem = getConvertItem(field, convertField);
                if (ObjectUtils.isEmpty(convertItem)) {
                    continue;
                }
                if (convertItemList.contains(convertItem)) {
                    continue;
                }
                convertItemList.add(convertItem);
            }
        }
        return convertItemList;
    }

    /**
     * description 不同项目支持自定义一  需要重写该方法
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/8/25 上午9:30
     */
    public String getConvertItem(Field field, ConvertField convertField) {
        //  Annotation annotation = AnnotationUtils.getAnnotation(field, clazz);
        // 默认返回       convertField.convertItem
        if (null == convertField) {
            return null;
        }
        return convertField.convertItem();
    }
}
