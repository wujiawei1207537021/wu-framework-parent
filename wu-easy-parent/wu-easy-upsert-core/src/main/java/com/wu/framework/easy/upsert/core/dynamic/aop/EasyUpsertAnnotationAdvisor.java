package com.wu.framework.easy.upsert.core.dynamic.aop;


import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsert;

import java.lang.annotation.Annotation;

/**
 * description 自定义一数据源切面绑定
 *
 * @author Jia wei Wu
 * @date 2020/9/11 上午9:24
 */
public class EasyUpsertAnnotationAdvisor extends AbstractPointcutEasyUpsertAnnotationAdvisor {


    @Override
    public Class<? extends Annotation> getAnnotationClass() {
        return EasyUpsert.class;
    }
}
