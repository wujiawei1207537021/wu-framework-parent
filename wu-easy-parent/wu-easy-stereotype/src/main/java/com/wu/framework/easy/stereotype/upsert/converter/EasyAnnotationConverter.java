package com.wu.framework.easy.stereotype.upsert.converter;


import com.wu.framework.easy.stereotype.upsert.EasyTable;
import com.wu.framework.easy.stereotype.upsert.EasyTableFile;
import com.wu.framework.easy.stereotype.upsert.EasyUnique;
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
 * description 解析 EasyTable 中的参数
 *
 * @author 吴佳伟
 * @date 2020/7/16 下午2:12
 */
public class EasyAnnotationConverter {

    /**
     * 默认
     */
    private final static String DEFAULT = "DEFAULT";

    private static Logger log = LoggerFactory.getLogger(EasyAnnotationConverter.class);

    /**
     * 获取  EasyTable 上的 kafkaTopicName
     *
     * @param clazz
     * @return
     */
    public static String getCustomTableValue(Class clazz) {
        EasyTable easyTable = AnnotationUtils.getAnnotation(clazz, EasyTable.class);
        if (null != easyTable && !ObjectUtils.isEmpty(easyTable.value())) {
            return easyTable.value();
        }
        return CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
    }


    /**
     * 获取  EasyTable 上的 kafkaTopicName
     *
     * @param clazz
     * @return
     */
    public static String getKafkaTopicName(Class clazz) {
        EasyTable easyTable = AnnotationUtils.getAnnotation(clazz, EasyTable.class);
        if (null != easyTable && !ObjectUtils.isEmpty(easyTable.kafkaTopicName())) {
            return easyTable.kafkaTopicName();
        }
        return CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
    }

    /**
     * 获取  EasyTable 上的 kafkaSchemaName
     *
     * @param clazz
     * @return
     */
    public static String getKafkaSchemaName(Class clazz) {
        return getKafkaSchemaName(clazz, false);
    }

    public static String getKafkaSchemaName(Class clazz, boolean deduplicationSwitch) {
        EasyTable easyTable = AnnotationUtils.getAnnotation(clazz, EasyTable.class);
        if (null != easyTable && !ObjectUtils.isEmpty(easyTable.kafkaSchemaName())) {
            return easyTable.kafkaSchemaName();
        }
        if (deduplicationSwitch) {
            return CamelAndUnderLineConverter.humpToLine2(clazz.getName().replace(".", "_"));
        }
        return CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
    }

    /**
     * 获取 EasyUnique 中的value
     *
     * @param clazz
     * @return
     */
    public static List<String> getCustomUniquePK(Class clazz) {
        List<String> uniqueList = new ArrayList<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
            declaredField.setAccessible(true);
            EasyUnique easyUnique = AnnotationUtils.getAnnotation(declaredField, EasyUnique.class);
            if (null != easyUnique) {
                String pk = declaredField.getName();
                if (!ObjectUtils.isEmpty(easyUnique.value())) {
                    pk = easyUnique.value();
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
        EasyTableFile easyTableFile = AnnotatedElementUtils.getMergedAnnotation(field, EasyTableFile.class);
        // 确认枚举转换
        if (null != easyTableFile && !DefaultIEnum.class.equals(easyTableFile.iEnum()) && easyTableFile.iEnum().isEnum()) {
            Class<? extends IEnum> e = easyTableFile.iEnum();
            if (!ObjectUtils.isEmpty(fieldVal)) {
                if (fieldVal instanceof String) {
                    fieldVal = ((String) fieldVal).trim();
                }
                // 字典转换中获取需要分割的字符

                String[] delimiters = e.getEnumConstants()[0].getConvertContentSeparator();
//                      easyTableFile.convertContentSeparator();
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
                if (!ObjectUtils.isEmpty(easyTableFile.dictionary())) {
                    Map<String, String> dictionaryMap = iEnumList.get(easyTableFile.dictionary());
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
            EasyTableFile easyTableFile = AnnotatedElementUtils.getMergedAnnotation(declaredField, EasyTableFile.class);
            if (null != easyTableFile && !DefaultIEnum.class.equals(easyTableFile.iEnum()) && easyTableFile.iEnum().isEnum()) {
                Class<? extends IEnum> e = easyTableFile.iEnum();
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
        EasyTable easyTable = AnnotationUtils.getAnnotation(clazz, EasyTable.class);
        if (null != easyTable && !ObjectUtils.isEmpty(easyTable.name())) {
            return easyTable.name();
        }
        return CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
    }

    /**
     * 获取 EasyTable comment
     * @param clazz
     * @return
     */
    public static String getComment(Class clazz) {
        EasyTable easyTable = AnnotationUtils.getAnnotation(clazz, EasyTable.class);
        if(null== easyTable){
            return "";
        }
        return easyTable.comment();
    }

    /**
     *  获取kafka code 编码
     * @param clazz
     * @return
     */
    public static String getKafkaCode(Class clazz) {
        EasyTable easyTable = AnnotationUtils.getAnnotation(clazz, EasyTable.class);
        if (null != easyTable && !ObjectUtils.isEmpty(easyTable.kafkaCode())) {
            return easyTable.kafkaCode();
        }
        return CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
    }
}
