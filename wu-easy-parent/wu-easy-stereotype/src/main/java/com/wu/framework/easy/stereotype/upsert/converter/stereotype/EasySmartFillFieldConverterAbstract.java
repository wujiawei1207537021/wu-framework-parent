package com.wu.framework.easy.stereotype.upsert.converter.stereotype;

import com.wu.framework.easy.stereotype.upsert.entity.EasyHashMap;
import com.wu.framework.easy.stereotype.upsert.entity.stereotye.EasyTableAnnotation;
import com.wu.framework.easy.stereotype.upsert.entity.stereotye.LocalStorageClassAnnotation;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 智能填充数据 处理
 * @date : 2021/3/3 9:25 下午
 */
public abstract class EasySmartFillFieldConverterAbstract extends EasySmartConverterAbstract implements IEasySmartConverter {
    /**
     * @param source
     * @param target
     * @return
     * @describe 智能填充数据
     * @author Jia wei Wu
     * @date 2021/3/3 9:23 下午
     **/
    @Override
    public void smartFillField(Object source, Object target) {
        EasyTableAnnotation targetEasyTableAnnotation = LocalStorageClassAnnotation.getEasyTableAnnotation(target.getClass(), true);
        AtomicBoolean smartFillField = new AtomicBoolean(targetEasyTableAnnotation.isSmartFillField());
        Field[] declaredFields = target.getClass().getDeclaredFields();

        if (smartFillField.get()) {
            smartFillField.set(false);
            Class<?> sourceClass = source.getClass();
            Set<CreateField> fieldSet = Arrays.stream(declaredFields).map(field -> new CreateField().setFieldName(field.getName()).setFieldType(field.getType())).collect(Collectors.toSet());

            Set<CreateField> sourceFieldSet;// 数据源属性name
            if (EasyHashMap.class.isAssignableFrom(sourceClass)) {
                sourceFieldSet = ((EasyHashMap<String, Object>) source).entrySet().stream().map(stringObjectEntry -> {
                    Class fileType = ObjectUtils.isEmpty(stringObjectEntry.getValue()) ? Object.class : stringObjectEntry.getValue().getClass();
                    return new CreateField().setFieldName(stringObjectEntry.getKey()).setFieldType(fileType);
                }).collect(Collectors.toSet());
                if (ObjectUtils.isEmpty(sourceFieldSet)) return;
            } else {
                Field[] sourceClassDeclaredFields = sourceClass.getDeclaredFields();
                if (ObjectUtils.isEmpty(sourceClassDeclaredFields)) return;
                sourceFieldSet = Arrays.stream(sourceClassDeclaredFields).map(field -> new CreateField().setFieldName(field.getName()).setFieldType(field.getType())).collect(Collectors.toSet());
            }
            List<CreateField> createFieldList = new ArrayList<>(fieldSet);
            sourceFieldSet.forEach(createField -> {
                if (!fieldSet.contains(createField)) {
                    createFieldList.add(createField);
                    smartFillField.set(true);
                }
            });
            if (smartFillField.get()) {
                targetClassWriteAttributeFieldList(createFieldList, target.getClass());
            }
        } else {

        }
    }

    /**
     * @param createFieldList 创建的字段
     * @param targetClass     目标类
     * @return
     * @describe 目标类写入属性字段
     * @author Jia wei Wu
     * @date 2021/3/3 10:04 下午
     **/
    public abstract void targetClassWriteAttributeFieldList(List<CreateField> createFieldList, Class targetClass);


    @Accessors(chain = true)
    @Data
    public static class CreateField {
        private Class fieldType;
        private String fieldName;
    }
}
