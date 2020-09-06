package com.wuframework.system.component.QR.domain;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.response.enmus.DefaultResultCode;
import com.wuframework.shiro.exceptions.CustomException;
import com.wuframework.system.common.entity.DefaultSysUser;
import com.wuframework.system.component.QR.*;
import com.wuframework.system.persistence.jpa.SysUserJpaRepository;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;

@StrategyService(QRStrategy.ALIPAY)
public class AlipayQRCodeLoginService implements QRCodeService {
    @Resource
    private QRProperties qrProperties;

    @Resource
    private SysUserJpaRepository sysUserJpaRepository;

    /**
     * 临时码获取accessToken userId
     *
     * @param code
     * @return
     */
    public AlipaySystemOauthTokenResponse getAlipaySystemOauthToken(String code) {
        AlipayClient alipayClient = new DefaultAlipayClient(qrProperties.getAlipay().getServerUrl(),
                qrProperties.getAlipay().getAppId(), qrProperties.getAlipay().getPrivateKey(), "json", "UTF-8", qrProperties.getAlipay().getAlipayPublicKey(), "RSA2");
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(code);
        request.setGrantType("authorization_code");
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
            System.out.println(oauthTokenResponse.getAccessToken());
            return oauthTokenResponse;
        } catch (AlipayApiException e) {
            //处理异常
            e.printStackTrace();
            throw new CustomException(e);
        }
    }

    @Override
    public DefaultSysUser getUser(QRBO qrbo) {
        AlipaySystemOauthTokenResponse alipaySystemOauthTokenResponse = getAlipaySystemOauthToken(qrbo.getCode());
        if (!ObjectUtils.isEmpty(alipaySystemOauthTokenResponse)) {
            DefaultSysUser defaultSysUser =sysUserJpaRepository.findByAlipayOpenId(alipaySystemOauthTokenResponse.getUserId());
            if (ObjectUtils.isEmpty(defaultSysUser)) {
                throw new CustomException(DefaultResultCode.QR_ASSOCIATION_USER_EXCEPTION.message);
            }
            return defaultSysUser;
        } else {
            throw new CustomException(DefaultResultCode.QR_OPENID_EXCEPTION.message);
        }
    }

    @Override
    public DefaultSysUser setOpenID(QRBindBO qrBindBO, DefaultSysUser defaultSysUser) {
        if (qrBindBO.getBind()) {
            String userId=getAlipaySystemOauthToken(qrBindBO.getCode()).getUserId();
            if(!ObjectUtils.isEmpty(sysUserJpaRepository.findByAlipayOpenId(userId))){
                throw new CustomException("当前支付宝账号已经绑定其他用户");
            }
            defaultSysUser.setAlipayOpenId(userId);
        } else {
            defaultSysUser.setAlipayOpenId("");
        }
        return defaultSysUser;
    }

    @Override
    public Result qRCodeProperties(QRProBO qrProBO) {
        //        指定属性
        if (!ObjectUtils.isEmpty(qrProBO.getAttribute())) {
            if ("privateKey".equals(qrProBO.getAttribute()) | "alipayPublicKey".equals(qrProBO.getAttribute())) {
                return ResultFactory.errorOf("敏感字段权限不足");
            }
            try {
                Field filed = QRProperties.Alipay.class.getDeclaredField(qrProBO.getAttribute());
                filed.setAccessible(true);
                return ResultFactory.successOf(filed.get(qrProperties.getAlipay()));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
                return ResultFactory.errorOf("属性不存在");
            }
        }
//        所有属性
        if (qrProBO.getAllAttribute()) {
            QRProperties.Alipay alipay = qrProperties.getAlipay();
            alipay.setAlipayPublicKey(null);
            alipay.setPrivateKey(null);
            return ResultFactory.successOf(alipay);
        }
//        url
        String url = "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=";
//https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=2021001132627270&scope=auth_user&redirect_uri=http://www.yuntsoft.com:7203/qrLoading
        if (ObjectUtils.isEmpty(qrProperties.getAlipay().getQRCodeUrl())) {
            url = url + qrProperties.getAlipay().getAppId() + "&scope=auth_user&state=" + QRStrategy.ALIPAY + "&redirect_uri=" + qrProperties.getAlipay().getRedirectUri();
        } else {
            url = qrProperties.getAlipay().getQRCodeUrl();
        }
        return ResultFactory.successOf(url);
    }

}
