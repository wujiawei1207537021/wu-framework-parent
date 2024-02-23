package com.wu.framework.easy.stereotype.dynamic.config;


import com.wu.framework.easy.stereotype.dynamic.AbstractDynamicEasyUpsert;
import com.wu.framework.easy.stereotype.dynamic.aop.EasyUpsertDSAnnotationAdvisor;
import com.wu.framework.easy.stereotype.dynamic.aop.QuickEasyUpsertAnnotationAdvisor;
import com.wu.framework.easy.stereotype.upsert.IEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.component.IUpsert;
import com.wu.framework.easy.stereotype.upsert.config.UpsertConfig;
import com.wu.framework.easy.stereotype.upsert.handler.IUpsertHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * description 自定义一数据源配置注入
 *
 * @author Jia wei Wu
 * @date 2020/9/11 上午9:39
 */
public class EasyDynamicDataSourceConfig {

    private final List<IEasyUpsert> iEasyUpsertList;
    private final UpsertConfig upsertConfig;
    private final AbstractDynamicEasyUpsert abstractDynamicEasyUpsert;


    public EasyDynamicDataSourceConfig(List<IEasyUpsert> iEasyUpsertList, UpsertConfig upsertConfig, AbstractDynamicEasyUpsert abstractDynamicEasyUpsert) {
        this.iEasyUpsertList = iEasyUpsertList;
        this.upsertConfig = upsertConfig;
        this.abstractDynamicEasyUpsert = abstractDynamicEasyUpsert;
    }

    /**
     * 切换方式
     *
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
     * 注解直接操作切换方式+入库
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public QuickEasyUpsertAnnotationAdvisor quickEasyUpsertAnnotationAdvisor() {
        QuickEasyUpsertAnnotationAdvisor.QuickEasyUpsertAnnotationInterceptor interceptor = new QuickEasyUpsertAnnotationAdvisor.QuickEasyUpsertAnnotationInterceptor(abstractDynamicEasyUpsert);
        QuickEasyUpsertAnnotationAdvisor advisor = new QuickEasyUpsertAnnotationAdvisor(interceptor);
        advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return advisor;
    }

    /**
     * @param
     * @return
     * @describe 注册IUpsert的代理类
     * @author Jia wei Wu
     * @date 2021/3/29 6:58 下午
     **/
    @Bean
    public IUpsert iUpsert(IUpsertHandler iUpsertHandler) {
        return (IUpsert) Proxy.newProxyInstance(IUpsert.class.getClassLoader(), new Class[]{IUpsert.class}, iUpsertHandler);
    }

}
