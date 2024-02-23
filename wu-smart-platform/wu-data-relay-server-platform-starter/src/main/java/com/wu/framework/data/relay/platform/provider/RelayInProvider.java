package com.wu.framework.data.relay.platform.provider;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/6/13 22:47
 */
public class RelayInProvider implements RelayIn {


    /**
     * describe
     *
     * @param payload   负载
     * @param tableName 表
     * @return
     * @throws
     * @author Jia wei Wu
     * @date 2022/6/13 22:49
     **/
    @Override
    public Map<String, Object> in(Object payload, String tableName) {
        Map<String, Object> outPayload = new HashMap<>();

        boolean containsKey = CONVERSION_CACHE.containsKey(tableName);
        if (containsKey) {
            Map<String, String> stringObjectMap = CONVERSION_CACHE.get(tableName);
            Map<String, Object> payloadMap;
            if (Map.class.isAssignableFrom(payload.getClass())) {
                payloadMap = (Map) payload;
            } else {
                payloadMap = Arrays.stream(payload.getClass().getDeclaredFields()).collect(
                        Collectors.toMap(Field::getName, field -> {
                            field.setAccessible(true);
                            try {
                                return field.get(payload);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }));

            }
            stringObjectMap.forEach((s, o) -> outPayload.put(s, payloadMap.getOrDefault(o, null)));
            return outPayload;
        }
        return outPayload;
    }
}
