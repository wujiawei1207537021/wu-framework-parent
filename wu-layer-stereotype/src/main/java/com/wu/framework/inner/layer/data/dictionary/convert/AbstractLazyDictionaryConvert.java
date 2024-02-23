package com.wu.framework.inner.layer.data.dictionary.convert;

import com.wu.framework.inner.layer.data.dictionary.ConvertField;
import com.wu.framework.inner.layer.data.dictionary.ConvertFieldBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * description 字段转译抽象类
 *
 * @author 吴佳伟
 * @date 2023/08/30 10:25
 */
@Slf4j
public abstract class AbstractLazyDictionaryConvert implements LazyDictionaryConvert {

    /**
     * 根据对象获取原始数据转换模版
     *
     * @param sources 原始数据
     * @return
     */
    public Map<String, Map<String, String>> conversionTemplateMaps(Object... sources) {
        // TODO 适配多个对象 。。。
        ConcurrentHashMap<String, Map<String, String>> conversionTemplateMaps = new ConcurrentHashMap<String, Map<String, String>>();
        List<Class<?>> sourceClassList = sourceClassList(sources);
        List<String> convertItemList = getSourceConvertItemList(sourceClassList);
        if (ObjectUtils.isEmpty(convertItemList)) {
            return new ConcurrentHashMap<>();
        }
        try {
            conversionTemplateMaps = getConversionTemplateMapsByItems(convertItemList, true);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("fail to init api:{}", e.getMessage());
        }
        return conversionTemplateMaps;
    }

    /**
     * rpc 获取转换模版
     *
     * @param convertItemList 字典大项
     * @param order           控制 code 和name 的顺序
     * @return 转换模版
     */
    protected abstract ConcurrentHashMap<String, Map<String, String>> getConversionTemplateMapsByItems(List<String> convertItemList, boolean order);

    /**
     * 获取原始数据中所有的class
     *
     * @param sources
     * @return
     */
    public List<Class<?>> sourceClassList(Object... sources) {
        List<Class<?>> sourceClassList = Arrays.stream(Arrays.stream(sources).filter(o -> !ObjectUtils.isEmpty(o)).map(o -> {
            if (o instanceof Collection && !ObjectUtils.isEmpty(o)) {
                return ((Collection<?>) o).iterator().next().getClass();
            }
            return o.getClass();
        }).toArray(Class<?>[]::new)).collect(Collectors.toList());
        // 获取字段中含有的ConvertFieldBean 注解
        List<Class<?>> classList = new ArrayList<>();
        for (Class<?> sourceClass : sourceClassList) {
            classList.add(sourceClass);
            deepSourceClassList(classList, sourceClass);
        }
        return classList;
    }

    /**
     * @param sourceClassList     原始数据中的class
     * @param deepSourceClassList 需要深度递归的字段class类型
     * @return
     */
    public List<Class<?>> deepSourceClassList(List<Class<?>> sourceClassList, Class<?> deepSourceClassList) {
        if (null == sourceClassList) {
            sourceClassList = new ArrayList<>();
        }
        for (Field declaredField : deepSourceClassList.getDeclaredFields()) {
            if (isFieldSupportDeep(declaredField)) {
                Type genericType = declaredField.getGenericType();
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) genericType;
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    Type rawType = parameterizedType.getRawType();
                    // list 数据字段
                    if (Collection.class.isAssignableFrom((Class<?>) rawType)) {
                        Class<?> actualTypeArgument = (Class<?>) actualTypeArguments[0];
                        if (sourceClassList.contains(actualTypeArgument)) {
                            return sourceClassList;
                        }
                        sourceClassList.add(actualTypeArgument);
                        return deepSourceClassList(sourceClassList, actualTypeArgument);
                    }
                    // map 单层数据
                    if (rawType instanceof Map) {
                        Type mapKeyType = actualTypeArguments[0];
                        Type mapValueType = actualTypeArguments[1];
                    }
                }
            }
        }
        return sourceClassList;
    }


    /**
     * 判断字段是否支持深度转换
     *
     * @param field 字段
     * @return 布尔类型
     */
    public boolean isFieldSupportDeep(Field field) {
        return AnnotatedElementUtils.hasAnnotation(field, ConvertFieldBean.class);
    }

    /**
     * 获取class 中字段上的ConvertField 注解
     *
     * @param classList 当前需要转换的class
     * @return 返回字典大项
     */
    private List<String> getSourceConvertItemList(List<Class<?>> classList) {
        List<String> convertItemList = new ArrayList<>();
        if (ObjectUtils.isEmpty(classList)) {
            return convertItemList;
        }
        for (Class<?> aClass : classList) {
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                if (isFieldConvert(field)) {
                    String convertItem = getConvertItem(field);
                    if (ObjectUtils.isEmpty(convertItem)) {
                        continue;
                    }
                    if (convertItemList.contains(convertItem)) {
                        continue;
                    }
                    convertItemList.add(convertItem);
                }

            }
        }
        return convertItemList;
    }

    /**
     * 判断字段是否支持转换
     *
     * @param field 字段
     * @return
     */
    public boolean isFieldConvert(Field field) {
        return AnnotatedElementUtils.hasAnnotation(field, ConvertField.class);
    }

    /**
     * 获取字段转换 item
     *
     * @param field 字段
     * @return
     */
    public String getConvertItem(Field field) {
        ConvertField mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(field, ConvertField.class);
        return mergedAnnotation.convertItem();
    }

    /**
     * @param dictionaryDataMap 字典模版
     * @param objects           转换多个对象
     * @return description 模糊转换
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/6/17 3:40 下午
     */
    public void convertObjects(Map<String, Map<String, String>> dictionaryDataMap, Object... objects) {
        if (ObjectUtils.isEmpty(objects)) {
            return;
        }
        for (Object object : objects) {
            if (ObjectUtils.isEmpty(object)) {
                continue;
            }
            if (object instanceof Collection) {
                convertCollection(dictionaryDataMap, (Collection) object);
            } else {
                convert(object, dictionaryDataMap);
            }
        }
    }

    /**
     * 转换集合
     *
     * @param dictionaryDataMap 字典模版
     * @param collection        转换对象集合
     */
    protected void convertCollection(Map<String, Map<String, String>> dictionaryDataMap, Collection<?> collection) {
        for (Object o : collection) {
            if (o instanceof Collection) {
                convertCollection(dictionaryDataMap, (Collection<?>) o);
            } else {
                convert(o, dictionaryDataMap);
            }
        }
    }


    /**
     * 单个对象转换需要配置枚举
     *
     * @param object            单个对象
     * @param dictionaryDataMap 字典模版
     */
    protected abstract void convert(Object object, Map<String, Map<String, String>> dictionaryDataMap);


    /**
     * 字段转译
     *
     * @param source 原始数据
     */
    @Override
    public void transformation(Object source) {
        Map<String, Map<String, String>> stringObjectConcurrentHashMap = conversionTemplateMaps(source);
        convertObjects(stringObjectConcurrentHashMap, source);
    }
}
