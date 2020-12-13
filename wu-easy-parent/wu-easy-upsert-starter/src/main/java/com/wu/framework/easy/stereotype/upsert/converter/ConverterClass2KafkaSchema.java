package com.wu.framework.easy.stereotype.upsert.converter;


import com.wu.framework.easy.stereotype.upsert.EasyTableField;
import com.wu.framework.easy.stereotype.upsert.entity.kafka.TargetJsonSchema;
import org.apache.kafka.common.protocol.types.Type;
import org.springframework.core.annotation.AnnotatedElementUtils;
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
        targetJsonSchema.setName(EasyAnnotationConverter.getKafkaSchemaName(clazz, forcedDuplicateNameSwitch));
        List<TargetJsonSchema.Field> fieldList = new ArrayList<>();
        targetJsonSchema.setFields(fieldList);
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            if (UpsertJsonMessage.ignoredFields.contains(field.getName())) {
                continue;
            }
            TargetJsonSchema.Field t = new TargetJsonSchema.Field();
            EasyTableField easyTableField = AnnotatedElementUtils.findMergedAnnotation(field, EasyTableField.class);
            String fieldName = "";
            String name = "";
            String type = "";
            boolean optional = true;
            fieldName = CamelAndUnderLineConverter.humpToLine2(field.getName());
            type = EasyTableField.JavaSchemaDataType.getAlias(field.getType());
            if (!ObjectUtils.isEmpty(easyTableField)) {
                if (!easyTableField.exist()) {
                    continue;
                }
                String fieldNameTemp = easyTableField.name();
                if (!ObjectUtils.isEmpty(fieldNameTemp)) {
                    fieldName = fieldNameTemp;
                }
                // 数据库字段类型
                type = databaseFieldConversionSchemaType(easyTableField.type()).toLowerCase();
                optional = easyTableField.optional();
            }
            t.setField(fieldName);
            t.setType(type);
            t.setOptional(optional);
            t.init();
            fieldList.add(t);
        }
        return targetJsonSchema;
    }

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
