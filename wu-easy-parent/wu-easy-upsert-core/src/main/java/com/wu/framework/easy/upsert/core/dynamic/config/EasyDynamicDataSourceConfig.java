package com.wu.framework.easy.upsert.core.dynamic.config;


import com.wu.framework.easy.upsert.core.dynamic.AbstractDynamicEasyUpsert;
import com.wu.framework.easy.upsert.core.dynamic.DynamicEasyUpsert;
import com.wu.framework.easy.upsert.core.dynamic.IUpsert;
import com.wu.framework.easy.upsert.core.dynamic.aop.EasyUpsertAnnotationAdvisor;
import com.wu.framework.easy.upsert.core.dynamic.aop.QuickEasyUpsertAnnotationAdvisor;
import com.wu.framework.easy.upsert.core.dynamic.handler.IUpsertHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.reflect.Proxy;

/**
 * description 自定义一数据源配置注入
 *
 * @author Jia wei Wu
 * @date 2020/9/11 上午9:39
 */
@Import({IUpsertHandler.class, DynamicEasyUpsert.class})
public class EasyDynamicDataSourceConfig {


    /**
     * 切换方式
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public EasyUpsertAnnotationAdvisor easyUpsertDSAnnotationAdvisor() {
        EasyUpsertAnnotationAdvisor easyUpsertAnnotationAdvisor = new EasyUpsertAnnotationAdvisor();
        easyUpsertAnnotationAdvisor.setOrder(Ordered.LOWEST_PRECEDENCE);
        return easyUpsertAnnotationAdvisor;
    }

    /**
     * 注解直接操作切换方式+入库
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public QuickEasyUpsertAnnotationAdvisor quickEasyUpsertAnnotationAdvisor(AbstractDynamicEasyUpsert abstractDynamicEasyUpsert) {
        QuickEasyUpsertAnnotationAdvisor advisor = new QuickEasyUpsertAnnotationAdvisor(abstractDynamicEasyUpsert);
        advisor.setOrder(Ordered.LOWEST_PRECEDENCE);
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
