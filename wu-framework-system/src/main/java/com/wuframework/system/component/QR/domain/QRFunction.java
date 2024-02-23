package com.wuframework.system.component.QR.domain;

/**
 * QR函数
 */
@FunctionalInterface
public interface QRFunction<O, R> {

    /**
     * 登录 绑定用户
     *
     * @param o openId
     * @return
     */
    R QR(O o);
}
