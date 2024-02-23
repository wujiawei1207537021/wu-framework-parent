package com.wu.framework.inner.layer.data.dictionary;

import com.wu.framework.inner.layer.data.dictionary.api.ConvertApi;
import com.wu.framework.inner.layer.data.dictionary.convert.DefaultLazyDictionaryConvert;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 适配器
 * 根据注解进行数据转换
 * 当前仅仅支持继承或注入使用
 * 注意事项 使用此适配器前请确认当前枚举值是否存在于字典中
 *
 * @author Jia wei Wu
 * <p>1.2.0版本后弃用 请使用 DefaultLazyDictionaryConvert</>
 * @see DefaultLazyDictionaryConvert
 */
@Deprecated
public class DefaultConvertAdapterService extends AbstractConvertAdapter {

    private final ConvertApi convertApi;

    public DefaultConvertAdapterService(ConvertApi convertApi) {
        this.convertApi = convertApi;
    }


    @Override
    Map<String, Map<String, String>> init(Map<String, Map<String, String>> dictionaryDataMap, Object... objects) {
        if (!ObjectUtils.isEmpty(dictionaryDataMap)) {
            return dictionaryDataMap;
        }
        Map<String, Map<String, String>> map = new HashMap<>();
        Class<?>[] classes = Arrays.stream(objects).filter(Objects::nonNull).map(o -> {
            if (o instanceof Collection && !ObjectUtils.isEmpty(o)) {
                return ((Collection<?>) o).iterator().next().getClass();
            }
            return o.getClass();
        }).toArray(Class<?>[]::new);
        // 获取字段中含有的ConvertFieldBean 注解
        List<Class<?>> classList = new ArrayList<>();
        for (Class<?> aClass : classes) {
            classList.add(aClass);
            convertConvertFieldBean(classList, aClass);
        }

        List<String> convertItemList = getConvertItemByClass(classList);
        if (ObjectUtils.isEmpty(convertItemList)) {
            return new HashMap<>();
        }
        try {
            map = convertApi.getConvertDataByItems(convertItemList, true);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("fail to init api:{}", e.getMessage());
        }
        return map;
    }

    /**
     * 获取字段中含有的ConvertFieldBean 注解
     *
     * @param resultClass           返回的class
     * @param convertFieldBeanClass 含有ConvertFieldBean 注解的class
     */
    private List<Class<?>> convertConvertFieldBean(List<Class<?>> resultClass, Class<?> convertFieldBeanClass) {
        if (null == resultClass) {
            resultClass = new ArrayList<>();
        }
        for (Field declaredField : convertFieldBeanClass.getDeclaredFields()) {
            ConvertFieldBean mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(declaredField, ConvertFieldBean.class);
            if (mergedAnnotation != null) {
                Type genericType = declaredField.getGenericType();
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) genericType;
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    Type rawType = parameterizedType.getRawType();
                    // list 数据字段
                    if (Collection.class.isAssignableFrom((Class<?>) rawType)) {
                        Class<?> actualTypeArgument = (Class<?>) actualTypeArguments[0];
                        if (resultClass.contains(actualTypeArgument)) {
                            return resultClass;
                        }
                        resultClass.add(actualTypeArgument);
                        return convertConvertFieldBean(resultClass, actualTypeArgument);
                    }
                    // map 单层数据
                    if (rawType instanceof Map) {
                        Type mapKeyType = actualTypeArguments[0];
                        Type mapValueType = actualTypeArguments[1];
                    }
                }
            }
        }

        return resultClass;
    }


    @Override
    void convert(Object object, Map<String, Map<String, String>> dictionaryDataMap) {
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
                String convertItem = getConvertItem(field, convertField);
                // TODO 是否判断树形 或列表
                String entryName;
                String entryVal = "";
                if (!ObjectUtils.isEmpty(dictionaryDataMap.get(convertItem))) {
                    String[] keys;
                    if (!ObjectUtils.isEmpty(convertField.convertSplitCharacter())) {
                        keys = String.valueOf(fieldVal).split(String.join("|", convertField.convertSplitCharacter()));
                    } else {
                        keys = new String[]{String.valueOf(fieldVal)};
                    }
                    // 取数值
                    entryVal = Arrays.stream(keys).map(s -> dictionaryDataMap.get(convertItem).getOrDefault(s, convertField.defaultValue())).collect(Collectors.joining(","));
                }
                if (ObjectUtils.isEmpty(convertField.chineseNameProperty())) {
                    entryName = fieldName + "Name";
                } else {
                    // 注解中定义的字典项名称
                    entryName = convertField.chineseNameProperty();
                }
                // 为字典项赋值 对象添加属性
//                if (fieldType.equals(String.class)) {
//                    log.info("the field:{} type is  String ", fieldName);
//                    String combinedValue = String.format("%s,%s:%s", fieldVal, entryName, entryVal);
//                    field.set(object, combinedValue);
//                }
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


    /**
     * 获取class 中字段上的ConvertField 注解
     *
     * @param classList 当前需要转换的class
     * @return 返回字典大项
     */
    private List<String> getConvertItemByClass(List<Class<?>> classList) {
        List<String> convertItemList = new ArrayList<>();
        if (ObjectUtils.isEmpty(classList)) {
            return convertItemList;
        }
        for (Class<?> aClass : classList) {
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
