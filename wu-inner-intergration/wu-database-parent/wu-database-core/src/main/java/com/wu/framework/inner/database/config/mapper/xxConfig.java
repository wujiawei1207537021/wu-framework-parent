package com.wu.framework.inner.database.config.mapper;

import com.wu.framework.easy.stereotype.upsert.EasyTable;
import com.wu.framework.inner.database.DDLAutoOperate;
import com.wu.framework.inner.database.EasyDataSourceAdapter;
import com.wu.framework.inner.database.config.ICustomDatabaseConfiguration;
import com.wu.framework.inner.database.converter.SQLConverter;
import com.wu.framework.inner.database.domain.CustomRepository;
import com.wu.framework.inner.database.handler.RepositoryProxyFactory;
import com.wu.framework.inner.database.proxy.RepositoryProxy;
import com.wu.framework.inner.database.stereotype.ScanEntity;
import com.wu.framework.inner.database.util.CustomDataSourceUtil;
import com.wu.framework.inner.database.util.ScanXmlPathUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/9/13 下午8:10
 */
public class xxConfig implements InitializingBean {

    public static Map<String, CustomRepository> customRepositoryMap;
    private final Log log = LogFactory.getLog(xxConfig.class);
    private final DatabaseMapperConfiguration databaseMapperConfiguration;
    private final RepositoryProxy repositoryProxy;
    private final DefaultListableBeanFactory defaultListableBeanFactory;
    private final ICustomDatabaseConfiguration iCustomDatabaseConfiguration;

    public xxConfig(DatabaseMapperConfiguration databaseMapperConfiguration, RepositoryProxy repositoryProxy, DefaultListableBeanFactory defaultListableBeanFactory, ICustomDatabaseConfiguration iCustomDatabaseConfiguration) {
        this.databaseMapperConfiguration = databaseMapperConfiguration;
        this.repositoryProxy = repositoryProxy;
        this.defaultListableBeanFactory = defaultListableBeanFactory;
        this.iCustomDatabaseConfiguration = iCustomDatabaseConfiguration;
    }

    //    TODO
    @Bean
    public DDLAutoOperate ddlAutoOperate() {
        return new DDLAutoOperate(databaseMapperConfiguration);
    }

    @Bean
    public RepositoryProxyFactory repositoryProxyFactory() {
        return new RepositoryProxyFactory(DDLAutoOperate.class, repositoryProxy);
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


        //


        log.info("init simpleCustomDatabaseConfiguration config:" + iCustomDatabaseConfiguration.getDriver().getName());
        if (iCustomDatabaseConfiguration.getDdlAuto().equals(ICustomDatabaseConfiguration.DDLAuto.CREATE)) {
            Connection connection = EasyDataSourceAdapter.createDataSource(iCustomDatabaseConfiguration).getConnection();
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

    @Bean
    public DataSource dataSource(){
        System.out.println("shideya");
        return EasyDataSourceAdapter.createDataSource(iCustomDatabaseConfiguration);
    }
}
