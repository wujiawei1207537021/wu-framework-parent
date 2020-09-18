package com.wu.framework.easy.stereotype.upsert.upsert.converter;


import com.wu.framework.easy.stereotype.upsert.CustomTable;
import com.wu.framework.easy.stereotype.upsert.CustomTableFile;
import com.wu.framework.easy.stereotype.upsert.CustomUnique;
import com.wu.framework.easy.stereotype.upsert.ienum.DefaultIEnum;
import com.wu.framework.easy.stereotype.upsert.ienum.IEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * description 解析 CustomTable 中的参数
 *
 * @author 吴佳伟
 * @date 2020/7/16 下午2:12
 */
public class CustomAnnotationConverter {

    /**
     * 默认
     */
    private final static String DEFAULT = "DEFAULT";

    private static Logger log = LoggerFactory.getLogger(CustomAnnotationConverter.class);

    /**
     * 获取  CustomTable 上的 kafkaTopicName
     *
     * @param clazz
     * @return
     */
    public static String getCustomTableValue(Class clazz) {
        CustomTable customTable = AnnotationUtils.getAnnotation(clazz, CustomTable.class);
        if (null != customTable && !ObjectUtils.isEmpty(customTable.value())) {
            return customTable.value();
        }
        return CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
    }


    /**
     * 获取  CustomTable 上的 kafkaTopicName
     *
     * @param clazz
     * @return
     */
    public static String getKafkaTopicName(Class clazz) {
        CustomTable customTable = AnnotationUtils.getAnnotation(clazz, CustomTable.class);
        if (null != customTable && !ObjectUtils.isEmpty(customTable.kafkaTopicName())) {
            return customTable.kafkaTopicName();
        }
        return CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
    }

    /**
     * 获取  CustomTable 上的 kafkaSchemaName
     *
     * @param clazz
     * @return
     */
    public static String getKafkaSchemaName(Class clazz) {
        return getKafkaSchemaName(clazz, false);
    }

    public static String getKafkaSchemaName(Class clazz, boolean deduplicationSwitch) {
        CustomTable customTable = AnnotationUtils.getAnnotation(clazz, CustomTable.class);
        if (null != customTable && !ObjectUtils.isEmpty(customTable.kafkaSchemaName())) {
            return customTable.kafkaSchemaName();
        }
        if (deduplicationSwitch) {
            return CamelAndUnderLineConverter.humpToLine2(clazz.getName().replace(".", "_"));
        }
        return CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
    }

    /**
     * 获取 CustomUnique 中的value
     *
     * @param clazz
     * @return
     */
    public static List<String> getCustomUniquePK(Class clazz) {
        List<String> uniqueList = new ArrayList<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
            declaredField.setAccessible(true);
            CustomUnique customUnique = AnnotationUtils.getAnnotation(declaredField, CustomUnique.class);
            if (null != customUnique) {
                String pk = declaredField.getName();
                if (!ObjectUtils.isEmpty(customUnique.value())) {
                    pk = customUnique.value();
                }
                uniqueList.add(pk);
            } else {
                continue;
            }
        }
        return uniqueList;
    }

    /**
     * description 注解字典转换
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2020/8/4 下午4:39
     */
    public static Object annotationDictionaryConversion(Field field, Object fieldVal, Map<String, Map<String, String>> iEnumList) {
        CustomTableFile customTableFile = AnnotatedElementUtils.getMergedAnnotation(field, CustomTableFile.class);
        // 确认枚举转换
        if (null != customTableFile && !DefaultIEnum.class.equals(customTableFile.iEnum()) && customTableFile.iEnum().isEnum()) {
            Class<? extends IEnum> e = customTableFile.iEnum();
            if (!ObjectUtils.isEmpty(fieldVal)) {
                if (fieldVal instanceof String) {
                    fieldVal = ((String) fieldVal).trim();
                }
                // 字典转换中获取需要分割的字符

                String[] delimiters = e.getEnumConstants()[0].getConvertContentSeparator();
//                      customTableFile.convertContentSeparator();
                // 分割后的内容
                List<String> splitContent = new ArrayList<>();
                if (!ObjectUtils.isEmpty(delimiters)) {
                    splitContent = Arrays.asList(fieldVal.toString().split(String.join("|", delimiters)));
                } else {
                    splitContent = (List<String>) fieldVal;
                }
                // 转换后的字典
                List<String> res = new ArrayList<>();
                // 字典api转换
                if (!ObjectUtils.isEmpty(customTableFile.dictionary())) {
                    Map<String, String> dictionaryMap = iEnumList.get(customTableFile.dictionary());
                    if (!ObjectUtils.isEmpty(dictionaryMap)) {
                        for (String val : splitContent) {
                            if (dictionaryMap.containsKey(val)) {
                                res.add(dictionaryMap.get(val));
                                continue;
                            }
                        }
                    }
                }
                // 枚举转换
                Map<String, String> fieldMap = iEnumList.get(field.getName());
                loop:
                for (String val : splitContent) {
                    if (fieldMap.containsKey(val)) {
                        res.add(fieldMap.get(val));
                        continue loop;
                    }
                    log.warn(" fail to find the val of:【{}】 in Enums:{} ", val, e);
                }
                if (ObjectUtils.isEmpty(res)) {
                    return e.getEnumConstants()[0].getDefaultCode();
                }
                return String.join(",", res);
            } else {
                return e.getEnumConstants()[0].getDefaultCode();
            }


        }
        // 不转换直接跳出
        return fieldVal;
    }

    /**
     * 收集类字典
     *
     * @param clazz
     * @return
     */
    public static Map<String, Map<String, String>> collectionDictionary(Class clazz) {
        Map<String, Map<String, String>> enumMap = new HashMap<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
            CustomTableFile customTableFile = AnnotatedElementUtils.getMergedAnnotation(declaredField, CustomTableFile.class);
            if (null != customTableFile && !DefaultIEnum.class.equals(customTableFile.iEnum()) && customTableFile.iEnum().isEnum()) {
                Class<? extends IEnum> e = customTableFile.iEnum();
                Map<String, String> fieldMap = Arrays.stream(e.getEnumConstants()).collect(Collectors.toMap(IEnum::getItem, IEnum::getCode));
                enumMap.put(declaredField.getName(), fieldMap);
            }
        }
        return enumMap;
    }

    /**
     * 获取表名称
     *
     * @param clazz
     * @return
     */
    public static String getTableName(Class clazz) {
        CustomTable customTable = AnnotationUtils.getAnnotation(clazz, CustomTable.class);
        if (null != customTable && !ObjectUtils.isEmpty(customTable.name())) {
            return customTable.name();
        }
        return CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
    }

    /**
     * 获取 CustomTable comment
     * @param clazz
     * @return
     */
    public static String getComment(Class clazz) {
        CustomTable customTable = AnnotationUtils.getAnnotation(clazz, CustomTable.class);
        if(null==customTable){
            return "";
        }
        return customTable.comment();
    }

    /**
     *  获取kafka code 编码
     * @param clazz
     * @return
     */
    public static String getKafkaCode(Class clazz) {
        CustomTable customTable = AnnotationUtils.getAnnotation(clazz, CustomTable.class);
        if (null != customTable && !ObjectUtils.isEmpty(customTable.kafkaCode())) {
            return customTable.kafkaCode();
        }
        return CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
    }
}
