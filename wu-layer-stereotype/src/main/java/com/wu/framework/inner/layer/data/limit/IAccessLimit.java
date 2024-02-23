package com.wu.framework.inner.layer.data.limit;

/**
 * description 接口访问限制
 *
 * @author Jia wei Wu
 * @date 2022/10/09 5:50 下午
 */
public interface IAccessLimit {

    /**
     * 限流控制
     *
     * @param accessLimit
     * @param key
     */
    void andThen(EasyAccessLimit accessLimit, String key);
}
