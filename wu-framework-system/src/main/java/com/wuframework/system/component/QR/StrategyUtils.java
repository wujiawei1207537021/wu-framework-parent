package com.wuframework.system.component.QR;

import com.wuframework.shiro.exceptions.CustomException;
import com.wuframework.shiro.util.ShiroContextUtil;
import org.springframework.beans.BeansException;
import org.springframework.util.ObjectUtils;

import java.util.Collection;

/**
 * 策略服务
 */
public class StrategyUtils {


    public static <T> T getBeansWithAnnotation(Class<T> interfac,  String code) throws BeansException {
        return getBeansWithAnnotation(interfac, StrategyService.class,code);
    }

    public static <T> T getBeansWithAnnotation(Class<T> interfac, Class<? extends StrategyService> annotation, String code) throws BeansException {
        if (ObjectUtils.isEmpty(code)) {
            throw new CustomException("QRService is null ");
        }
        Collection<T> tCollection = ShiroContextUtil.getApplicationContext().getBeansOfType(interfac).values();
        for (T t : tCollection) {
            StrategyService strategyService = t.getClass().getAnnotation(annotation);
            if (ObjectUtils.isEmpty(strategyService)) {
                throw new CustomException("not found code by QRService :" + code);
            }
            if (code.equals(strategyService.value())) {
                return t;
            }
        }
        throw new CustomException("fail to find  code by QRService :" + code);
    }


}
