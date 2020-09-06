package com.wuframework.shiro.web.configuration;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * 待完善
 */
@Deprecated
@Configuration
@Import(AuthorizationServerEndpointsConfiguration.TokenKeyEndpointRegistrar.class)
public class AuthorizationServerEndpointsConfiguration {

    @Component
    protected static class TokenKeyEndpointRegistrar implements BeanDefinitionRegistryPostProcessor {

        private BeanDefinitionRegistry registry;

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
                throws BeansException {

            //            String[] names =
            // BeanFactoryUtils.beanNamesForAnnotationIncludingAncestors(beanFactory,
            //                    EnableAuthorizationServer.class);
            //            if (names.length > 0) {
            //                BeanDefinitionBuilder builder =
            // BeanDefinitionBuilder.rootBeanDefinition(TokenKeyEndpoint.class);
            //                builder.addConstructorArgReference(names[0]);
            //                registry.registerBeanDefinition(TokenKeyEndpoint.class.getName(),
            // builder.getBeanDefinition());
            //            }
        }

        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
                throws BeansException {
            this.registry = registry;
        }
    }
}
