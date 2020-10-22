package com.wuframework.system.component.QR;

import com.wuframework.response.mark.ValidType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 登录二维码操作
 */
@Data
public class QRBO {

    @ApiModelProperty(value = "二维码类型 当前支持:DINGTALK, QQ")
    @NotNull(message = "二维码类型能为空", groups = {ValidType.Retrieve.class, ValidType.Update.class})
    private String type;

    @ApiModelProperty(value = "二维码code")
    @NotNull(message = "二维码code不能为空", groups = {ValidType.Retrieve.class, ValidType.Update.class})
    private String code;
//
//
//    @ApiModelProperty(value = "是否绑定 默认绑定操作")
//    private Boolean bind=true;

}
