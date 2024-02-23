package com.wu.framework.easy.upsert.dynamic.aop;


import com.wu.framework.easy.upsert.EasyUpsertMySQL;
import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsert;
import com.wu.framework.easy.upsert.core.dynamic.aop.AbstractPointcutEasyUpsertAnnotationAdvisor;
import com.wu.framework.easy.upsert.core.dynamic.toolkit.DynamicEasyUpsertContextHolder;
import com.wu.framework.inner.dynamic.database.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.LazyDS;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;

/**
 * description 自定义一数据源切面绑定
 *
 * @author Jia wei Wu
 * @date 2020/9/11 上午9:24
 */
public class EasyUpsertMySQLAnnotationAdvisor extends AbstractPointcutEasyUpsertAnnotationAdvisor {

    /**
     * @return
     * describe 切面处理
     * @author Jia wei Wu
     * @date 2021/7/10 11:15 上午
     **/
    @Override
    public Advice getAdvice() {
        return new MethodInterceptor() {
            @Nullable
            @Override
            public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
                // 切换数据源
                 LazyDS lazyDS = determineEasyUpsert(invocation, LazyDS.class);
                // 数据库数据源切换
                DynamicLazyDSContextHolder.push(lazyDS);
                // upsert 策略切换
                 EasyUpsert easyUpsert = determineEasyUpsert(invocation, EasyUpsert.class);
                DynamicEasyUpsertContextHolder.push(easyUpsert);
                try {
                    return invocation.proceed();
                } finally {
                    DynamicLazyDSContextHolder.clear();
                    DynamicEasyUpsertContextHolder.clear();
                }
            }
        };
    }

    @Override
    public Class<? extends Annotation> getAnnotationClass() {
        return EasyUpsertMySQL.class;
    }
}
