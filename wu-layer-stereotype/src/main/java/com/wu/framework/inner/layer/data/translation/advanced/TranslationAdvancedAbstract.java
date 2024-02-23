package com.wu.framework.inner.layer.data.translation.advanced;

import com.wu.framework.inner.layer.data.translation.TranslationBean;
import com.wu.framework.inner.layer.data.translation.TranslationField;
import com.wu.framework.inner.layer.data.translation.TranslationLayer;
import com.wu.framework.inner.layer.data.translation.api.TranslationAPI;
import com.wu.framework.inner.layer.data.translation.endpoint.TransferDataFieldEndpoint;
import com.wu.framework.inner.layer.data.translation.endpoint.TranslationClassEndpoint;
import com.wu.framework.inner.layer.data.translation.endpoint.TranslationFieldEndpoint;
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
 * description 转译适配者抽象类
 *
 * @author 吴佳伟
 * @date 2023/09/22 14:26
 */
@Slf4j
public abstract class TranslationAdvancedAbstract implements TranslationAdvanced {
    /**
     * 根据对象获取原始数据转换模版
     *
     * @param sources 原始数据
     * @return
     */
    public List<TransferDataFieldEndpoint> transformationTemplateMaps(Object... sources) {
        // TODO 适配多个对象 。。。
        List<TranslationClassEndpoint> translationClassEndpointList = sourceClassEndpointList(sources);

        // 解析sourceValues 准备使用原始数据换取需要转译的数据
        sourceValueObjects(translationClassEndpointList, sources);
        List<TransferDataFieldEndpoint> transferDataFieldEndpointList = new ArrayList<>();
        try {
            transferDataFieldEndpointList = getTransformationTemplateMapsByRpc(translationClassEndpointList, true);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("fail to init api:{}", e.getMessage());
        }
        return transferDataFieldEndpointList;
    }

    /**
     * 通过Rpc 聚合转译数据并填充到 TransferDataFieldEndpoint 中
     *
     * @param translationClassEndpointList 需要转译的数据结构
     * @param order                        排序顺序
     * @return
     */
    private List<TransferDataFieldEndpoint> getTransformationTemplateMapsByRpc(List<TranslationClassEndpoint> translationClassEndpointList, boolean order) {
        // 递归出所有的 sourceValues
        ConcurrentMap<Class<? extends TranslationAPI>, Set<Object>> sourceValuesByTranslationClassEndpointList =
                getSourceValuesByTranslationClassEndpointList(translationClassEndpointList, null);

        // 转译数据
        ConcurrentMap<? extends Class<? extends TranslationAPI>, ConcurrentMap<String, Object>> transformationTemplateMapsByItems =
                getTransformationTemplateMapsByItems(sourceValuesByTranslationClassEndpointList);

        // 根据 translationClassEndpointList 递归出 List<TransferDataFieldEndpoint>

        return setTransferDataFieldEndpointList(translationClassEndpointList, transformationTemplateMapsByItems, null);
    }

