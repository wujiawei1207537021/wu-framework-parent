package com.wu.framework.easy.stereotype.upsert.dynamic.upsert.config;


import com.wu.framework.easy.stereotype.upsert.dynamic.upsert.AbstractDynamicEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.dynamic.upsert.aop.EasyUpsertDSAnnotationAdvisor;
import com.wu.framework.easy.stereotype.upsert.dynamic.upsert.aop.QuickEasyUpsertAnnotationAdvisor;
import com.wu.framework.easy.stereotype.upsert.upsert.IEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.upsert.config.UpsertConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

import java.util.List;

/**
 * description 自定义一数据源配置注入
 *
 * @author 吴佳伟
 * @date 2020/9/11 上午9:39
 */
public class CustomDynamicDataSourceConfig {

    private final List<IEasyUpsert> iEasyUpsertList;
    private final UpsertConfig upsertConfig;
    private final AbstractDynamicEasyUpsert abstractDynamicEasyUpsert;


    public CustomDynamicDataSourceConfig(List<IEasyUpsert> iEasyUpsertList, UpsertConfig upsertConfig, AbstractDynamicEasyUpsert abstractDynamicEasyUpsert) {
        this.iEasyUpsertList = iEasyUpsertList;
        this.upsertConfig = upsertConfig;
        this.abstractDynamicEasyUpsert = abstractDynamicEasyUpsert;
    }

    /**
     * 切换方式
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public EasyUpsertDSAnnotationAdvisor easyUpsertDSAnnotationAdvisor() {
        EasyUpsertDSAnnotationAdvisor.EasyUpsertDSAnnotationInterceptor interceptor = new EasyUpsertDSAnnotationAdvisor.EasyUpsertDSAnnotationInterceptor();
        EasyUpsertDSAnnotationAdvisor advisor = new EasyUpsertDSAnnotationAdvisor(interceptor);
        advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return advisor;
    }

    /**
     *  注解直接操作切换方式+入库
     * @return
     */
    @Bean
    public QuickEasyUpsertAnnotationAdvisor quickEasyUpsertAnnotationAdvisor() {
        QuickEasyUpsertAnnotationAdvisor.QuickEasyUpsertAnnotationInterceptor interceptor = new QuickEasyUpsertAnnotationAdvisor.QuickEasyUpsertAnnotationInterceptor(abstractDynamicEasyUpsert);
        QuickEasyUpsertAnnotationAdvisor advisor = new QuickEasyUpsertAnnotationAdvisor(interceptor);
        advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return advisor;
    }

}
