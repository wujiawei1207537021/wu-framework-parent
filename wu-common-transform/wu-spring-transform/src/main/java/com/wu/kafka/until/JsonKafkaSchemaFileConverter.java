package com.wu.kafka.until;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supconit.its.transform.util.JsonUtils;
import com.wu.kafka.entity.MesEntity;
import com.wu.kafka.stereotype.KafkaSchemaFile;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;

/**
 * @Description 转换含有KafkaSchemaFile 注解的类参数
 * @Author Jia wei Wu
 * @Date 2020-05-15 2:54 下午
 */
public class JsonKafkaSchemaFileConverter {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    /**
     * @param
     * @return
     * @description 单个对象转换
     * @exception/throws 无
     * @author Jia wei Wu
     * @date 2020/5/15 3:10 下午
     */
    public static String simpleBean2String(Object bean) {
        try {
            return OBJECT_MAPPER.writeValueAsString(parseBean2map(bean));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param
     * @return
     * @description 单个对象转换
     * @exception/throws 无
     * @author Jia wei Wu
     * @date 2020/5/15 3:10 下午
     */
    public static String listBean2String(Object bean) {
        List<String> mapList = new ArrayList<>();
        if (bean instanceof Collection) {
            Collection collection = (Collection) bean;
            for (Object o : collection) {
                mapList.add(simpleBean2String(o));
            }
        }
        return JsonUtils.toJsonString(mapList);
    }

    /**
     * @param
     * @return
     * @description 对象转换成map
     * @exception/throws 无
     * @author Jia wei Wu
     * @date 2020/5/15 4:04 下午
     */
    public static Map<String, Object> parseBean2map(Object bean) {
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
                KafkaSchemaFile kafkaSchemaFile = declaredField.getAnnotation(KafkaSchemaFile.class);
                Object v = null;
                try {
                    v = declaredField.get(bean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (ObjectUtils.isEmpty(kafkaSchemaFile)) {
                    map.put(CamelAndUnderLineConverter.humpToLine2(declaredField.getName()), v);
                } else {
                    map.put(kafkaSchemaFile.field(), v);
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * @param
     * @return
     * @description 数组对象转换
     * @exception/throws 无
     * @author Jia wei Wu
     * @date 2020/5/15 4:05 下午
     */
    public static List<String> parseArray(String str) {
        try {
            return OBJECT_MAPPER.readValue(str, List.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        MesEntity mesEntity = new MesEntity();
        mesEntity.setUserId("333");
        mesEntity.setUserName("shide");
        System.out.println(simpleBean2String(mesEntity));
    }
}
