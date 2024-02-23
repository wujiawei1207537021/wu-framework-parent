package com.wu.framework.easy.upsert.core.dynamic.aop;

import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsert;
import com.wu.framework.easy.upsert.core.dynamic.AbstractDynamicEasyUpsert;
import com.wu.framework.easy.upsert.core.dynamic.toolkit.DynamicEasyUpsertContextHolder;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.annotation.Nonnull;

/**
 * description  自定义快速一数据源切换(Kafka、MySQL多数据源-mybatis)切面绑定
 *
 * @author Jia wei Wu
 * @date 2020/9/11 上午9:24
 */
public abstract class AbstractPointcutQuickEasyUpsertAnnotationAdvisor extends AbstractPointcutEasyUpsertAnnotationAdvisor {
    private final AbstractDynamicEasyUpsert abstractDynamicEasyUpsert;

    protected AbstractPointcutQuickEasyUpsertAnnotationAdvisor(AbstractDynamicEasyUpsert abstractDynamicEasyUpsert) {
        this.abstractDynamicEasyUpsert = abstractDynamicEasyUpsert;
    }

    /**
     * @return describe 切面处理
     * @author Jia wei Wu
     * @date 2021/7/10 11:15 上午
     **/
    @Override
    public Advice getAdvice() {
        return new MethodInterceptor() {

            @Override
            public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
                // 切换数据源
                EasyUpsert easyUpsert = determineEasyUpsert(invocation, EasyUpsert.class);
                try {
                    DynamicEasyUpsertContextHolder.push(easyUpsert);
                    Object object = invocation.proceed();
                    if (null == object) {
                        return null;
                    }
                    abstractDynamicEasyUpsert.determineIEasyUpsert().fuzzyUpsert(object);
                    return object;
                } finally {
                    DynamicEasyUpsertContextHolder.poll();
                }
            }
        };
    }
}


