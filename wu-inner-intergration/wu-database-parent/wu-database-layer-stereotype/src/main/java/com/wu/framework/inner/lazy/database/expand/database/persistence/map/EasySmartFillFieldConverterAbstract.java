package com.wu.framework.inner.lazy.database.expand.database.persistence.map;

import com.wu.framework.inner.lazy.database.expand.database.persistence.analyze.SQLAnalyze;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyTableAnnotation;
import com.wu.framework.inner.layer.data.NormalUsedString;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
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
 * describe: 智能填充数据 处理
 * @date : 2021/3/3 9:25 下午
 */
public abstract class EasySmartFillFieldConverterAbstract extends EasySmartConverterAbstract implements IEasySmartConverter, SQLAnalyze {

    /**
     * @param source 数据源
     * @param target 目标数据 或者目标类存放地址已经存在的class 同级
     * @return
     * describe 智能填充数据
     * @author Jia wei Wu
     * @date 2021/3/3 9:23 下午
     **/
    public void smartFillField(Object source, Object target) {
        Class targetClass;
        AtomicBoolean smartFillField;
        Field[] declaredFields;
        if (target.getClass().equals(Class.class)) {
            targetClass = (Class) target;
            smartFillField = new AtomicBoolean(true);
            declaredFields = new Field[]{};
        } else {
            targetClass = target.getClass();
            LazyTableAnnotation targetLazyTableAnnotation = classLazyTableAnalyze(targetClass);
            smartFillField = new AtomicBoolean(targetLazyTableAnnotation.isSmartFillField());
            declaredFields = target.getClass().getDeclaredFields();
        }


        if (smartFillField.get()) {
            smartFillField.set(false);
            Class<?> sourceClass = source.getClass();
            Set<CreateField> fieldSet = Arrays.stream(declaredFields).map(field -> new CreateField().setFieldName(field.getName()).setFieldTypeName(field.getType().getSimpleName())).collect(Collectors.toSet());

            Set<CreateField> sourceFieldSet;// 数据源属性name
            if (EasyHashMap.class.isAssignableFrom(sourceClass)) {
                sourceFieldSet = ((EasyHashMap<String, Object>) source).entrySet().stream().map(stringObjectEntry -> {
                    Class fileType = ObjectUtils.isEmpty(stringObjectEntry.getValue()) ? Object.class : stringObjectEntry.getValue().getClass();
                    return new CreateField().setFieldName(stringObjectEntry.getKey()).setFieldTypeName(fileType.getSimpleName());
                }).collect(Collectors.toSet());
                if (ObjectUtils.isEmpty(sourceFieldSet)) return;
            } else {
                Field[] sourceClassDeclaredFields = sourceClass.getDeclaredFields();
                if (ObjectUtils.isEmpty(sourceClassDeclaredFields)) return;
                sourceFieldSet = Arrays.stream(sourceClassDeclaredFields).map(field -> new CreateField().setFieldName(field.getName()).setFieldTypeName(field.getType().getSimpleName())).collect(Collectors.toSet());
            }
            List<CreateField> createFieldList = new ArrayList<>(fieldSet);
            sourceFieldSet.forEach(createField -> {
                if (!fieldSet.contains(createField)) {
                    createFieldList.add(createField);
                    smartFillField.set(true);
                }
            });
            if (smartFillField.get()) {
                CreateInfo createInfo = new CreateInfo().setCreateFieldList(createFieldList);
                createInfo.setClassName(targetClass.getSimpleName());
                targetClassWriteAttributeFieldList(createInfo);
            }
        } else {

        }
    }


    /**
     * @param createInfo 创建信息
     * @return
     * describe 目标类写入属性字段
     * @author Jia wei Wu
     * @date 2021/3/3 10:04 下午
     **/
    protected abstract String targetClassWriteAttributeFieldList(CreateInfo createInfo);

    /**
     * @param source 数据源
     * @param target 目标数据
     * @return
     * describe 转换
     * @author Jia wei Wu
     * @date 2021/3/3 11:23 下午
     **/
    @Override
    public Object converter(Object source, Object target) {
        smartFillField(source, target);
        return target;
    }

    /**
     * @author Jia wei Wu
     * describe 创建字段信息
     * @date 2021/3/5 6:42 下午
     **/
    @Accessors(chain = true)
    @Data
    public static class CreateField {

        private String fieldTypeName;
        private String fieldName;
    }

    @Accessors(chain = true)
    @Data
    public static class CreateInfo {

        private List<CreateField> createFieldList = new ArrayList<>();
        private Package aPackage = EasySmartFillFieldConverterAbstract.class.getPackage();
        private Boolean override = false;
        private String className;
        private List<Annotation> classAnnotationList = new ArrayList<>();
        private String fileSuffix = NormalUsedString.DOT_CLASS;

        private List<CreateInfo> innerClassList = new ArrayList<>();// 内部class


    }
}
