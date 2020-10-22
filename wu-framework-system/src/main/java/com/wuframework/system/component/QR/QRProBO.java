package com.wuframework.system.component.QR;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 获取二维码信息
 */
@Data
public class QRProBO {

    @ApiModelProperty(value = "二维码属性")
    private String attribute;

    @ApiModelProperty(value = "完整二维码地址")
    private Boolean url = false;

    @ApiModelProperty(value = "二维码 所有属性")
    private Boolean allAttribute = false;

}
