package com.wu.framework.data.relay.platform.provider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/6/13 22:46
 */
public interface RelayIn {

    /**
     * 转换缓存
     */
    Map<String, Map<String, String>> CONVERSION_CACHE = new ConcurrentHashMap<>();

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
    Map<String, Object> in(Object payload, String tableName);

}
