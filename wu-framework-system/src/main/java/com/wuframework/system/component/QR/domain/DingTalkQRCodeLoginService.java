package com.wuframework.system.component.QR.domain;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.taobao.api.ApiException;
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

/**
 * 钉钉登录
 */
@StrategyService(QRStrategy.DINGTALK)
public class DingTalkQRCodeLoginService implements QRCodeService {

    @Resource
    private QRProperties qrProperties;

    @Resource
    private SysUserJpaRepository sysUserJpaRepository;


    /**
     * 获取openID
     *
     * @param code
     * @return
     */
    public String getOpenId(String code) {
        // 查询当先服务类型对应的 数据信息（serverUrl， accessKey， accessSecret）
        DefaultDingTalkClient client = new DefaultDingTalkClient(qrProperties.getDingtalk().getServerUrl());
        OapiSnsGetuserinfoBycodeRequest req = new OapiSnsGetuserinfoBycodeRequest();
        req.setTmpAuthCode(code);
        OapiSnsGetuserinfoBycodeResponse response = null;
        try {
            response = client.execute(req, qrProperties.getDingtalk().getAccessKey(), qrProperties.getDingtalk().getAccessSecret());
            if (response.isSuccess()) {
                System.out.println("用户钉钉openID" + response.getUserInfo().getOpenid());
                return response.getUserInfo().getOpenid();
            } else {
                System.out.println(response.getErrmsg());
                new CustomException(response.getMessage());
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 通过openID 获取用户
     * @param qrbo
     * @return
     */
    @Override
    public DefaultSysUser getUser(QRBO qrbo) {
        String o = getOpenId(qrbo.getCode());
        if (!ObjectUtils.isEmpty(o)) {
            DefaultSysUser defaultSysUser = sysUserJpaRepository.findByDingtalkOpenId(o);
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
            String openId = getOpenId(qrBindBO.getCode());
            if (!ObjectUtils.isEmpty(sysUserJpaRepository.findByDingtalkOpenId(openId))) {
                throw new CustomException("当前钉钉账号已经绑定其他用户");
            }
            defaultSysUser.setDingtalkOpenId(openId);
        } else {
            defaultSysUser.setDingtalkOpenId("");
        }
        return defaultSysUser;
    }


    @Override
    public Result qRCodeProperties(QRProBO qrProBO) {
//        指定属性
        if (!ObjectUtils.isEmpty(qrProBO.getAttribute())) {
            if ("accessSecret".equals(qrProBO.getAttribute())) {
                return ResultFactory.errorOf("敏感字段权限不足");
            }
            try {
                Field filed = QRProperties.Dingtalk.class.getDeclaredField(qrProBO.getAttribute());
                filed.setAccessible(true);
                return ResultFactory.successOf(filed.get(qrProperties.getDingtalk()));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
                return ResultFactory.errorOf("属性不存在");
            }
        }
//        所有属性
        if (qrProBO.getAllAttribute()) {
            QRProperties.Dingtalk dingtalk = qrProperties.getDingtalk();
            dingtalk.setAccessSecret(null);
            return ResultFactory.successOf(dingtalk);
        }
//        url
        String url = "https://oapi.dingtalk.com/connect/qrconnect?appid=";
//        return "https://oapi.dingtalk.com/connect/qrconnect?appid=dingoauysihjy3mstkwadn&response_type=code&scope=snsapi_login&state=1&redirect_uri=http://saas.yuntsoft.com/login";
        if (ObjectUtils.isEmpty(qrProperties.getDingtalk().getQRCodeUrl())) {
            url = url + qrProperties.getDingtalk().getAccessKey() + "&response_type=code&scope=snsapi_login&state=" + QRStrategy.DINGTALK + "&redirect_uri=" + qrProperties.getDingtalk().getRedirectUri();
        } else {
            url = qrProperties.getDingtalk().getQRCodeUrl();
        }
        return ResultFactory.successOf(url);
    }


}
