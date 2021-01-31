package com.wu.framework.easy.stereotype.upsert.entity;

import com.wu.framework.easy.stereotype.upsert.EasySmartField;
import com.wu.framework.easy.stereotype.upsert.converter.CamelAndUnderLineConverter;
import com.wu.framework.easy.stereotype.upsert.entity.stereotye.EasyTableAnnotation;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author : Jiawei Wu
 * @version 1.0
 * @describe : 使用此对象会自动创建数据库表
 * @date : 2020/12/31 6:42 下午
 */
public class EasyHashMap<K, V> extends HashMap<K, V> implements Map<K, V> {

    /**
     * 唯一性标示
     */
    private String uniqueLabel = UUID.randomUUID().toString();


    public EasyHashMap() {

    }

    public EasyHashMap(String uniqueLabel) {
        this.uniqueLabel = uniqueLabel;
    }


    /**
     * @return
     * @params 转换成 EasyTableAnnotation
     * @author Jiawei Wu
     * @date 2020/12/31 6:52 下午
     **/
    public EasyTableAnnotation toEasyTableAnnotation() {
        return toEasyTableAnnotation(false);
    }

    /**
     * description
     *
     * @param isCapitalized 是否大写
     * @return
     * @exception/throws
     * @author Jiawei Wu
     * @date 2021/1/20 下午5:28
     */
    public EasyTableAnnotation toEasyTableAnnotation(boolean isCapitalized) {
        EasyTableAnnotation easyTableAnnotation = new EasyTableAnnotation();
        easyTableAnnotation.setClassName(this.getClass().getName());
        easyTableAnnotation.setClazz(this.getClass());
        easyTableAnnotation.setTableName(CamelAndUnderLineConverter.humpToLine2(uniqueLabel));
        easyTableAnnotation.setComment(String.format("创建时间%s", LocalDateTime.now()));
        easyTableAnnotation.setKafkaCode(uniqueLabel);
        easyTableAnnotation.setKafkaTopicName(uniqueLabel);
        easyTableAnnotation.setKafkaSchemaName(UUID.randomUUID() + uniqueLabel);
        List<ConvertedField> convertedFieldList = new ArrayList<>();
        forEach((key, value) -> {
            String fieldName;
            if (isCapitalized) {
                fieldName = key.toString().toUpperCase();
            } else {
                fieldName = key.toString().toLowerCase();
            }
            ConvertedField convertedField = new ConvertedField();
            convertedField.setFieldName(fieldName);
            Class fieldClazz=null==value?String.class:value.getClass();
            convertedField.setType(EasySmartField.FileType.getTypeByClass(fieldClazz));
            convertedField.setComment(String.format("字段创建时间%s", LocalDateTime.now()));
            convertedField.setClazz(fieldClazz);
            convertedField.setConvertedFieldName(CamelAndUnderLineConverter.humpToLine2(fieldName));
            convertedField.setFieldIndexType(EasySmartField.TableFileIndexType.FILE_TYPE);
            convertedFieldList.add(convertedField);
        });
        easyTableAnnotation.setConvertedFieldList(convertedFieldList);
        return easyTableAnnotation;
    }

    /**
     * @return
     * @params 获取范型
     * @author Jiawei Wu
     * @date 2020/12/31 7:25 下午
     **/
    public Type[] getClassParadigm() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return parameterizedType.getActualTypeArguments();
    }

    public String getUniqueLabel() {
        return uniqueLabel;
    }

    public void setUniqueLabel(String uniqueLabel) {
        this.uniqueLabel = uniqueLabel;
    }
}