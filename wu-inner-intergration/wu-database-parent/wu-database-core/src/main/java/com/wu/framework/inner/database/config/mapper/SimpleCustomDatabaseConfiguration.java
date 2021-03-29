package com.wu.framework.inner.database.config.mapper;

import com.wu.framework.inner.database.config.ICustomDatabaseConfiguration;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 数据库连接配置
 * @date : 2020/6/25 下午10:57
 */
//@Order(methodName = Ordered.HIGHEST_PRECEDENCE)
@Data
@Configurable(value = "simpleCustomDatabaseConfiguration", autowire = Autowire.BY_NAME)
@ConfigurationProperties(prefix = "spring.wu.database")
@Configuration(value = "simpleCustomDatabaseConfiguration")
@ConditionalOnProperty(prefix = "spring.wu.dynamic.database", value = "enable", havingValue = "false", matchIfMissing = true)
public class SimpleCustomDatabaseConfiguration implements ICustomDatabaseConfiguration, InitializingBean {

    private final Log log = LogFactory.getLog(SimpleCustomDatabaseConfiguration.class);
    private Class driver;
    private String url;
    private String username;
    private String password;
    /**
     * 表模式
     */
    private DDLAuto ddlAuto = DDLAuto.NONE;


    @Override
    public void afterPropertiesSet() throws Exception {
    }


}
