package com.wuframework.system.component.QR.domain;

import com.alibaba.fastjson.JSONObject;
import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.response.enmus.DefaultResultCode;
import com.wuframework.shiro.exceptions.CustomException;
import com.wuframework.system.common.entity.DefaultSysUser;
import com.wuframework.system.component.QR.*;
import com.wuframework.system.persistence.jpa.SysUserJpaRepository;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * QQ 扫码登录服务
 */

@StrategyService(QRStrategy.QQ)
public class QQQRCodeLoginService implements QRCodeService {

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private QRProperties qrProperties;
    @Resource
    private SysUserJpaRepository sysUserJpaRepository;


    /**
     * 获取Access Token
     * access_token=7B0DE00E80249F5613316ADD9AD4819F&expires_in=7776000&refresh_token=325A649D220C839861B60660BE2FAD85
     *
     * @param code
     * @return
     */
    public Map<String, String> getAccessToken(String code) {
        String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id={1}&client_secret={2}&code={3}&redirect_uri={4}";
        String res = restTemplate.getForObject(url, String.class, qrProperties.getQq().getAppId(), qrProperties.getQq().getAppKey(), code, qrProperties.getQq().getRedirectUri());
        System.out.println("QQ获取Access Token" + res);
        Map<String, String> resMap = new HashMap<>();
        try {
            for (String s : res.split("&")) {
                resMap.put(s.split("=")[0], s.split("=")[1]);
            }
        } catch (Exception e) {
            throw new CustomException("QQ扫码登录失败");
        }
        return resMap;
    }

    /**
     * 获取openID
     *
     * @param accessToken
     * @return callback({ " client_id " : " YOUR_APPID ", " openid " : " YOUR_OPENID " });
     */
    private String getOpenId(String accessToken) {
        String url = "https://graph.qq.com/oauth2.0/me?access_token={1}";
        String res = restTemplate.getForObject(url, String.class, accessToken);
        System.out.println(res);
        res = res.replace("callback", "");
        res = res.replace("(", "");
        res = res.replace(")", "");
        res = res.replace(";", "");
        System.out.println(res);
        return JSONObject.parseObject(res).getString("openid");
    }

    public String getOpenId(QRBO qrbo) {
        Map<String, String> tokenMap = this.getAccessToken(qrbo.getCode());
        return getOpenId(tokenMap.get("access_token"));
    }

    @Override
    public DefaultSysUser getUser(QRBO qrbo) {
        String openId = getOpenId(qrbo);
        if (!ObjectUtils.isEmpty(openId)) {
            DefaultSysUser defaultSysUser = sysUserJpaRepository.findByQqOpenId(openId);
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
            String openId = getOpenId(qrBindBO);
            if (!ObjectUtils.isEmpty(sysUserJpaRepository.findByQqOpenId(openId))) {
                throw new CustomException("当前QQ账号已经绑定其他用户");
            }
            defaultSysUser.setQqOpenId(openId);
        } else {
            defaultSysUser.setQqOpenId(null);
        }
        return defaultSysUser;
    }

    @Override
    public Result qRCodeProperties(QRProBO qrProBO) {
        //        指定属性
        if (!ObjectUtils.isEmpty(qrProBO.getAttribute())) {
            if ("appKey".equals(qrProBO.getAttribute())) {
                return ResultFactory.errorOf("敏感字段权限不足");
            }
            try {
                Field filed = QRProperties.QQ.class.getDeclaredField(qrProBO.getAttribute());
                filed.setAccessible(true);
                return ResultFactory.successOf(filed.get(qrProperties.getQq()));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
                return ResultFactory.errorOf("属性不存在");
            }
        }
//        所有属性
        if (qrProBO.getAllAttribute()) {
            QRProperties.QQ qq = qrProperties.getQq();
            qq.setAppKey(null);
            return ResultFactory.successOf(qq);
        }
//        url
        String url = "https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=";
        if (ObjectUtils.isEmpty(qrProperties.getQq().getQRCodeUrl())) {
            //        https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=[YOUR_APPID]&redirect_uri=[YOUR_REDIRECT_URI]&scope=[THE_SCOPE]
            //https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=101850243&redirect_uri=http://saas.yuntsoft.com/mycb&state=QQ

            url = url + qrProperties.getQq().getAppId() + "&state=" + QRStrategy.QQ + "&scope=get_user_info&redirect_uri=" + qrProperties.getQq().getRedirectUri();
        } else {
            url = qrProperties.getQq().getQRCodeUrl();
        }
        return ResultFactory.successOf(url);
    }

}
