package com.wu.framework.easy.stereotype.upsert.upsert.converter;


import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Description 转换含有KafkaSchemaFile 注解的类参数
 * @Author 吴佳伟
 * @Date 2020-05-15 2:54 下午
 */
public class JsonFileConverter {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * @param bean
     * @return java.lang.String
     * @description 单个对象转换成JSON 字符串
     * @exception/throws 无
     * @author 吴佳伟
     * @date 2020/5/18 1:52 下午
     */
    public static String simpleBean2JSONString(Object bean) {
        Map map = parseBean2map(bean);
        return JSON.toJSONString(map, SerializerFeature.WriteMapNullValue);
    }


    /**
     * @param collection
     * @return java.util.List<java.lang.String>
     * @description 将bean集合对象转换成 JSON String
     * @exception/throws 无
     * @author 吴佳伟
     * @date 2020/5/18 1:50 下午
     */
    public static String listBean2JSONString(Collection collection) {
        List<String> mapList = new ArrayList<>();
        for (Object o : collection) {
            mapList.add(simpleBean2JSONString(o));
        }
        return mapList.toString();
    }

    /**
     * @param collection
     * @return java.util.List<java.lang.String>
     * @description 将bean集合对象转换成 JSON String 集合
     * @exception/throws 无
     * @author 吴佳伟
     * @date 2020/5/18 1:50 下午
     */
    public static List<String> listBean2ListJSONString(Collection collection) {
        List<String> mapList = new ArrayList<>();
        for (Object o : collection) {
            mapList.add(simpleBean2JSONString(o));
        }
        return parseArray(mapList.toString());
    }

    /**
     * @param bean
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @description 将bean 对象转换成map对象
     * @exception/throws 无
     * @author 吴佳伟
     * @date 2020/5/18 1:53 下午
     */
    public static Map<String, Object> parseBean2map(Object bean) {
        return parseBean2map(bean, null);
    }

    public static Map<String, Object> parseBean2map(Object bean, Map<String, Map<String, String>> iEnumList) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (bean instanceof String) {
                String json = (String) bean;
                bean = OBJECT_MAPPER.readValue(json, Object.class);
            }
            for (Field declaredField : bean.getClass().getDeclaredFields()) {
                if (!declaredField.isAccessible()) {
                    declaredField.setAccessible(true);
                }
                if(UpsertJsonMessage.ignoredFields.contains(declaredField.getName())){
                    continue;
                }
                CustomTableFile customTableFile = AnnotatedElementUtils.getMergedAnnotation(declaredField, CustomTableFile.class);
                Object v = null;
                try {
                    v = declaredField.get(bean);
                    if (ObjectUtils.isEmpty(v) && null != customTableFile && !ObjectUtils.isEmpty(customTableFile.fieldDefaultValue())) {
                        v = customTableFile.fieldDefaultValue();
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                String defaultKey = CamelAndUnderLineConverter.humpToLine2(declaredField.getName());
                if (!ObjectUtils.isEmpty(customTableFile)) {
                    if (!customTableFile.exist()) {
                        continue;
                    } else {
                        if (!ObjectUtils.isEmpty(customTableFile.value())) {
                            defaultKey = customTableFile.value();
                        }
                    }
                }
                v = annotationDictionaryConversion(declaredField, v, iEnumList);
                map.put(defaultKey, v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * @param str
     * @return java.util.List<java.lang.String>
     * @description 将String 字符串转换成List 集合
     * @exception/throws 无
     * @author 吴佳伟
     * @date 2020/5/18 1:54 下午
     */
    public static List<String> parseArray(String str) {
        try {
            return OBJECT_MAPPER.readValue(str, List.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
