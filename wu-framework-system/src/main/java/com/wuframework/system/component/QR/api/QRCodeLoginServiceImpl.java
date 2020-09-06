package com.wuframework.system.component.QR.api;

import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.shiro.LoginService;
import com.wuframework.shiro.UserDetailsService;
import com.wuframework.shiro.model.UserDetails;
import com.wuframework.system.common.entity.DefaultSysUser;
import com.wuframework.system.component.QR.QRBO;
import com.wuframework.system.component.QR.QRBindBO;
import com.wuframework.system.component.QR.QRProBO;
import com.wuframework.system.component.QR.StrategyUtils;
import com.wuframework.system.component.QR.domain.QRCodeService;
import com.wuframework.system.component.QR.domain.QRFunction;
import com.wuframework.system.persistence.jpa.SysUserJpaRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class QRCodeLoginServiceImpl implements QRCodeLoginService {

    @Resource
    private SysUserJpaRepository sysUserJpaRepository;

    @Resource
    private LoginService loginService;

    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 登录 SHoiSXXnrakiPCFtRiSLOiSPIgiEiE
     * @param qrbo
     * @return
     */
    @Override
    public Result loginQR(QRBO qrbo) {
        UserDetails defaultSysUser = StrategyUtils.getBeansWithAnnotation(QRCodeService.class, qrbo.getType()).getUser(qrbo);
        defaultSysUser=userDetailsService.loadUserByUsername(defaultSysUser.getUsername());
        return loginService.accessToken(defaultSysUser, "web");
    }

    /**
     * 绑定
     * @param qrBindBO
     * @param userId
     * @return
     */
    @Override
    public Result bindQR(QRBindBO qrBindBO, Integer userId) {
        DefaultSysUser defaultSysUser = sysUserJpaRepository.findByUserId(userId);
        defaultSysUser = StrategyUtils.getBeansWithAnnotation(QRCodeService.class, qrBindBO.getType()).setOpenID(qrBindBO, defaultSysUser);
        sysUserJpaRepository.save(defaultSysUser);
        return ResultFactory.successOf();

    }

    @Override
    public Result qRCodeProperties(String type, QRProBO qrProBO) {
        return StrategyUtils.getBeansWithAnnotation(QRCodeService.class, type).qRCodeProperties(qrProBO);
    }



    /**
     * 用户登录 绑定
     *
     * @param openId
     * @param qrFunction
     * @return
     */
    public Result loginOrBind(String openId, QRFunction<String, Result> qrFunction) {
        return qrFunction.QR(openId);
    }
}
