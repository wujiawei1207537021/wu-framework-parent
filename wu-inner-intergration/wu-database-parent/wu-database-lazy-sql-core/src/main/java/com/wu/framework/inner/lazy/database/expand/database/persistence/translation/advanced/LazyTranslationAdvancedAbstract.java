package com.wu.framework.inner.lazy.database.expand.database.persistence.translation.advanced;


import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.LazyTableTranslationBean;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.LazyTableTranslationLayer;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.LazyTableTranslationOneField;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.api.LazyTranslationAPI;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.endpoint.LazyTransferDataFieldEndpoint;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.endpoint.LazyTranslationClassEndpoint;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.endpoint.LazyTranslationFieldEndpoint;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.endpoint.LazyTranslationTableEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * description 数据库ORM转译适配者抽象类
 *
 * @author 吴佳伟
 * @date 2023/09/22 14:26
 */
@Slf4j
public abstract class LazyTranslationAdvancedAbstract implements LazyTranslationAdvanced {
    /**
     * 根据字段信息提取需要转换的目标表信息
     *
     * @param lazyTranslationFieldEndpoint 字段信息
     * @return 目标表信息
     */
    private static LazyTranslationTableEndpoint getLazyTranslationTableEndpoint(LazyTranslationFieldEndpoint lazyTranslationFieldEndpoint) {
        String translationTargetTableName = lazyTranslationFieldEndpoint.getTranslationTargetTableName();
        Class<?> translationTargetTableClass = lazyTranslationFieldEndpoint.getTranslationTargetTableClass();
        String[] columnList = lazyTranslationFieldEndpoint.getColumnList();
        LazyTranslationTableEndpoint.Type type = lazyTranslationFieldEndpoint.getType();
        Class<?> translationTargetType = lazyTranslationFieldEndpoint.getTranslationTargetType();
        Class<? extends LazyTranslationAPI> lazyTranslationAPIClass = lazyTranslationFieldEndpoint.getLazyTranslationAPIClass();

        LazyTranslationTableEndpoint lazyTranslationTableEndpoint = new LazyTranslationTableEndpoint();
        lazyTranslationTableEndpoint.setTranslationTargetTableName(translationTargetTableName);
        lazyTranslationTableEndpoint.setTranslationTargetTableClass(translationTargetTableClass);
        lazyTranslationTableEndpoint.setTranslationTargetType(translationTargetType);
        lazyTranslationTableEndpoint.setColumnList(columnList);
        lazyTranslationTableEndpoint.setType(type);
        lazyTranslationTableEndpoint.setLazyTranslationAPIClass(lazyTranslationAPIClass);
        lazyTranslationTableEndpoint.setTranslationSourceName(lazyTranslationFieldEndpoint.getTranslationSourceName());
        lazyTranslationTableEndpoint.setTranslationTargetName(lazyTranslationFieldEndpoint.getTranslationTargetName());
        lazyTranslationTableEndpoint.setSourceValues(lazyTranslationFieldEndpoint.getSourceValues());
        return lazyTranslationTableEndpoint;
    }

    /**
     * 根据对象获取原始数据转换模版
     *
     * @param sources 原始数据
     * @return
     */
    public List<LazyTransferDataFieldEndpoint> transformationTemplateMaps(Object... sources) {
        List<LazyTranslationClassEndpoint> lazyTranslationClassEndpointList = sourceClassEndpointList(sources);

        // 解析sourceValues 准备使用原始数据换取需要转译的数据
        sourceValueObjects(lazyTranslationClassEndpointList, sources);
        List<LazyTransferDataFieldEndpoint> transferDataFieldEndpointList = new ArrayList<>();
        try {
            transferDataFieldEndpointList = getTransformationTemplateMapsByRpc(lazyTranslationClassEndpointList, true);

        } catch (Exception e) {
            e.printStackTrace();
//            log.error("fail to init api:{}", e.getMessage());
        }
        return transferDataFieldEndpointList;
    }

