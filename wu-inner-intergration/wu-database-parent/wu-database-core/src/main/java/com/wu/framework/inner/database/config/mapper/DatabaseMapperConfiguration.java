package com.wu.framework.inner.database.config.mapper;

import com.wu.framework.inner.database.util.ScanXmlPathUtil;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 扫描xml
 * @date : 2020/9/5 上午2:29
 */
@Data
@Configuration
@Configurable(value = "databaseMapperConfiguration", autowire = Autowire.BY_NAME)
@ConfigurationProperties(prefix = "wu.database.mapper", ignoreInvalidFields = true)
public class DatabaseMapperConfiguration implements ICustomDatabaseScanBean, InitializingBean {


    private final Log log = LogFactory.getLog(DatabaseMapperConfiguration.class);

    private List<Class> scanBeanClasses = new ArrayList<>();

    /**
     * 扫描xml文件路径 TODO 当前指定文件名
     */
    private List<String> scanXmlPath;


    @Override
    public void afterPropertiesSet() throws Exception {
        if (null == scanXmlPath) {
            return;
        }
        scanBeanClasses.addAll(ScanXmlPathUtil.getCustomScanBeanClass(scanXmlPath));
        log.info("init interface of ICustomDatabaseScanBean ");
    }
}
