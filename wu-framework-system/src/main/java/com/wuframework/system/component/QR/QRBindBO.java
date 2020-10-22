package com.wuframework.system.component.QR;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 绑定二维码操作
 */
@Data
public class QRBindBO extends QRBO {

    @ApiModelProperty(value = "是否绑定 默认绑定操作")
    private Boolean bind = true;


}
