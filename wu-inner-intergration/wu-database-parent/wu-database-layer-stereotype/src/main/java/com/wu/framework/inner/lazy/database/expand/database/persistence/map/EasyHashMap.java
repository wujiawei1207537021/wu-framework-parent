package com.wu.framework.inner.lazy.database.expand.database.persistence.map;

import com.wu.framework.inner.layer.CamelAndUnderLineConverter;
import com.wu.framework.inner.layer.data.IBeanUpsert;
import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.data.ProcessException;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.lazy.database.expand.database.persistence.conf.LazyDatabaseJsonMessage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.ConvertedField;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.JavaVerification;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyTableAnnotation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.LazyTableField;
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
 * @describe : 使用此对象会自动创建数据库表
 * @date : 2020/12/31 6:42 下午
 */
public class EasyHashMap<K, V> extends HashMap<K, V> implements Map<K, V>, IBeanUpsert {

    /**
     * 唯一性标示
     */
    private String uniqueLabel = UUID.randomUUID().toString();
    private List<Object> uniqueFieldList = new ArrayList<>();

    /**
     * value  对应的集类型
     */
    private HashMap<K, Class> ValueType = new HashMap();


    // 修改 唯一性标示
    private boolean modifyUniqueLabel = false;


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
     * @return
     * @describe 将Map 转换成 Java
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
                String className = CamelAndUnderLineConverter.lineToHump(NormalUsedString.UNDERSCORE + fieldName);
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
                    createField.setFieldTypeName(v.getClass().getSimpleName());
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
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * @return
     * @params 转换成 LazyTableAnnotation
     * @author Jiawei Wu
     * @date 2020/12/31 6:52 下午
     **/
    public LazyTableAnnotation toEasyTableAnnotation() {
        return toEasyTableAnnotation(false);
    }

    public LazyTableAnnotation toEasyTableAnnotation(boolean isCapitalized) {
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
    public LazyTableAnnotation toEasyTableAnnotation(boolean isCapitalized, boolean humpToUnderline) {
        LazyTableAnnotation lazyTableAnnotation = new LazyTableAnnotation();
        lazyTableAnnotation.setClassName(this.getClass().getName());
        lazyTableAnnotation.setClazz(this.getClass());
        lazyTableAnnotation.setTableName(CamelAndUnderLineConverter.humpToLine2(uniqueLabel));
        lazyTableAnnotation.setComment(String.format("创建时间%s", LocalDateTime.now()));
//        lazyTableAnnotation.setKafkaCode(uniqueLabel);
//        lazyTableAnnotation.setKafkaTopicName(uniqueLabel);
//        lazyTableAnnotation.setKafkaSchemaName(UUID.randomUUID() + uniqueLabel);
        List<ConvertedField> convertedFieldList = new ArrayList<>();
        forEach((key, value) -> {
            String fieldName = key.toString();

            if (humpToUnderline) {
                fieldName = CamelAndUnderLineConverter.humpToLine2(fieldName);
            }
            if (isCapitalized) {
                fieldName = fieldName.toUpperCase();
            } else {
                fieldName = fieldName.toLowerCase();
            }
            String tempFieldName = new String(fieldName);
            if (LazyDatabaseJsonMessage.specialFields.contains(tempFieldName.toUpperCase())) {
                fieldName = "`" + fieldName + "`";
            }
            ConvertedField convertedField = new ConvertedField();
            convertedField.setFieldName(fieldName);
            Class fieldClazz = null == value ? ValueType.getOrDefault(key, String.class) : value.getClass();
            convertedField.setType(LazyTableField.FileType.getTypeByClass(fieldClazz));
            convertedField.setComment(String.format("字段创建时间%s", LocalDateTime.now()));
            convertedField.setClazz(fieldClazz);
            convertedField.setConvertedFieldName(CamelAndUnderLineConverter.humpToLine2(fieldName));
            if (uniqueFieldList.contains(key)) {
                convertedField.setFieldIndexType(LayerField.LayerFieldType.UNIQUE);
            } else {
                convertedField.setFieldIndexType(LayerField.LayerFieldType.FILE_TYPE);
            }

            convertedFieldList.add(convertedField);
        });
        lazyTableAnnotation.setConvertedFieldList(convertedFieldList);
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
     * @describe 生成class
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
     * @return
     * @describe 对象处理之前
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


}