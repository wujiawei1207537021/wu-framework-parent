package com.wu.framework.easy.upsert.sink.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author Jia wei Wu
 * @params
 * @return
 * @date 2020/9/18 下午10:27
 **/
public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String toJsonString(Object value) {
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("转换为Json字符串出错:%s" + e);
        }
        return json;
    }

    public static <T> T parseObject(String fromValue, Class<T> toValueType) {
        T toValue;
        try {
            toValue = OBJECT_MAPPER.readValue(fromValue, toValueType);
        } catch (IOException e) {
            throw new RuntimeException("转换为Json字符串出错:%s" + e);
        }
        return toValue;
    }

    public static <T> T parseObject(String fromValue, TypeReference<T> toValueType) {
        T toValue;
        try {
            toValue = OBJECT_MAPPER.readValue(fromValue, toValueType);
        } catch (IOException e) {
            throw new RuntimeException("转换为Json字符串出错:%s" + e);
        }
        return toValue;
    }

    public static <T> T convertObject(Object fromValue, Class<T> toValueType) {
        return OBJECT_MAPPER.convertValue(fromValue, toValueType);
    }
}
