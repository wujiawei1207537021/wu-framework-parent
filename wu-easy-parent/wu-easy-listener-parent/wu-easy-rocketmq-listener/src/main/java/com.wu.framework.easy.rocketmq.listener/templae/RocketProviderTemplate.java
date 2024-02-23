package com.wu.framework.easy.rocketmq.listener.templae;

import com.wu.framework.easy.listener.core.ProviderTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2021/12/16 9:04 下午
 */
@ConditionalOnProperty(prefix = "spring.rocket", value = "getHost")
@ConditionalOnMissingBean(value = RocketProviderTemplate.class)
public class RocketProviderTemplate<T> implements ProviderTemplate<T, Long> {

    /**
     * 数据发送
     *
     * @param topic   发送的主题、通道
     * @param message 发送的数据
     * @return ID 返回数据
     */
    @Override
    public <T1> Long send(String topic, T1 message) {
        return null;
    }
}
