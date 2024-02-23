package com.wu.framework.easy.upsert.sink.converter;


import com.wu.framework.easy.upsert.autoconfigure.EasySmart;
import com.wu.framework.easy.upsert.autoconfigure.EasySmartField;
import com.wu.framework.easy.upsert.sink.kafka.TargetJsonSchema;
import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;
import org.apache.kafka.common.protocol.types.Type;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * description KafkaSchema 转换操作
 *
 * @author Jia wei Wu
 * @date 2020/6/29 上午10:48
 */
public class ConverterClass2KafkaSchema {

    /**
     * description 转换class 为TargetJsonSchema
     *
     * @param clazz
     * @return TargetJsonSchema
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/6/29 上午10:54
     */
    public static TargetJsonSchema converterClass2TargetJsonSchema(Class clazz, boolean forcedDuplicateNameSwitch) {
        TargetJsonSchema targetJsonSchema = new TargetJsonSchema();
        targetJsonSchema.setName(getKafkaSchemaName(clazz, forcedDuplicateNameSwitch));
        List<TargetJsonSchema.Field> fieldList = new ArrayList<>();
        targetJsonSchema.setFields(fieldList);
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            if (LazyDatabaseJsonMessage.ignoredFields.contains(field.getName())) {
                continue;
            }
            TargetJsonSchema.Field t = new TargetJsonSchema.Field();
            EasySmartField easySmartField = AnnotatedElementUtils.findMergedAnnotation(field, EasySmartField.class);
            String fieldName = "";
            String name = "";
            String type = "";
            boolean optional = true;
            fieldName = CamelAndUnderLineConverter.humpToLine2(field.getName());
            type = EasySmartField.JavaSchemaDataType.getAlias(field.getType());
            if (!ObjectUtils.isEmpty(easySmartField)) {
                if (!easySmartField.exist()) {
                    continue;
                }
                String fieldNameTemp = easySmartField.name();
                if (!ObjectUtils.isEmpty(fieldNameTemp)) {
                    fieldName = fieldNameTemp;
                }
                // 数据库字段类型
                type = databaseFieldConversionSchemaType(easySmartField.columnType()).toLowerCase();
                optional = easySmartField.optional();
            }
            t.setField(fieldName);
            t.setType(type);
            t.setOptional(optional);
            t.init();
            fieldList.add(t);
        }
        return targetJsonSchema;
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
     * @param
     * @return describe 数据库字段转换架构类型
     * @author Jia wei Wu
     * @date 2021/4/11 10:34 上午
     **/
    private static String databaseFieldConversionSchemaType(String type) {
        String typeLowerCase = type.trim().toLowerCase();
        if (typeLowerCase.startsWith("varchar")) {
            return Type.STRING.toString();
        }
        if (typeLowerCase.startsWith("number") || typeLowerCase.startsWith("int")) {
            return Type.INT32.toString();
        }
        return type;

    }
}
