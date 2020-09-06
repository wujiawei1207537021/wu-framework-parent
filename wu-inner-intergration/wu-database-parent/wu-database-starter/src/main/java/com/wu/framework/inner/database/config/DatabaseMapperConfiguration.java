package com.wu.framework.inner.database.config;

import com.wu.framework.inner.database.domain.CustomRepository;
import com.wu.framework.inner.database.handler.RepositoryProxyFactory;
import com.wu.framework.inner.database.proxy.RepositoryProxy;
import com.wu.framework.inner.database.util.ScanXmlPathUtil;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : 吴佳伟
 * @version : 1.0
 * @describe: 扫描xml
 * @date : 2020/9/5 上午2:29
 */
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Data
@Configuration
@ConfigurationProperties(prefix = "wu.database.mapper")
public class DatabaseMapperConfiguration implements ICustomDatabaseScanBean, InitializingBean {


    private final Log log = LogFactory.getLog(DatabaseMapperConfiguration.class);

    private List<Class> scanBeanClasses = new ArrayList<>();


    /**
     * 扫描xml文件路径 TODO 当前指定文件名
     */
    public List<String> scanXmlPath;


    public static Map<String, CustomRepository> customRepositoryMap;


    // 使用 DefaultListableBeanFactory 注入bean
    private final DefaultListableBeanFactory defaultListableBeanFactory;

    // 代理类
    private final RepositoryProxy repositoryProxy;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 获取所有的xml并封装成对象
        customRepositoryMap = ScanXmlPathUtil.getCustomRepository(scanXmlPath);

        log.info("init interface of CustomDatabaseMapperConfiguration ");
        scanBeanClasses.addAll(ScanXmlPathUtil.getCustomScanBeanClass(scanXmlPath));
        for (Class clazz : scanBeanClasses) {
//            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
//            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
//            definition.getConstructorArgumentValues().addGenericArgumentValue(clazz);
//            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
//            definition.setBeanClass(RepositoryProxyFactory.class);
//            defaultListableBeanFactory.registerBeanDefinition(clazz.getSimpleName(), definition);
            RepositoryProxyFactory repositoryProxyFactory = new RepositoryProxyFactory(clazz, repositoryProxy);
            defaultListableBeanFactory.registerSingleton(clazz.getSimpleName(), repositoryProxyFactory);
            log.info("init interface of mapper:" + clazz.getSimpleName());
        }
    }
}
