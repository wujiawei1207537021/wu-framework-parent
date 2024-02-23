package com.wu.framework.inner.lazy.database.expand.database.persistence.factory.config;

import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.config.enums.DDLAuto;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.persistence.util.ScanClassUtil;
import com.wu.framework.inner.lazy.stereotype.LazyScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 *
 * LazyBeanPostProcessor
 * DDL 操作
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/2 6:23 下午
 */
public class LazyBeanPostProcessor implements BeanPostProcessor, Ordered {
    private final LazyOperation operation;
    private final LazyOperationConfig operationConfig;

    public LazyBeanPostProcessor(LazyOperation operation, LazyOperationConfig operationConfig) {
        this.operation = operation;
        this.operationConfig = operationConfig;
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

        if (AnnotatedElementUtils.hasAnnotation(bean.getClass(), LazyScan.class)) {
            LazyScan lazyScan = AnnotatedElementUtils.findMergedAnnotation(bean.getClass(), LazyScan.class);
            final String[] scanBasePackages = lazyScan.scanBasePackages();
            final Class<?>[] scanBasePackageClasses = lazyScan.scanBasePackageClasses();
            if (operationConfig.getDdlAuto().equals(DDLAuto.CREATE)) {

            }
            // 含有包路径
            if (!ObjectUtils.isEmpty(scanBasePackages)) {
                for (String scanBasePackage : scanBasePackages) {
                    final List<Class> classes = ScanClassUtil.scanClass(scanBasePackage, null);
                    for (Class clazz : classes) {
                        if (DDLAuto.CREATE.equals(operationConfig.getDdlAuto())) {
                            operation.createTable(clazz);
                        } else if (DDLAuto.UPDATE.equals(operationConfig.getDdlAuto())) {
                            operation.updateTable(clazz);
                        } else if (DDLAuto.PERFECT.equals(operationConfig.getDdlAuto())) {
                            operation.perfect(clazz);
                        }

                    }
                }
            }
            // 包含 包路径的class
            if (!ObjectUtils.isEmpty(scanBasePackageClasses)) {
                for (Class<?> scanBasePackageClass : scanBasePackageClasses) {
                    final List<Class> classes = ScanClassUtil.scanClass(scanBasePackageClass.getPackage().getName(), null);
                    for (Class clazz : classes) {
                        if (DDLAuto.CREATE.equals(operationConfig.getDdlAuto())) {
                            operation.createTable(clazz);
                        } else if (DDLAuto.UPDATE.equals(operationConfig.getDdlAuto())) {
                            operation.updateTable(clazz);
                        } else if (DDLAuto.PERFECT.equals(operationConfig.getDdlAuto())) {
                            operation.perfect(clazz);
                        }
                    }
                }
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
}
