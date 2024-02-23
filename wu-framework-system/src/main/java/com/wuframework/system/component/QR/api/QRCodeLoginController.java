package com.wuframework.system.component.QR.api;


import com.wuframework.response.Result;
import com.wuframework.response.mark.ValidType;
import com.wuframework.system.component.QR.QRBO;
import com.wuframework.system.component.QR.QRBindBO;
import com.wuframework.system.component.QR.QRProBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * Login QR code
 * 获取登录二维码
 */
@Api(tags = "二维码登录")
@RestController
@RequestMapping("/login/QR")
public class QRCodeLoginController {

    @Resource
    private QRCodeLoginService qrCodeLoginService;

    /**
     * @return
     */
    @ApiOperation("获取二维码参数地址 路径参数如:DINGTALK,QQ （优先级指定属性->所有属性->url）")
    @GetMapping("/{type}")
    public Result qRCodeProperties(@PathVariable String type, @ModelAttribute QRProBO qrProBO) {
        return qrCodeLoginService.qRCodeProperties(type, qrProBO);
    }

    /**
     * 二维码登录
     * TODO POST
     *
     * @param QRBO
     * @return
     */
    @ApiOperation("二维码登录")
    @PostMapping()
    public Result loginQR(@Validated(ValidType.Retrieve.class) @ModelAttribute QRBO QRBO) {
        return qrCodeLoginService.loginQR(QRBO);
    }

    /**
     * 用户绑定二维码
     *
     * @param QRBO
     * @return
     */
    @ApiOperation("用户绑定二维码")
    @PostMapping("/{userId}")
    public Result bindQR(@Validated(ValidType.Update.class) @ModelAttribute QRBindBO qrBindBO, @PathVariable Integer userId) {
//        return QRUtils.getBeansWithAnnotation(QRCodeLoginService.class,qrbo.getType()).bindQR(qrbo,userId);
        return qrCodeLoginService.bindQR(qrBindBO, userId);
    }
}
