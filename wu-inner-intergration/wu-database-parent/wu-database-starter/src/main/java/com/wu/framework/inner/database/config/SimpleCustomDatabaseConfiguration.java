package com.wu.framework.inner.database.config;


import com.wu.framework.easy.stereotype.upsert.EasyTable;
import com.wu.framework.inner.database.SimpleCustomDataSource;
import com.wu.framework.inner.database.converter.SQLConverter;
import com.wu.framework.inner.database.stereotype.ScanEntity;
import com.wu.framework.inner.database.util.CustomDataSourceUtil;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;


/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 数据库连接配置
 * @date : 2020/6/25 下午10:57
 */
//@Order(value = Ordered.HIGHEST_PRECEDENCE)
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
        log.info("init simpleCustomDatabaseConfiguration config:" + driver.getName());
        if (ddlAuto.equals(ICustomDatabaseConfiguration.DDLAuto.CREATE)) {
            Connection connection = new SimpleCustomDataSource(this).getConnection();
            Map<String, Object> objectMap = new HashMap<>();
//                    applicationContext.getBeansWithAnnotation(ScanEntity.class);
            List<String> scanEntityPath = new ArrayList<>();
            for (Map.Entry<String, Object> stringObjectEntry : objectMap.entrySet()) {
                ScanEntity scanEntity = AnnotationUtils.getAnnotation(stringObjectEntry.getValue().getClass(), ScanEntity.class);
                if (ObjectUtils.isEmpty(scanEntity.basePackage())) {
                    scanEntityPath.addAll(Arrays.asList(scanEntity.basePackage()));
                }
                if (ObjectUtils.isEmpty(scanEntity.value())) {
                    scanEntityPath.add(scanEntity.value());
                }
            }
            Set<Class> classSet = CustomDataSourceUtil.scanClass(scanEntityPath, EasyTable.class);
            if (ObjectUtils.isEmpty(classSet)) {
                return;
            }
            for (Class aClass : classSet) {
                String createTableSQL = SQLConverter.createTableSQL(aClass);
                PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL);
                preparedStatement.execute();
                preparedStatement.close();
                log.info("init table of class:" + aClass.getSimpleName());
            }
        }

    }


}
