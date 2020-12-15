package com.wu.framework.easy.stereotype.upsert.converter;


import com.wu.framework.easy.stereotype.upsert.EasySmart;
import com.wu.framework.easy.stereotype.upsert.EasySmartField;
import com.wu.framework.easy.stereotype.upsert.EasyUnique;
import com.wu.framework.easy.stereotype.upsert.SmartMark;
import com.wu.framework.easy.stereotype.upsert.ienum.DefaultIEnum;
import com.wu.framework.easy.stereotype.upsert.ienum.IEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * description 解析 EasySmart 中的参数
 *
 * @author Jia wei Wu
 * @date 2020/7/16 下午2:12
 */
public class EasyAnnotationConverter {

    /**
     * 默认
     */
    private final static String DEFAULT = "DEFAULT";

    private static Logger log = LoggerFactory.getLogger(EasyAnnotationConverter.class);

    /**
     * 获取  EasySmart 上的 kafkaTopicName
     *
     * @param clazz
     * @return
     */
    public static String getCustomTableValue(Class clazz) {
        EasySmart easySmart = AnnotationUtils.getAnnotation(clazz, EasySmart.class);
        if (null != easySmart && !ObjectUtils.isEmpty(easySmart.value())) {
            return easySmart.value();
        }
        return CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
    }


    /**
     * 获取  EasySmart 上的 kafkaTopicName
     *
     * @param clazz
     * @return
     */
    public static String getKafkaTopicName(Class clazz) {
        EasySmart easySmart = AnnotationUtils.getAnnotation(clazz, EasySmart.class);
        if (null != easySmart && !ObjectUtils.isEmpty(easySmart.kafkaTopicName())) {
            return easySmart.kafkaTopicName();
        }
        return CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
    }

    /**
     * 获取  EasySmart 上的 kafkaSchemaName
     *
     * @param clazz
     * @return
     */
    public static String getKafkaSchemaName(Class clazz) {
        return getKafkaSchemaName(clazz, false);
    }

    /**
     * @param clazz               类
     * @param deduplicationSwitch 是否强制去重
     * @return
     * @author Jia wei Wu
     * @date 2020/11/21 下午10:48
     **/
    public static String getKafkaSchemaName(Class clazz, boolean deduplicationSwitch) {
        EasySmart easySmart = AnnotationUtils.getAnnotation(clazz, EasySmart.class);
        if (null != easySmart && !ObjectUtils.isEmpty(easySmart.kafkaSchemaName())) {
            return easySmart.kafkaSchemaName();
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
     * @author Jia wei Wu
     * @date 2020/8/4 下午4:39
     */
    public static Object annotationConvertConversion(Field field, Object fieldVal, Map<String, Map<String, String>> iEnumList) {
        EasySmartField easySmartField = AnnotatedElementUtils.getMergedAnnotation(field, EasySmartField.class);
        // 确认枚举转换
        if (null != easySmartField && !DefaultIEnum.class.equals(easySmartField.iEnum()) && easySmartField.iEnum().isEnum()) {
            Class<? extends IEnum> e = easySmartField.iEnum();
            if (!ObjectUtils.isEmpty(fieldVal)) {
                if (fieldVal instanceof String) {
                    fieldVal = ((String) fieldVal).trim();
                }
                // 字典转换中获取需要分割的字符

                String[] delimiters = e.getEnumConstants()[0].getConvertContentSeparator();
//                      easySmartField.convertContentSeparator();
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
                if (!ObjectUtils.isEmpty(easySmartField.convert())) {
                    Map<String, String> ConvertMap = iEnumList.get(easySmartField.convert());
                    if (!ObjectUtils.isEmpty(ConvertMap)) {
                        for (String val : splitContent) {
                            if (ConvertMap.containsKey(val)) {
                                res.add(ConvertMap.get(val));
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
    public static Map<String, Map<String, String>> collectionConvert(Class clazz) {
        Map<String, Map<String, String>> enumMap = new HashMap<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
            EasySmartField easySmartField = AnnotatedElementUtils.getMergedAnnotation(declaredField, EasySmartField.class);
            if (null != easySmartField && !DefaultIEnum.class.equals(easySmartField.iEnum()) && easySmartField.iEnum().isEnum()) {
                Class<? extends IEnum> e = easySmartField.iEnum();
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
        EasySmart easySmart = AnnotationUtils.getAnnotation(clazz, EasySmart.class);
        if (null != easySmart && !ObjectUtils.isEmpty(easySmart.tableName())) {
            return easySmart.tableName();
        }
        return CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
    }

    /**
     * 获取 EasySmart comment
     *
     * @param clazz
     * @return
     */
    public static String getComment(Class clazz) {
        EasySmart easySmart = AnnotationUtils.getAnnotation(clazz, EasySmart.class);
        if (null == easySmart) {
            return "";
        }
        return easySmart.comment();
    }

    /**
     * 获取kafka code 编码
     *
     * @param clazz
     * @return
     */
    public static String getKafkaCode(Class clazz) {
        EasySmart easySmart = AnnotationUtils.getAnnotation(clazz, EasySmart.class);
        if (null != easySmart && !ObjectUtils.isEmpty(easySmart.kafkaCode())) {
            return easySmart.kafkaCode();
        }
        return CamelAndUnderLineConverter.humpToLine2(clazz.getSimpleName());
    }

    /**
     * description 提取数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/12/14 下午12:26
     */
    public static List<List> extractData(Class<? extends Annotation> classAnnotation, List<List> extractList, Object... objects) {
        if (extractList == null) extractList = new ArrayList();
        for (Object object : objects) {
            Class<?> aClass = object.getClass();
            if (null == AnnotationUtils.getAnnotation(aClass, classAnnotation)) {
                extractList.add(Collections.singletonList(object));
                continue;
            }
            for (Field field : aClass.getDeclaredFields()) {
                field.setAccessible(true);
                SmartMark smartMark = AnnotationUtils.getAnnotation(field, SmartMark.class);
                if (null == smartMark) continue;
                Object o = null;
                try {
                    o = field.get(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if(null==o)continue;;
                if (smartMark.drillDown()) {
                     List<List> lists = extractData(classAnnotation, extractList, o);
                    for (List list : lists) {
                        extractList.addAll(list);
                    }
                }
                extractList.add(Collections.singletonList(o));
            }
        }
        return extractList;
    }
}
