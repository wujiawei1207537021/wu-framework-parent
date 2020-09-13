package com.wu.framework.inner.database.config;

import com.wu.framework.inner.database.domain.CustomRepository;
import com.wu.framework.inner.database.handler.RepositoryProxyFactory;
import com.wu.framework.inner.database.proxy.RepositoryProxy;
import com.wu.framework.inner.database.util.ScanXmlPathUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;

import java.util.Map;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2020/9/13 下午8:10
 */

public class xxConfig implements InitializingBean {

    private final Log log = LogFactory.getLog(xxConfig.class);


    private final DatabaseMapperConfiguration databaseMapperConfiguration;
    private final RepositoryProxy repositoryProxy;
    private final DefaultListableBeanFactory defaultListableBeanFactory;

    public static Map<String, CustomRepository> customRepositoryMap;

    public xxConfig(DatabaseMapperConfiguration databaseMapperConfiguration, RepositoryProxy repositoryProxy, DefaultListableBeanFactory defaultListableBeanFactory) {
        this.databaseMapperConfiguration = databaseMapperConfiguration;
        this.repositoryProxy = repositoryProxy;
        this.defaultListableBeanFactory = defaultListableBeanFactory;
    }

//    TODO
    @Bean
    public RepositoryProxyFactory repositoryProxyFactory() {
        return new RepositoryProxyFactory(databaseMapperConfiguration.getScanBeanClasses().get(0), repositoryProxy);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("init bean of xxConfig ");
        // 获取所有的xml并封装成对象
        customRepositoryMap = ScanXmlPathUtil.getCustomRepository(databaseMapperConfiguration.getScanXmlPath());
        for (Class clazz : databaseMapperConfiguration.getScanBeanClasses()) {
            //            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
//            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
//            definition.getConstructorArgumentValues().addGenericArgumentValue(clazz);
//            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
//            definition.setBeanClass(RepositoryProxyFactory.class);
//            defaultListableBeanFactory.registerBeanDefinition(clazz.getSimpleName(), definition);
            RepositoryProxyFactory repositoryProxyFactory = new RepositoryProxyFactory(clazz, repositoryProxy);
            defaultListableBeanFactory.registerSingleton(clazz.getSimpleName(), repositoryProxyFactory);
            log.info("init interface of mapper:" + clazz.getName());
        }
    }
}