    /**
     * 通过Rpc 聚合转译数据并填充到 TransferDataFieldEndpoint 中
     *
     * @param lazyTranslationClassEndpointList 需要转译的数据结构
     * @param order                            排序顺序
     * @return
     */
    private List<LazyTransferDataFieldEndpoint> getTransformationTemplateMapsByRpc(List<LazyTranslationClassEndpoint> lazyTranslationClassEndpointList, boolean order) {
        // 递归出所有的 sourceValues
        ConcurrentMap<LazyTranslationTableEndpoint, Set<Object>> sourceValuesByTranslationClassEndpointList =
                getSourceValuesByTranslationClassEndpointList(lazyTranslationClassEndpointList, null);

        // 转译数据
        ConcurrentMap<LazyTranslationTableEndpoint/*字段注解上写的关于表及映射信息*/, ConcurrentMap<String, Object>/*返回的转译数据*/> transformationTemplateMapsByItems = getTransformationTemplateMapsByItems(sourceValuesByTranslationClassEndpointList);

        // 根据 lazyTranslationClassEndpointList 递归出 List<TransferDataFieldEndpoint>

        return setTransferDataFieldEndpointList(lazyTranslationClassEndpointList, transformationTemplateMapsByItems, null);
    }

    /**
     * 创建含有转译数据的 数据结构对象
     *
     * @param lazyTranslationClassEndpointList  转译class
     * @param transformationTemplateMapsByItems 转译后的数据
     * @param transferDataFieldEndpointList     需要返回的含有转译数据的 数据结构对象
     * @return
     */
    private List<LazyTransferDataFieldEndpoint> setTransferDataFieldEndpointList(
            List<LazyTranslationClassEndpoint> lazyTranslationClassEndpointList,
            ConcurrentMap<LazyTranslationTableEndpoint, ConcurrentMap<String, Object>> transformationTemplateMapsByItems,
            List<LazyTransferDataFieldEndpoint> transferDataFieldEndpointList) {
        if (ObjectUtils.isEmpty(transferDataFieldEndpointList)) {
            transferDataFieldEndpointList = new ArrayList<>();
        }
        for (LazyTranslationClassEndpoint lazyTranslationClassEndpoint : lazyTranslationClassEndpointList) {
            Class<?> translationClass = lazyTranslationClassEndpoint.getTranslationClass();
            List<LazyTranslationFieldEndpoint> lazyTranslationFieldEndpointList = lazyTranslationClassEndpoint.getLazyTranslationFieldEndpointList();
            for (LazyTranslationFieldEndpoint lazyTranslationFieldEndpoint : lazyTranslationFieldEndpointList) {
                LazyTransferDataFieldEndpoint transferDataFieldEndpoint = new LazyTransferDataFieldEndpoint();
                transferDataFieldEndpoint.setLazyTranslationFieldEndpoint(lazyTranslationFieldEndpoint);
                if (lazyTranslationFieldEndpoint.getDeep()) {
                    // 深度递归
                    List<LazyTransferDataFieldEndpoint> transferDataFieldEndpointList1 = setTransferDataFieldEndpointList(
                            lazyTranslationFieldEndpoint.getMultipleLazyTranslationClassEndpointList(),
                            transformationTemplateMapsByItems,
                            null);
                    transferDataFieldEndpoint.setTransferDataFieldEndpointList(transferDataFieldEndpointList1);
                    transferDataFieldEndpointList.add(transferDataFieldEndpoint);
                } else {
                    LazyTranslationTableEndpoint lazyTranslationTableEndpoint = getLazyTranslationTableEndpoint(lazyTranslationFieldEndpoint);
                    transferDataFieldEndpoint.setTransferDataMap(transformationTemplateMapsByItems.getOrDefault(lazyTranslationTableEndpoint, null));
                    transferDataFieldEndpointList.add(transferDataFieldEndpoint);
                }

            }

        }
        return transferDataFieldEndpointList;

    }

