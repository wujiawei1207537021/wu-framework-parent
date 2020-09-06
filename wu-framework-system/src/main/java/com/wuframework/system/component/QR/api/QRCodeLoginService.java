package com.wuframework.system.component.QR.api;

import com.wuframework.response.Result;
import com.wuframework.system.component.QR.QRBO;
import com.wuframework.system.component.QR.QRBindBO;
import com.wuframework.system.component.QR.QRProBO;

/**
 * 二维码登录服务
 */
public interface QRCodeLoginService {
    /**
     * 二维码登录服务
     * @param qrbo
     * @return
     */
    Result loginQR(QRBO qrbo);

    /**
     * 用户绑定二维码
     * @param qrBindBO
     * @param userId
     * @return
     */
    Result bindQR(QRBindBO qrBindBO, Integer userId);

    /**
     * 获取二维码
     * @param type
     * @param qrProBO
     * @return
     */
    Result qRCodeProperties(String type, QRProBO qrProBO);

}
