package com.wu.framework.easy.stereotype.upsert.entity;

import com.wu.framework.easy.stereotype.upsert.EasySmartField;
import com.wu.framework.easy.stereotype.upsert.converter.CamelAndUnderLineConverter;
import com.wu.framework.easy.stereotype.upsert.entity.stereotye.EasyTableAnnotation;
import com.wu.framework.easy.stereotype.upsert.entity.stereotye.LocalStorageClassAnnotation;
import com.wu.framework.easy.stereotype.upsert.enums.NormalUsedString;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author : Jiawei Wu
 * @version 1.0
 * @describe : 使用此对象会自动创建数据库表
 * @date : 2020/12/31 6:42 下午
 */
public class EasyHashMap<K, V> extends HashMap<K, V> implements Map<K, V>, IBeanUpsert {

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
            Class fieldClazz = null == value ? String.class : value.getClass();
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

    /**
     * @return String  class 字符串
     * @describe 生成class
     * @author Jia wei Wu
     * @date 2021/1/31 8:12 下午
     **/
    public StringBuffer generateClass() {
        StringBuffer clazzStringBuffer = new StringBuffer(LocalStorageClassAnnotation.DOMAIN_CLASS_TEMP);
        EasyHashMap<Class, String> classStringEasyHashMap = new EasyHashMap<>();
        forEach((k, v) -> {
            Class vClass = v == null ? String.class : v.getClass();
            classStringEasyHashMap.put(vClass, vClass.getSimpleName());
        });
        for (Class aClass : classStringEasyHashMap.keySet()) {
            clazzStringBuffer.append("import").append(NormalUsedString.SPACE).append(aClass.getName()).append(NormalUsedString.SEMICOLON).append(NormalUsedString.NEWLINE);
        }
        clazzStringBuffer.append(NormalUsedString.NEWLINE);
        clazzStringBuffer.append(String.format("public class %s {", uniqueLabel)).append(NormalUsedString.NEWLINE);
        forEach((k, v) -> clazzStringBuffer.append(NormalUsedString.PRIVATE).append(NormalUsedString.SPACE).
                // 字段类型
                        append(v == null ? NormalUsedString.STRING : v.getClass().getSimpleName()).append(NormalUsedString.SPACE).
                // 字段名称
                        append(CamelAndUnderLineConverter.lineToHump(k.toString())).
                        append(NormalUsedString.SEMICOLON).append(NormalUsedString.NEWLINE));
        clazzStringBuffer.append(NormalUsedString.RIGHT_BRACE);
        return clazzStringBuffer;
    }

    /**
     * @return
     * @describe 对象处理之前
     * @author Jia wei Wu
     * @date 2021/3/2 6:24 下午
     **/
    @Override
    public Object beforeObjectProcess() throws Exception {
        return null;
    }

    public byte[] getBytes(String key) {
        Object value = get(key);

        if (value == null) {
            return null;
        }

        return castToBytes(value);
    }

    public static byte[] castToBytes(Object value) {
        if (value instanceof byte[]) {
            return (byte[]) value;
        }
        if (value instanceof String) {
            return ((String) value).getBytes(StandardCharsets.UTF_8);
        }
        throw new RuntimeException("can not cast to byte[], value : " + value);
    }
}