    /**
     * 解析转换class对象
     *
     * @return
     */
    private ConcurrentMap<LazyTranslationTableEndpoint, Set<Object>> getSourceValuesByTranslationClassEndpointList(List<LazyTranslationClassEndpoint> lazyTranslationClassEndpointList, ConcurrentMap<LazyTranslationTableEndpoint, Set<Object>> classListConcurrentMap) {

        if (ObjectUtils.isEmpty(classListConcurrentMap)) {
            classListConcurrentMap = new ConcurrentHashMap<>();
        }
        for (LazyTranslationClassEndpoint lazyTranslationClassEndpoint : lazyTranslationClassEndpointList) {
            for (LazyTranslationFieldEndpoint lazyTranslationFieldEndpoint : lazyTranslationClassEndpoint.getLazyTranslationFieldEndpointList()) {
                Boolean deep = lazyTranslationFieldEndpoint.getDeep();
                if (deep) {
                    // 深度递归
                    ConcurrentMap<LazyTranslationTableEndpoint, Set<Object>> sourceValuesByTranslationClassEndpointList = getSourceValuesByTranslationClassEndpointList(lazyTranslationFieldEndpoint.getMultipleLazyTranslationClassEndpointList(), classListConcurrentMap);
                    for (Map.Entry<LazyTranslationTableEndpoint, Set<Object>> classSetEntry : sourceValuesByTranslationClassEndpointList.entrySet()) {
                        LazyTranslationTableEndpoint translationTableEndpoint = classSetEntry.getKey();
                        Set<Object> value = classSetEntry.getValue();
                        Set<Object> objects = classListConcurrentMap.getOrDefault(translationTableEndpoint, value);
                        objects.addAll(value);
                        classListConcurrentMap.put(translationTableEndpoint, objects);
                    }

                } else {
                    Set<Object> sourceValues = lazyTranslationFieldEndpoint.getSourceValues();

                    LazyTranslationTableEndpoint lazyTranslationTableEndpoint = getLazyTranslationTableEndpoint(lazyTranslationFieldEndpoint);
                    Set<Object> orDefault = classListConcurrentMap.getOrDefault(lazyTranslationTableEndpoint, sourceValues);
                    orDefault.addAll(sourceValues);
                    classListConcurrentMap.put(lazyTranslationTableEndpoint, orDefault);

                }
            }
        }
        return classListConcurrentMap;
    }

    /**
     * rpc 获取转换模版
     *
     * @param classTranslationAPIListSourceConcurrentMap@return 转换模版
     */
    abstract ConcurrentMap<LazyTranslationTableEndpoint, ConcurrentMap<String, Object>/*原始转译字段、转译数据*/> getTransformationTemplateMapsByItems(ConcurrentMap<LazyTranslationTableEndpoint/*对应表*/, Set<Object>/*api对应sourceValue值*/> classTranslationAPIListSourceConcurrentMap);

    /**
     * sourceValue赋值
     *
     * @param lazyTranslationClassEndpointList 解析出的所有注解对象
     * @param sources                          原始数据
     */
    public void sourceValueObjects(List<LazyTranslationClassEndpoint> lazyTranslationClassEndpointList, Object... sources) {

        for (Object source : sources) {
            if (Collection.class.isAssignableFrom(source.getClass())) {
                sourceValueList(lazyTranslationClassEndpointList, (Collection<?>) source);
            } else {
                sourceValueOne(lazyTranslationClassEndpointList, source);
            }
        }
    }

    /**
     * sourceValue赋值
     *
     * @param lazyTranslationClassEndpointList 解析出的所有注解对象
     * @param sources                          原始数据
     */
    public void sourceValueList(List<LazyTranslationClassEndpoint> lazyTranslationClassEndpointList, Collection<?> sources) {

        for (Object source : sources) {
            if (Collection.class.isAssignableFrom(source.getClass())) {
                sourceValueList(lazyTranslationClassEndpointList, (Collection<?>) source);
            } else {
                sourceValueOne(lazyTranslationClassEndpointList, source);
            }
        }
    }

