package com.wu.smart.acw.client;

import com.wu.smart.acw.client.properties.AcwServerProperties;

/**
 * description acw 客户端
 *
 * @author 吴佳伟
 * @date 2023/07/17 16:04
 */
public interface AcwClient {
    /**
     * 创建对应的Java 代码
     * @param acwServerProperties 地址密钥信息
     */
    void stuffedJava(AcwServerProperties acwServerProperties);


}
