package com.wu.framework.easy.stereotype.upsert.component.mysql;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;

/**
 * @author : Jiawei Wu
 * @version 1.0
 * @describe :
 * @date : 2021/1/1 7:15 下午
 */
@Deprecated
public class EasyUpsertDataSourceCondition implements Condition {

    /**
     * Determine if the condition matches.
     *
     * @param context  the condition context
     * @param metadata the metadata of the {@link AnnotationMetadata class}
     *                 or {@link MethodMetadata method} being checked
     * @return {@code true} if the condition matches and the component can be registered,
     * or {@code false} to veto the annotated component's registration
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        final boolean containsBeanDefinition = context.getRegistry().containsBeanDefinition("dataSource");
        return containsBeanDefinition;
    }


}
