package com.wu.framework.easy.upsert.core.dynamic.aop;

import com.wu.framework.easy.upsert.autoconfigure.dynamic.QuickEasyUpsert;
import com.wu.framework.easy.upsert.core.dynamic.AbstractDynamicEasyUpsert;

import java.lang.annotation.Annotation;

/**
 * description  自定义快速一数据源切换(Kafka、MySQL多数据源-mybatis)切面绑定
 *
 * @author Jia wei Wu
 * @date 2020/9/11 上午9:24
 */
public class QuickEasyUpsertAnnotationAdvisor extends AbstractPointcutQuickEasyUpsertAnnotationAdvisor {


    public QuickEasyUpsertAnnotationAdvisor(AbstractDynamicEasyUpsert abstractDynamicEasyUpsert) {
        super(abstractDynamicEasyUpsert);
    }

    @Override
    public Class<? extends Annotation> getAnnotationClass() {
        return QuickEasyUpsert.class;
    }
}