    /**
     * sourceValue赋值
     *
     * @param lazyTranslationClassEndpointList 解析出的所有注解对象
     * @param source                           原始数据
     */
    public void sourceValueOne(List<LazyTranslationClassEndpoint> lazyTranslationClassEndpointList, Object source) {

        ConcurrentMap<? extends Class<?>, LazyTranslationClassEndpoint> translationClassEndpointConcurrentMap = lazyTranslationClassEndpointList.stream().collect(Collectors.toConcurrentMap(LazyTranslationClassEndpoint::getTranslationClass, Function.identity()));
        if (translationClassEndpointConcurrentMap.containsKey(source.getClass())) {

            LazyTranslationClassEndpoint lazyTranslationClassEndpoint = translationClassEndpointConcurrentMap.get(source.getClass());
            lazyTranslationClassEndpoint.getLazyTranslationFieldEndpointList().forEach(lazyTranslationFieldEndpoint -> {
                Field sourceField = lazyTranslationFieldEndpoint.getSourceField();

                Object o;
                try {

                    // 如果是深度解析的TranslationBean 获取数据后再次获取数据
                    if (Boolean.TRUE.equals(lazyTranslationFieldEndpoint.getDeep())) {
                        // target field
                        Field targetField = lazyTranslationFieldEndpoint.getTargetField();
                        targetField.setAccessible(true);
                        Object targetValue = targetField.get(source);
                        List<LazyTranslationClassEndpoint> multipleLazyTranslationClassEndpointList = lazyTranslationFieldEndpoint.getMultipleLazyTranslationClassEndpointList();
                        sourceValueObjects(multipleLazyTranslationClassEndpointList, targetValue);
                        return;
                    } else {
                        sourceField.setAccessible(true);
                        o = sourceField.get(source);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                lazyTranslationFieldEndpoint.getSourceValues().add(o);
            });
        }
    }

    /**
     * 获取原始数据中所有的class
     *
     * @param sources
     * @return
     */
    public List<LazyTranslationClassEndpoint> sourceClassEndpointList(Object... sources) {
        List<Class<?>> sourceClassList = Arrays.stream(Arrays.stream(sources).filter(o -> !ObjectUtils.isEmpty(o)).map(o -> {
            if (o instanceof Collection && !ObjectUtils.isEmpty(o)) {
                return ((Collection<?>) o).iterator().next().getClass();
            }
            return o.getClass();
        }).toArray(Class<?>[]::new)).toList();
        // 获取字段中含有的 TranslationBean 注解
        List<LazyTranslationClassEndpoint> lazyTranslationClassEndpointList = new ArrayList<>();
        for (Class<?> sourceClass : sourceClassList) {
            List<LazyTranslationClassEndpoint> classEndpointList = doSourceClassEndpoint(null, sourceClass);
            lazyTranslationClassEndpointList.addAll(classEndpointList);
        }
        return lazyTranslationClassEndpointList;
    }

    /**
     * @param lazyTranslationClassEndpointList 原始数据中的 translationClassEndpoint
     * @param sourceClass                      需要深度递归的字段class类型
     * @return
     */
    public List<LazyTranslationClassEndpoint> doSourceClassEndpoint(List<LazyTranslationClassEndpoint> lazyTranslationClassEndpointList, Class<?> sourceClass) {
        if (null == lazyTranslationClassEndpointList) {
            lazyTranslationClassEndpointList = new ArrayList<>();
        }
        Field[] fields = sourceClass.getDeclaredFields();
        ConcurrentMap<String, Field> fieldConcurrentMap = Arrays.stream(fields).collect(Collectors.toConcurrentMap(Field::getName, Function.identity()));// 当前字段降维
        LazyTranslationClassEndpoint lazyTranslationClassEndpoint = new LazyTranslationClassEndpoint();// 转译的class
        lazyTranslationClassEndpoint.setTranslationClass(sourceClass);
        if (lazyTranslationClassEndpointList.contains(lazyTranslationClassEndpoint)) {
            return lazyTranslationClassEndpointList;
        }
        List<LazyTranslationFieldEndpoint> lazyTranslationFieldEndpointList = Arrays.stream(fields).filter(this::isTranslationLayer).map(field -> {
            LazyTranslationFieldEndpoint lazyTranslationFieldEndpoint = new LazyTranslationFieldEndpoint();

            // 深度解析
            if (isFieldSupportDeep(field)) {
                Class<?> fieldType = field.getType();
                Type genericType = field.getGenericType();
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) genericType;
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    Type rawType = parameterizedType.getRawType();
                    // list 数据字段
                    if (Collection.class.isAssignableFrom((Class<?>) rawType)) {
                        Class<?> actualTypeArgument = (Class<?>) actualTypeArguments[0];
                        fieldType = actualTypeArgument;
                    }
                    // map 单层数据
                    if (rawType instanceof Map) {
                        Type mapKeyType = actualTypeArguments[0];
                        Type mapValueType = actualTypeArguments[1];
                    }
                }
                lazyTranslationFieldEndpoint.setTargetClass(sourceClass);
                // 对象字段深度解析 转换
                lazyTranslationFieldEndpoint.setDeep(true);
                lazyTranslationFieldEndpoint.setTransferDataName(field.getName());
                lazyTranslationFieldEndpoint.setTranslationTargetName(field.getName());
                lazyTranslationFieldEndpoint.setTargetField(field);
                List<LazyTranslationClassEndpoint> lazyTranslationClassEndpoints = doSourceClassEndpoint(null, fieldType);
                lazyTranslationFieldEndpoint.getMultipleLazyTranslationClassEndpointList().addAll(lazyTranslationClassEndpoints);

            } else if (isFieldConvert(field)) {
                lazyTranslationFieldEndpoint = getTranslationFieldEndpoint(field);
                lazyTranslationFieldEndpoint.setDeep(false);
                if (ObjectUtils.isEmpty(lazyTranslationFieldEndpoint)) {
                    return null;
                }
                lazyTranslationFieldEndpoint.setTargetClass(sourceClass);
                String translationSourceName = lazyTranslationFieldEndpoint.getTranslationSourceName();// 目标字段
                if (fieldConcurrentMap.containsKey(translationSourceName)) {// 重写toString、hash
                    Field sourceField = fieldConcurrentMap.get(translationSourceName);
                    lazyTranslationFieldEndpoint.setSourceField(sourceField);
                } else {
                    throw new RuntimeException("配置错误无法从当前class: " + sourceClass.getName() + " 找到原始数据字段: " + translationSourceName);
                }
            }

            return lazyTranslationFieldEndpoint;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        lazyTranslationClassEndpoint.setLazyTranslationFieldEndpointList(lazyTranslationFieldEndpointList);
        lazyTranslationClassEndpointList.add(lazyTranslationClassEndpoint);
        return lazyTranslationClassEndpointList;
    }


    /**
     * 判断字段是否支持深度转换
     *
     * @param field 字段
     * @return 布尔类型
     */
    public boolean isFieldSupportDeep(Field field) {
        return AnnotatedElementUtils.hasAnnotation(field, LazyTableTranslationBean.class);
    }

    /**
     * 获取class 中字段上的ConvertField 注解
     *
     * @param classList 当前需要转换的class
     * @return 返回字典大项
     */
    private List<LazyTranslationClassEndpoint> getSourceTranslationFieldEndpointList(List<Class<?>> classList) {
        List<LazyTranslationClassEndpoint> lazyTranslationClassEndpointList = new ArrayList<>();
        if (ObjectUtils.isEmpty(classList)) {
            return lazyTranslationClassEndpointList;
        }


        for (Class<?> aClass : classList) {
            Field[] fields = aClass.getDeclaredFields();
            ConcurrentMap<String, Field> fieldConcurrentMap = Arrays.stream(fields).collect(Collectors.toConcurrentMap(Field::getName, Function.identity()));// 当前字段降维
            LazyTranslationClassEndpoint lazyTranslationClassEndpoint = new LazyTranslationClassEndpoint();// 转译的class
            lazyTranslationClassEndpoint.setTranslationClass(aClass);
            if (lazyTranslationClassEndpointList.contains(lazyTranslationClassEndpoint)) {
                continue;
            }
            List<LazyTranslationFieldEndpoint> lazyTranslationFieldEndpointList = Arrays.stream(fields).filter(this::isFieldConvert).map(field -> {
                LazyTranslationFieldEndpoint lazyTranslationFieldEndpoint = getTranslationFieldEndpoint(field);
                if (ObjectUtils.isEmpty(lazyTranslationFieldEndpoint)) {
                    return null;
                }
                lazyTranslationFieldEndpoint.setTargetClass(aClass);
                String translationSourceName = lazyTranslationFieldEndpoint.getTranslationSourceName();// 目标字段
                if (fieldConcurrentMap.containsKey(translationSourceName)) {// 重写toString、hash
                    Field sourceField = fieldConcurrentMap.get(translationSourceName);
                    lazyTranslationFieldEndpoint.setSourceField(sourceField);
                } else {
                    throw new RuntimeException("配置错误无法从当前class: " + aClass.getName() + " 找到原始数据字段: " + translationSourceName);
                }
                if (isFieldConvert(field)) {
                    // 字段注解
                    lazyTranslationFieldEndpoint.setDeep(false);
                } else if (isFieldSupportDeep(field)) {
                    // 对象字段深度解析 转换
                    lazyTranslationFieldEndpoint.setDeep(true);
                }
                return lazyTranslationFieldEndpoint;
            }).filter(Objects::nonNull).collect(Collectors.toList());
            // 对象字段深度解析 转换
            List<LazyTranslationFieldEndpoint> deepFieldEndpointList = Arrays.stream(fields).filter(this::isFieldSupportDeep).map(field -> {
                Class<?> fieldType = field.getType();

                LazyTranslationFieldEndpoint lazyTranslationFieldEndpoint = new LazyTranslationFieldEndpoint();
                lazyTranslationFieldEndpoint.setTargetClass(aClass);
                // 对象字段深度解析 转换
                lazyTranslationFieldEndpoint.setDeep(true);
                lazyTranslationFieldEndpoint.setTransferDataName(field.getName());
                lazyTranslationFieldEndpoint.setTranslationTargetName(field.getName());
                lazyTranslationFieldEndpoint.setTargetField(field);
                return lazyTranslationFieldEndpoint;
            }).toList();
            lazyTranslationFieldEndpointList.addAll(deepFieldEndpointList);
            lazyTranslationClassEndpoint.setLazyTranslationFieldEndpointList(lazyTranslationFieldEndpointList);
            lazyTranslationClassEndpointList.add(lazyTranslationClassEndpoint);
        }
        return lazyTranslationClassEndpointList;
    }

    /**
     * 判断字段是否支持转换
     *
     * @param field 字段
     * @return
     */
    public boolean isTranslationLayer(Field field) {
        return AnnotatedElementUtils.hasAnnotation(field, LazyTableTranslationLayer.class);
    }

    /**
     * 判断字段是否支持转换
     *
     * @param field 字段
     * @return
     */
    public boolean isFieldConvert(Field field) {
        return AnnotatedElementUtils.hasAnnotation(field, LazyTableTranslationOneField.class);
    }

    /**
     * 获取字段转换 item
     *
     * @param field 字段
     * @return LazyTranslationFieldEndpoint
     */
    public LazyTranslationFieldEndpoint getTranslationFieldEndpoint(Field field) {
        LazyTableTranslationOneField translationField = AnnotatedElementUtils.findMergedAnnotation(field, LazyTableTranslationOneField.class);
        LazyTranslationFieldEndpoint lazyTranslationFieldEndpoint = new LazyTranslationFieldEndpoint();
        if (null == translationField) {
            return null;
        }
        lazyTranslationFieldEndpoint.setTranslationSourceName(translationField.translationSourceName());
        lazyTranslationFieldEndpoint.setTransferDataName(ObjectUtils.isEmpty(translationField.transferDataName()) ? field.getName() : translationField.transferDataName());
        lazyTranslationFieldEndpoint.setTranslationTargetName(ObjectUtils.isEmpty(translationField.translationTargetName()) ? field.getName() : translationField.translationTargetName());
        lazyTranslationFieldEndpoint.setTranslationTargetType(translationField.translationTargetType());
        lazyTranslationFieldEndpoint.setTargetField(field);

        lazyTranslationFieldEndpoint.setTranslationTargetTableName(translationField.translationTargetTableName());
        lazyTranslationFieldEndpoint.setTranslationTargetTableClass(translationField.translationTargetTableClass());
        lazyTranslationFieldEndpoint.setColumnList(translationField.columnList());
        lazyTranslationFieldEndpoint.setType(translationField.type());
        lazyTranslationFieldEndpoint.setLazyTranslationAPIClass(translationField.lazyTranslationAPIClass());
        return lazyTranslationFieldEndpoint;
    }


    /**
     * @param transferDataEndpointConcurrentMap 转换数据map
     * @param sources                           转换多个对象
     * @return description 模糊转换
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/6/17 3:40 下午
     */
    public void transformationObjects(List<LazyTransferDataFieldEndpoint> transferDataEndpointConcurrentMap, Object... sources) {
        if (ObjectUtils.isEmpty(sources)) {
            return;
        }
        for (Object source : sources) {
            if (ObjectUtils.isEmpty(source)) {
                continue;
            }
            if (source instanceof Collection) {
                transformationCollection(transferDataEndpointConcurrentMap, (Collection<?>) source);
            } else {
                doTransformation(source, transferDataEndpointConcurrentMap);
            }
        }
    }

    /**
     * 转换集合
     *
     * @param transferDataEndpointConcurrentMap 转换数据map
     * @param collection                        转换对象集合
     */
    protected void transformationCollection(List<LazyTransferDataFieldEndpoint> transferDataEndpointConcurrentMap, Collection<?> collection) {
        for (Object o : collection) {
            if (o instanceof Collection) {
                transformationCollection(transferDataEndpointConcurrentMap, (Collection<?>) o);
            } else {
                doTransformation(o, transferDataEndpointConcurrentMap);
            }
        }
    }


    /**
     * 单个对象转换需要配置枚举
     *
     * @param source                            单个对象
     * @param transferDataEndpointConcurrentMap 转换数据map
     */
    abstract void doTransformation(Object source, List<LazyTransferDataFieldEndpoint> transferDataEndpointConcurrentMap);


    /**
     * 字段转译
     *
     * @param source 原始数据
     */
    @Override
    public void transformation(Object source) {
        // 解析对象身上的注解

        List<LazyTransferDataFieldEndpoint> transferDataEndpointConcurrentMap = transformationTemplateMaps(source);

        transformationObjects(transferDataEndpointConcurrentMap, source);
    }


}
