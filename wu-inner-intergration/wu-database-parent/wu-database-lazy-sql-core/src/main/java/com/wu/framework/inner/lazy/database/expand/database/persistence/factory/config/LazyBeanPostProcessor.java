package com.wu.framework.inner.lazy.database.expand.database.persistence.factory.config;

import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.enums.DDLAuto;
import com.wu.framework.inner.lazy.config.enums.LazyDataSourceType;
import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.config.prop.SchemaScript;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.persistence.util.ScanClassUtil;
import com.wu.framework.inner.lazy.source.SqlSourceClass;
import com.wu.framework.inner.lazy.source.adapter.SourceFactory;
import com.wu.framework.inner.lazy.stereotype.LazyScan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LazyBeanPostProcessor
 * DDL 操作
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/2 6:23 下午
 */
@Slf4j
@ConditionalOnProperty(prefix = "spring.datasource", value = "url")
public class LazyBeanPostProcessor implements BeanPostProcessor, Ordered, InitializingBean {
    private final LazyOperation operation;
    private final LazyOperationConfig operationConfig;
    private final LazyDataSourceProperties lazyDataSourceProperties;

    public LazyBeanPostProcessor(LazyOperation operation, LazyOperationConfig operationConfig, LazyDataSourceProperties lazyDataSourceProperties) {
        this.operation = operation;
        this.operationConfig = operationConfig;
        this.lazyDataSourceProperties = lazyDataSourceProperties;
    }

    /**
     * Apply this {@code BeanPostProcessor} to the given new bean instance <i>before</i> any bean
     * initialization callbacks (like InitializingBean's {@code afterPropertiesSet}
     * or a custom init-method). The bean will already be populated with property values.
     * The returned bean instance may be a wrapper around the original.
     * <p>The default implementation returns the given {@code bean} as-is.
     *
     * @param bean     the new bean instance
     * @param beanName the name of the bean
     * @return the bean instance to use, either the original or a wrapped one;
     * if {@code null}, no subsequent BeanPostProcessors will be invoked
     * @throws BeansException in case of errors
     * @see InitializingBean#afterPropertiesSet
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        LazyOperationConfig.DdlConfigure ddlConfigure = operationConfig.getDdlConfigure();
        String ddlPerfectCommitment = ddlConfigure.getDdlPerfectCommitment();
        DDLAuto ddlAuto = ddlConfigure.getDdlAuto();


        SourceFactory.defaultLazyDataSourceType=lazyDataSourceProperties.getLazyDataSourceType();
        List<Class> tableClass = new ArrayList<>();
        if (AnnotatedElementUtils.hasAnnotation(bean.getClass(), LazyScan.class)) {
            LazyScan lazyScan = AnnotatedElementUtils.findMergedAnnotation(bean.getClass(), LazyScan.class);
            Class<?>[] scanClass = lazyScan.scanClass();
            tableClass.addAll(Arrays.asList(scanClass));
            final String[] scanBasePackages = lazyScan.scanBasePackages();
            final Class<?>[] scanBasePackageClasses = lazyScan.scanBasePackageClasses();
            // 含有包路径
            if (!ObjectUtils.isEmpty(scanBasePackages)) {
                for (String scanBasePackage : scanBasePackages) {
                    final List<Class> classes = ScanClassUtil.scanClass(scanBasePackage, null);
                    tableClass.addAll(classes);
                }
            }
            // 包含 包路径的class
            if (!ObjectUtils.isEmpty(scanBasePackageClasses)) {
                for (Class<?> scanBasePackageClass : scanBasePackageClasses) {
                    final List<Class> classes = ScanClassUtil.scanClass(scanBasePackageClass.getPackage().getName(), null);
                    tableClass.addAll(classes);
                }
            }
        }

        for (Class clazz : tableClass) {
            try {
                if (DDLAuto.CREATE.equals(ddlAuto)) {
                    operation.createTable(clazz);
                } else if (DDLAuto.UPDATE.equals(ddlAuto)) {
                    operation.updateTable(clazz);
                } else if (DDLAuto.PERFECT.equals(ddlAuto)) {
                    if (ObjectUtils.isEmpty(ddlPerfectCommitment)) {
                        log.warn("您没有填写DDL 完善表承诺内容，当前模式将不更改任何数据库，请配置如 spring.lazy.ddl-configure.ddl-perfect-commitment: 我承诺完善表结构，支持表字段删除操作");
                    } else {
                        operation.perfect(clazz);
                    }
                } else if (DDLAuto.NONE.equals(ddlAuto)) {
                    SqlSourceClass sqlSourceClass = SqlSourceClass.getInstance(clazz);
                    sqlSourceClass.getLazyTableEndpoint();
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("无法通过class: " + clazz.getName() + " 映射表");
            }
        }


        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    /**
     * Apply this {@code BeanPostProcessor} to the given new bean instance <i>after</i> any bean
     * initialization callbacks (like InitializingBean's {@code afterPropertiesSet}
     * or a custom init-method). The bean will already be populated with property values.
     * The returned bean instance may be a wrapper around the original.
     * <p>In case of a FactoryBean, this callback will be invoked for both the FactoryBean
     * instance and the objects created by the FactoryBean (as of Spring 2.0). The
     * post-processor can decide whether to apply to either the FactoryBean or created
     * objects or both through corresponding {@code bean instanceof FactoryBean} checks.
     * <p>This callback will also be invoked after a short-circuiting triggered by a
     * {@link InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation} method,
     * in contrast to all other {@code BeanPostProcessor} callbacks.
     * <p>The default implementation returns the given {@code bean} as-is.
     *
     * @param bean     the new bean instance
     * @param beanName the name of the bean
     * @return the bean instance to use, either the original or a wrapped one;
     * if {@code null}, no subsequent BeanPostProcessors will be invoked
     * @throws BeansException in case of errors
     * @see InitializingBean#afterPropertiesSet
     * @see FactoryBean
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<Resource> resources = new ArrayList<>();
        SchemaScript schemaScript = operationConfig.getDdlConfigure().getSchemaScript();

        // resource 下对应的初始化sql
        for (String classPath : schemaScript.getClassPathResources()) {
            ClassPathResource classPathResource = new ClassPathResource(classPath);
            resources.add(classPathResource);
        }
        // 对应当前服务器下sql文件地址
        for (String fileSystem : schemaScript.getFileSystemResources()) {
            FileSystemResource classPathResource = new FileSystemResource(fileSystem);
            resources.add(classPathResource);
        }
        // 对应网络服务器下sql文件地址
        for (String url : schemaScript.getUrlResources()) {
            UrlResource classPathResource = new UrlResource(url);
            resources.add(classPathResource);
        }
        if (resources.size() != 0) {
            try {
                operation.scriptRunner(resources.toArray(new Resource[0]));
            } catch (Exception e) {
                log.error("执行sql 文件失败{}", e.getMessage());
                e.printStackTrace();
            }
        }

    }
}
