package com.wu.framework.inner.lazy.persistence.map;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.IBeanUpsert;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.data.ProcessException;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.FieldLazyTableFieldEndpoint;
import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;
import com.wu.framework.inner.lazy.persistence.domain.JavaVerification;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author : Jiawei Wu
 * @version 1.0
 * describe : 使用此对象会自动创建数据库表
 * @date : 2020/12/31 6:42 下午
 * @see com.wu.framework.inner.layer.data.schema.SchemaMap
 */
public class EasyHashMap<K, V> extends HashMap<K, V> implements Map<K, V>, IBeanUpsert {

    public static final Pattern pattern = Pattern.compile("[0-9]*");
    private final List<Object> uniqueFieldList = new ArrayList<>();
    /**
     * value  对应的集类型
     */
    private final HashMap<K, Class> ValueType = new HashMap();
    /**
     * 唯一性标示
     */
    private String uniqueLabel = UUID.randomUUID().toString();
    // 修改 唯一性标示
    private boolean modifyUniqueLabel = false;
    // key 是否调整 (下划线转驼峰、驼峰转下划线)  true 调整 false 不调整
    private boolean keyAdjust = true;

    public EasyHashMap() {

    }

    public EasyHashMap(String uniqueLabel) {
        this.uniqueLabel = uniqueLabel;
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

    /**
     * @param
     * @return describe 将Map 转换成 Java
     * @author Jia wei Wu
     * @date 2021/3/5 7:48 下午
     **/
    public static EasySmartFillFieldConverterAbstract.CreateInfo mapConverterJava(Map<Object, Object> map) {
        EasySmartFillFieldConverterAbstract.CreateInfo createInfo = new EasySmartFillFieldConverterAbstract.CreateInfo();
        map.forEach((k, v) -> {
            // v 为属性
            EasySmartFillFieldConverterAbstract.CreateField createField = new EasySmartFillFieldConverterAbstract.CreateField();
            if (k instanceof String) {
                String fieldName = (String) k;
                // 校验k是否为Java编码规则
                JavaVerification javaVerification = verificationKey(fieldName);
                if (!javaVerification.isJava()) {
                    fieldName = javaVerification.getNewName();
                }
                createField.setFieldName(fieldName);

                if (fieldName.contains(NormalUsedString.RIGHT_TITLE_NUMBER) || fieldName.contains(NormalUsedString.LEFT_TITLE_NUMBER)) {
                    fieldName = fieldName.replace(NormalUsedString.RIGHT_TITLE_NUMBER, NormalUsedString.UNDERSCORE);
                    fieldName = fieldName.replace(NormalUsedString.LEFT_TITLE_NUMBER, NormalUsedString.UNDERSCORE);
                }
                if (fieldName.contains(NormalUsedString.RIGHT_TITLE_NUMBER)) {
                    System.out.println(fieldName);
                }
                String className = CamelAndUnderLineConverter.lineToHumpField(NormalUsedString.UNDERSCORE + fieldName);
                if (v instanceof Map) {
                    EasySmartFillFieldConverterAbstract.CreateInfo innerClass = mapConverterJava((Map<Object, Object>) v);
                    innerClass.setClassName(className);
                    createInfo.getInnerClassList().add(innerClass);
                    createField.setFieldTypeName(innerClass.getClassName());
                } else if (v instanceof List) {
                    if (ObjectUtils.isEmpty(v)) {
                        createField.setFieldTypeName(List.class.getSimpleName());
                    } else {
                        Object o = ((List<?>) v).get(0);
                        if (o instanceof Map) {
                            EasySmartFillFieldConverterAbstract.CreateInfo innerClass = mapConverterJava((Map<Object, Object>) o);
                            innerClass.setClassName(className);
                            createInfo.getInnerClassList().add(innerClass);
                            createField.setFieldTypeName(innerClass.getClassName());
                        } else {
                            createField.setFieldTypeName(v.getClass().getSimpleName());
                        }
                    }
                } else {
                    if (ObjectUtils.isEmpty(v)) {
                        createField.setFieldTypeName(String.class.getSimpleName());
                    } else {
                        createField.setFieldTypeName(v.getClass().getSimpleName());
                    }

                }
            } else {

                // k 对象 或者不是Java 规则的ClassName
            }
            createInfo.getCreateFieldList().add(createField);
        });
        // 数据去重

        createInfo.setClassName("Temp" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd")));
        return createInfo;
    }

    public static JavaVerification verificationKey(String key) {
        JavaVerification javaVerification = new JavaVerification();
        javaVerification.setOldName(key);
        boolean isJava = true;
        // 非法参数
        List<String> illegalList = Arrays.asList(
                NormalUsedString.DOT,
                NormalUsedString.COMMA,
                NormalUsedString.ASTERISK,
                NormalUsedString.COLON,
                NormalUsedString.SLASH,
                NormalUsedString.DASH);
        for (String illegal : illegalList) {
            if (key.contains(illegal)) {
                isJava = false;
                key = key.replace(illegal, "");
                javaVerification.setNewName(key);
            }
        }
        if (isNumeric(key)) {
            javaVerification.setNewName("temp" + key);
            isJava = false;
        }
        javaVerification.setJava(isJava);
        return javaVerification;
    }

    public static boolean isNumeric(String str) {
        return pattern.matcher(str).matches();
    }

    /**
     * @return
     * @params 转换成 LazyTableAnnotation
     * @author Jiawei Wu
     * @date 2020/12/31 6:52 下午
     **/
    public ClassLazyTableEndpoint toEasyTableAnnotation() {
        return toEasyTableAnnotation(false);
    }

    public ClassLazyTableEndpoint toEasyTableAnnotation(boolean isCapitalized) {
        return toEasyTableAnnotation(false, false);
    }

    /**
     * description
     *
     * @param isCapitalized   是否大写
     * @param humpToUnderline 驼峰转下划线
     * @return
     * @exception/throws
     * @author Jiawei Wu
     * @date 2021/1/20 下午5:28
     */
    public ClassLazyTableEndpoint toEasyTableAnnotation(boolean isCapitalized, boolean humpToUnderline) {
        ClassLazyTableEndpoint lazyTableAnnotation = new ClassLazyTableEndpoint();
        lazyTableAnnotation.setClassName(this.getClass().getName());
        lazyTableAnnotation.setClazz(this.getClass());
        lazyTableAnnotation.setTableName(CamelAndUnderLineConverter.humpToLine2(uniqueLabel));
        lazyTableAnnotation.setComment(String.format("创建时间%s", LocalDateTime.now()));
//        lazyTableAnnotation.setKafkaCode(uniqueLabel);
//        lazyTableAnnotation.setKafkaTopicName(uniqueLabel);
//        lazyTableAnnotation.setKafkaSchemaName(UUID.randomUUID() + uniqueLabel);
        List<FieldLazyTableFieldEndpoint> convertedFieldList = new ArrayList<>();
        forEach((key, value) -> {
            String fieldName = key.toString();
            FieldLazyTableFieldEndpoint convertedField = new FieldLazyTableFieldEndpoint();
            convertedField.setName(fieldName);
            // 对key进行 调整
            if (keyAdjust) {
                if (humpToUnderline) {
                    fieldName = CamelAndUnderLineConverter.humpToLine2(fieldName);
                }
                if (isCapitalized) {
                    fieldName = fieldName.toUpperCase();
                } else {
                    fieldName = fieldName.toLowerCase();
                }

            } else {
                fieldName = key.toString();
            }
            String tempFieldName = fieldName;
            if (LazyDatabaseJsonMessage.specialFields.contains(tempFieldName.toUpperCase())) {
                fieldName = "`" + fieldName + "`";
            }

            Class fieldClazz = null == value ? ValueType.getOrDefault(key, String.class) : value.getClass();
            convertedField.setColumnType(LazyTableField.FieldType.getTypeByClass(fieldClazz));
            convertedField.setComment(String.format("字段创建时间%s", LocalDateTime.now()));
            convertedField.setClazz(fieldClazz);
            convertedField.setExist(true);
            convertedField.setColumnName(CamelAndUnderLineConverter.humpToLine2(fieldName));
            // 保持原来的数据key
            if (!keyAdjust) {
                convertedField.setColumnName(fieldName);
            }

            if (uniqueFieldList.contains(key)) {
                convertedField.setFieldIndexType(LayerField.LayerFieldType.UNIQUE);
            } else {
                convertedField.setFieldIndexType(LayerField.LayerFieldType.FIELD_TYPE);
            }

            convertedFieldList.add(convertedField);
        });
        lazyTableAnnotation.setFieldEndpoints(convertedFieldList);
        return lazyTableAnnotation;
    }

    public V put(K key, V value, String valueClass) {
        try {
            ValueType.put(key, Class.forName(valueClass));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return put(key, value);
    }

    public V put(K key, V value, Class valueClass) {
        ValueType.put(key, valueClass);
        return put(key, value);
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

    public V putUnique(K key, V value) {
        uniqueFieldList.add(key);
        return super.put(key, value);
    }


    public boolean isModifyUniqueLabel() {
        return modifyUniqueLabel;
    }

    public String getUniqueLabel() {
        return uniqueLabel;
    }

    public void setUniqueLabel(String uniqueLabel) {
        this.modifyUniqueLabel = true;
        this.uniqueLabel = uniqueLabel;
    }

    /**
     * @return String  class 字符串
     * describe 生成class
     * @author Jia wei Wu
     * @date 2021/1/31 8:12 下午
     **/
    public String generateClass(boolean createClass) {
        EasySmartFillFieldConverter easySmartFillFieldConverter = new EasySmartFillFieldConverter();
        EasySmartFillFieldConverterAbstract.CreateInfo createInfo = mapConverterJava((Map<Object, Object>) this);
        createInfo.setFileSuffix(NormalUsedString.DOT_CLASS);
        createInfo.setClassName(uniqueLabel);
        return createClass ? easySmartFillFieldConverter.targetClassWriteAttributeFieldList(createInfo) : easySmartFillFieldConverter.createInfo2String(createInfo);
    }

    /**
     * 生成class 并创建class 文件
     *
     * @param createJava
     * @return
     */
    public String generateJava(boolean createJava) {
        EasySmartFillFieldConverter easySmartFillFieldConverter = new EasySmartFillFieldConverter();
        EasySmartFillFieldConverterAbstract.CreateInfo createInfo = mapConverterJava((Map<Object, Object>) this);
        createInfo.setFileSuffix(NormalUsedString.DOT_JAVA);
        createInfo.setClassName(uniqueLabel);
        return createJava ? easySmartFillFieldConverter.targetClassWriteAttributeFieldList(createInfo) : easySmartFillFieldConverter.createInfo2String(createInfo);
    }

    /**
     * @return describe 对象处理之前
     * @author Jia wei Wu
     * @date 2021/3/2 6:24 下午
     **/
    @Override
    public Object beforeObjectProcess() throws ProcessException {
        return null;
    }

    public byte[] getBytes(String key) {
        Object value = get(key);

        if (value == null) {
            return null;
        }

        return castToBytes(value);
    }

    public void setKeyAdjust(boolean keyAdjust) {
        this.keyAdjust = keyAdjust;
    }
}