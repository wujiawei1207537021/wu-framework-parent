package com.wuframework.system.component.QR.domain;

import com.wuframework.response.Result;
import com.wuframework.system.common.entity.DefaultSysUser;
import com.wuframework.system.component.QR.QRBO;
import com.wuframework.system.component.QR.QRBindBO;
import com.wuframework.system.component.QR.QRProBO;

/**
 * 第三方二维码登录服务
 */
public interface QRCodeService {


    /**
     * 获取用户
     * @param qrbo
     * @return
     */
    DefaultSysUser getUser(QRBO qrbo);

    /**
     * 绑定用户
     */
    DefaultSysUser setOpenID(QRBindBO qrBindBO, DefaultSysUser defaultSysUser);

    /**
     * 获取二维码地址
     * @return
     */
    Result qRCodeProperties(QRProBO qrProBO);
}
