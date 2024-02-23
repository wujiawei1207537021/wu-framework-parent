package com.wu.framework.easy.upsert.dynamic.aop;


import com.wu.framework.easy.upsert.QuickEasyUpsertRedisSearch;
import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsert;
import com.wu.framework.easy.upsert.core.dynamic.AbstractDynamicEasyUpsert;
import com.wu.framework.easy.upsert.core.dynamic.aop.AbstractPointcutQuickEasyUpsertAnnotationAdvisor;
import com.wu.framework.easy.upsert.core.dynamic.toolkit.DynamicEasyUpsertContextHolder;
import com.wu.framework.inner.redis.annotation.LazyRedis;
import com.wu.framework.inner.redis.component.LazyRedisTemplate;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;

/**
 * description 自定义一数据源切面绑定
 *
 * @author Jia wei Wu
 * @date 2020/9/11 上午9:24
 */
public class QuickEasyUpsertRediSearchAnnotationAdvisor extends AbstractPointcutQuickEasyUpsertAnnotationAdvisor {

    private final AbstractDynamicEasyUpsert abstractDynamicEasyUpsert;
    private final LazyRedisTemplate lazyRedisTemplate;

    public QuickEasyUpsertRediSearchAnnotationAdvisor(AbstractDynamicEasyUpsert abstractDynamicEasyUpsert, LazyRedisTemplate lazyRedisTemplate) {
        super(abstractDynamicEasyUpsert);
        this.abstractDynamicEasyUpsert = abstractDynamicEasyUpsert;
        this.lazyRedisTemplate = lazyRedisTemplate;
    }

    /**
     * @return describe 切面处理
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
                LazyRedis lazyRedis = determineEasyUpsert(invocation, LazyRedis.class);
                // 数据库数据源切换
                lazyRedisTemplate.setDyDatabase(lazyRedis.database());
                // upsert 策略切换
                EasyUpsert easyUpsert = determineEasyUpsert(invocation, EasyUpsert.class);
                DynamicEasyUpsertContextHolder.push(easyUpsert);
                try {
                    Object object = invocation.proceed();
                    if (null == object) {
                        return null;
                    }
                    abstractDynamicEasyUpsert.determineIEasyUpsert().fuzzyUpsert(object);
                    return object;
                } finally {
                    DynamicEasyUpsertContextHolder.clear();
                    lazyRedisTemplate.reset();
                }
            }
        };
    }

    @Override
    public Class<? extends Annotation> getAnnotationClass() {
        return QuickEasyUpsertRedisSearch.class;
    }
}