    /**
     * 创建含有转译数据的 数据结构对象
     *
     * @param translationClassEndpointList      转译class
     * @param transformationTemplateMapsByItems 转译后的数据
     * @param transferDataFieldEndpointList     需要返回的含有转译数据的 数据结构对象
     * @return
     */
    private List<TransferDataFieldEndpoint> setTransferDataFieldEndpointList(
            List<TranslationClassEndpoint> translationClassEndpointList,
            ConcurrentMap<? extends Class<? extends TranslationAPI>, ConcurrentMap<String, Object>> transformationTemplateMapsByItems,
            List<TransferDataFieldEndpoint> transferDataFieldEndpointList) {
        if (ObjectUtils.isEmpty(transferDataFieldEndpointList)) {
            transferDataFieldEndpointList = new ArrayList<>();
        }
        for (TranslationClassEndpoint translationClassEndpoint : translationClassEndpointList) {
            Class<?> translationClass = translationClassEndpoint.getTranslationClass();
            List<TranslationFieldEndpoint> translationFieldEndpointList = translationClassEndpoint.getTranslationFieldEndpointList();
            for (TranslationFieldEndpoint translationFieldEndpoint : translationFieldEndpointList) {
                TransferDataFieldEndpoint transferDataFieldEndpoint = new TransferDataFieldEndpoint();
                transferDataFieldEndpoint.setTranslationFieldEndpoint(translationFieldEndpoint);
                if (translationFieldEndpoint.getDeep()) {
                    // 深度递归
                    List<TransferDataFieldEndpoint> transferDataFieldEndpointList1 = setTransferDataFieldEndpointList(
                            translationFieldEndpoint.getMultipleTranslationClassEndpointList(),
                            transformationTemplateMapsByItems,
                            null);
                    transferDataFieldEndpoint.setTransferDataFieldEndpointList(transferDataFieldEndpointList1);
                    transferDataFieldEndpointList.add(transferDataFieldEndpoint);
                } else {
                    Class<? extends TranslationAPI> smartConvertAdapter = translationFieldEndpoint.getTranslationAPI();
                    transferDataFieldEndpoint.setTransferDataMap(transformationTemplateMapsByItems.getOrDefault(smartConvertAdapter, null));
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
    private ConcurrentMap<Class<? extends TranslationAPI>, Set<Object>> getSourceValuesByTranslationClassEndpointList(
            List<TranslationClassEndpoint> translationClassEndpointList,
            ConcurrentMap<Class<? extends TranslationAPI>, Set<Object>> classListConcurrentMap) {

        if (ObjectUtils.isEmpty(classListConcurrentMap)) {
            classListConcurrentMap = new ConcurrentHashMap<>();
        }
        for (TranslationClassEndpoint translationClassEndpoint : translationClassEndpointList) {
            for (TranslationFieldEndpoint translationFieldEndpoint : translationClassEndpoint.getTranslationFieldEndpointList()) {
                Boolean deep = translationFieldEndpoint.getDeep();
                if (deep) {
                    // 深度递归
                    ConcurrentMap<Class<? extends TranslationAPI>, Set<Object>> sourceValuesByTranslationClassEndpointList = getSourceValuesByTranslationClassEndpointList(translationFieldEndpoint.getMultipleTranslationClassEndpointList(), classListConcurrentMap);
                    for (Map.Entry<Class<? extends TranslationAPI>, Set<Object>> classSetEntry : sourceValuesByTranslationClassEndpointList.entrySet()) {
                        Class<? extends TranslationAPI> translationAPIClass = classSetEntry.getKey();
                        Set<Object> value = classSetEntry.getValue();
                        Set<Object> objects = classListConcurrentMap.getOrDefault(translationAPIClass, value);
                        objects.addAll(value);
                        classListConcurrentMap.put(translationAPIClass, objects);
                    }

                    classListConcurrentMap.putAll(sourceValuesByTranslationClassEndpointList);

                } else {
                    Set<Object> sourceValues = translationFieldEndpoint.getSourceValues();
                    Class<? extends TranslationAPI> translationAPI = translationFieldEndpoint.getTranslationAPI();
                    Set<Object> orDefault = classListConcurrentMap.getOrDefault(translationAPI, sourceValues);
                    orDefault.addAll(sourceValues);
                    classListConcurrentMap.put(translationAPI, orDefault);

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
    abstract ConcurrentMap<? extends Class<? extends TranslationAPI>, ConcurrentMap<String, Object>/*原始转译字段、转译数据*/> getTransformationTemplateMapsByItems(ConcurrentMap<Class<? extends TranslationAPI>/*对应api*/, Set<Object>/*api对应sourceValue值*/> classTranslationAPIListSourceConcurrentMap);

    /**
     * sourceValue赋值
     *
     * @param translationClassEndpointList 解析出的所有注解对象
     * @param sources                      原始数据
     */
    public void sourceValueObjects(List<TranslationClassEndpoint> translationClassEndpointList, Object... sources) {

        for (Object source : sources) {
            if (source != null) {
                if (Collection.class.isAssignableFrom(source.getClass())) {
                    sourceValueList(translationClassEndpointList, (Collection<?>) source);
                } else {
                    sourceValueOne(translationClassEndpointList, source);
                }
            }
        }
    }

    /**
     * sourceValue赋值
     *
     * @param translationClassEndpointList 解析出的所有注解对象
     * @param sources                      原始数据
     */
    public void sourceValueList(List<TranslationClassEndpoint> translationClassEndpointList, Collection<?> sources) {

        for (Object source : sources) {
            if (Collection.class.isAssignableFrom(source.getClass())) {
                sourceValueList(translationClassEndpointList, (Collection<?>) source);
            } else {
                sourceValueOne(translationClassEndpointList, source);
            }
        }
    }

    /**
     * sourceValue赋值
     *
     * @param translationClassEndpointList 解析出的所有注解对象
     * @param source                       原始数据
     */
    public void sourceValueOne(List<TranslationClassEndpoint> translationClassEndpointList, Object source) {

        ConcurrentMap<? extends Class<?>, TranslationClassEndpoint> translationClassEndpointConcurrentMap = translationClassEndpointList.stream().collect(Collectors.toConcurrentMap(TranslationClassEndpoint::getTranslationClass, Function.identity()));
        if (translationClassEndpointConcurrentMap.containsKey(source.getClass())) {

            TranslationClassEndpoint translationClassEndpoint = translationClassEndpointConcurrentMap.get(source.getClass());
            translationClassEndpoint.getTranslationFieldEndpointList().forEach(translationFieldEndpoint -> {
                Field sourceField = translationFieldEndpoint.getSourceField();

                Object o;
                try {

                    // 如果是深度解析的TranslationBean 获取数据后再次获取数据
                    if (Boolean.TRUE.equals(translationFieldEndpoint.getDeep())) {
                        // target field
                        Field targetField = translationFieldEndpoint.getTargetField();
                        targetField.setAccessible(true);
                        Object targetValue = targetField.get(source);
                        List<TranslationClassEndpoint> multipleTranslationClassEndpointList = translationFieldEndpoint.getMultipleTranslationClassEndpointList();
                        sourceValueObjects(multipleTranslationClassEndpointList, targetValue);
                        return;
                    } else {
                        sourceField.setAccessible(true);
                        o = sourceField.get(source);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                translationFieldEndpoint.getSourceValues().add(o);
            });
        }
    }

    /**
     * 获取原始数据中所有的class
     *
     * @param sources
     * @return
     */
    public List<TranslationClassEndpoint> sourceClassEndpointList(Object... sources) {
        List<Class<?>> sourceClassList = Arrays.stream(Arrays.stream(sources).filter(o -> !ObjectUtils.isEmpty(o)).map(o -> {
            if (o instanceof Collection && !ObjectUtils.isEmpty(o)) {
                return ((Collection<?>) o).iterator().next().getClass();
            }
            return o.getClass();
        }).toArray(Class<?>[]::new)).toList();
        // 获取字段中含有的 TranslationBean 注解
        List<TranslationClassEndpoint> translationClassEndpointList = new ArrayList<>();
        for (Class<?> sourceClass : sourceClassList) {
            List<TranslationClassEndpoint> classEndpointList = doSourceClassEndpoint(null, sourceClass);
            translationClassEndpointList.addAll(classEndpointList);
        }
        return translationClassEndpointList;
    }

    /**
     * @param translationClassEndpointList 原始数据中的 translationClassEndpoint
     * @param sourceClass                  需要深度递归的字段class类型
     * @return
     */
    public List<TranslationClassEndpoint> doSourceClassEndpoint(List<TranslationClassEndpoint> translationClassEndpointList, Class<?> sourceClass) {
        if (null == translationClassEndpointList) {
            translationClassEndpointList = new ArrayList<>();
        }
        Field[] fields = sourceClass.getDeclaredFields();
        ConcurrentMap<String, Field> fieldConcurrentMap = Arrays.stream(fields).collect(Collectors.toConcurrentMap(Field::getName, Function.identity()));// 当前字段降维
        TranslationClassEndpoint translationClassEndpoint = new TranslationClassEndpoint();// 转译的class
        translationClassEndpoint.setTranslationClass(sourceClass);
        if (translationClassEndpointList.contains(translationClassEndpoint)) {
            return translationClassEndpointList;
        }
        List<TranslationFieldEndpoint> translationFieldEndpointList = Arrays.stream(fields).filter(this::isTranslationLayer).map(field -> {
            TranslationFieldEndpoint translationFieldEndpoint = new TranslationFieldEndpoint();

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
                translationFieldEndpoint.setTargetClass(sourceClass);
                // 对象字段深度解析 转换
                translationFieldEndpoint.setDeep(true);
                translationFieldEndpoint.setTransferDataName(field.getName());
                translationFieldEndpoint.setTranslationTargetName(field.getName());
                translationFieldEndpoint.setTargetField(field);
                List<TranslationClassEndpoint> translationClassEndpoints = doSourceClassEndpoint(null, fieldType);
                translationFieldEndpoint.getMultipleTranslationClassEndpointList().addAll(translationClassEndpoints);

            } else if (isFieldConvert(field)) {
                translationFieldEndpoint = getTranslationFieldEndpoint(field);
                translationFieldEndpoint.setDeep(false);
                if (ObjectUtils.isEmpty(translationFieldEndpoint)) {
                    return null;
                }
                translationFieldEndpoint.setTargetClass(sourceClass);
                String translationSourceName = translationFieldEndpoint.getTranslationSourceName();// 目标字段
                if (fieldConcurrentMap.containsKey(translationSourceName)) {// 重写toString、hash
                    Field sourceField = fieldConcurrentMap.get(translationSourceName);
                    translationFieldEndpoint.setSourceField(sourceField);
                } else {
                    throw new RuntimeException("配置错误无法从当前class: " + sourceClass.getName() + " 找到原始数据字段: " + translationSourceName);
                }
            }

            return translationFieldEndpoint;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        translationClassEndpoint.setTranslationFieldEndpointList(translationFieldEndpointList);
        translationClassEndpointList.add(translationClassEndpoint);
        return translationClassEndpointList;
    }


    /**
     * 判断字段是否支持深度转换
     *
     * @param field 字段
     * @return 布尔类型
     */
    public boolean isFieldSupportDeep(Field field) {
        return AnnotatedElementUtils.hasAnnotation(field, TranslationBean.class);
    }

    /**
     * 获取class 中字段上的ConvertField 注解
     *
     * @param classList 当前需要转换的class
     * @return 返回字典大项
     */
    private List<TranslationClassEndpoint> getSourceTranslationFieldEndpointList(List<Class<?>> classList) {
        List<TranslationClassEndpoint> translationClassEndpointList = new ArrayList<>();
        if (ObjectUtils.isEmpty(classList)) {
            return translationClassEndpointList;
        }


        for (Class<?> aClass : classList) {
            Field[] fields = aClass.getDeclaredFields();
            ConcurrentMap<String, Field> fieldConcurrentMap = Arrays.stream(fields).collect(Collectors.toConcurrentMap(Field::getName, Function.identity()));// 当前字段降维
            TranslationClassEndpoint translationClassEndpoint = new TranslationClassEndpoint();// 转译的class
            translationClassEndpoint.setTranslationClass(aClass);
            if (translationClassEndpointList.contains(translationClassEndpoint)) {
                continue;
            }
            List<TranslationFieldEndpoint> translationFieldEndpointList = Arrays.stream(fields).filter(this::isFieldConvert).map(field -> {
                TranslationFieldEndpoint translationFieldEndpoint = getTranslationFieldEndpoint(field);
                if (ObjectUtils.isEmpty(translationFieldEndpoint)) {
                    return null;
                }
                translationFieldEndpoint.setTargetClass(aClass);
                String translationSourceName = translationFieldEndpoint.getTranslationSourceName();// 目标字段
                if (fieldConcurrentMap.containsKey(translationSourceName)) {// 重写toString、hash
                    Field sourceField = fieldConcurrentMap.get(translationSourceName);
                    translationFieldEndpoint.setSourceField(sourceField);
                } else {
                    throw new RuntimeException("配置错误无法从当前class: " + aClass.getName() + " 找到原始数据字段: " + translationSourceName);
                }
                if (isFieldConvert(field)) {
                    // 字段注解
                    translationFieldEndpoint.setDeep(false);
                } else if (isFieldSupportDeep(field)) {
                    // 对象字段深度解析 转换
                    translationFieldEndpoint.setDeep(true);
                }
                return translationFieldEndpoint;
            }).filter(Objects::nonNull).collect(Collectors.toList());
            // 对象字段深度解析 转换
            List<TranslationFieldEndpoint> deepFieldEndpointList = Arrays.stream(fields).filter(this::isFieldSupportDeep).map(field -> {
                Class<?> fieldType = field.getType();

                TranslationFieldEndpoint translationFieldEndpoint = new TranslationFieldEndpoint();
                translationFieldEndpoint.setTargetClass(aClass);
                // 对象字段深度解析 转换
                translationFieldEndpoint.setDeep(true);
                translationFieldEndpoint.setTransferDataName(field.getName());
                translationFieldEndpoint.setTranslationTargetName(field.getName());
                translationFieldEndpoint.setTargetField(field);
                return translationFieldEndpoint;
            }).toList();
            translationFieldEndpointList.addAll(deepFieldEndpointList);
            translationClassEndpoint.setTranslationFieldEndpointList(translationFieldEndpointList);
            translationClassEndpointList.add(translationClassEndpoint);
        }
        return translationClassEndpointList;
    }

    /**
     * 判断字段是否支持转换
     *
     * @param field 字段
     * @return
     */
    public boolean isTranslationLayer(Field field) {
        return AnnotatedElementUtils.hasAnnotation(field, TranslationLayer.class);
    }

    /**
     * 判断字段是否支持转换
     *
     * @param field 字段
     * @return
     */
    public boolean isFieldConvert(Field field) {
        return AnnotatedElementUtils.hasAnnotation(field, TranslationField.class);
    }

    /**
     * 获取字段转换 item
     *
     * @param field 字段
     * @return TranslationFieldEndpoint
     */
    public TranslationFieldEndpoint getTranslationFieldEndpoint(Field field) {
        TranslationField translationField = AnnotatedElementUtils.findMergedAnnotation(field, TranslationField.class);
        TranslationFieldEndpoint translationFieldEndpoint = new TranslationFieldEndpoint();
        if (null == translationField) {
            return null;
        }
//        translationFieldEndpoint.setType(translationField.type());
        translationFieldEndpoint.setDefaultValue(translationField.defaultValue());
//        translationFieldEndpoint.setChineseNameProperty(translationField.chineseNameProperty());

        translationFieldEndpoint.setTranslationSourceName(translationField.translationSourceName());
        translationFieldEndpoint.setTransferDataName(ObjectUtils.isEmpty(translationField.transferDataName()) ? field.getName() : translationField.transferDataName());

        translationFieldEndpoint.setTranslationTargetName(ObjectUtils.isEmpty(translationField.translationTargetName()) ? field.getName() : translationField.translationTargetName());
        translationFieldEndpoint.setTranslationTargetType(translationField.translationTargetType());
        translationFieldEndpoint.setTranslationAPI(translationField.translationAPI());
        translationFieldEndpoint.setConvertSplitCharacter(translationField.convertSplitCharacter());
        translationFieldEndpoint.setTargetField(field);
        return translationFieldEndpoint;
    }


    /**
     * @param transferDataEndpointConcurrentMap 转换数据map
     * @param sources                           转换多个对象
     * @return description 模糊转换
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/6/17 3:40 下午
     */
    public void transformationObjects(List<TransferDataFieldEndpoint> transferDataEndpointConcurrentMap, Object... sources) {
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
    protected void transformationCollection(List<TransferDataFieldEndpoint> transferDataEndpointConcurrentMap, Collection<?> collection) {
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
    abstract void doTransformation(Object source, List<TransferDataFieldEndpoint> transferDataEndpointConcurrentMap);


    /**
     * 字段转译
     *
     * @param source 原始数据
     */
    @Override
    public void transformation(Object source) {
        // 解析对象身上的注解

        // 解析转换策略
        List<TransferDataFieldEndpoint> transferDataEndpointConcurrentMap = transformationTemplateMaps(source);

        transformationObjects(transferDataEndpointConcurrentMap, source);
    }


}
