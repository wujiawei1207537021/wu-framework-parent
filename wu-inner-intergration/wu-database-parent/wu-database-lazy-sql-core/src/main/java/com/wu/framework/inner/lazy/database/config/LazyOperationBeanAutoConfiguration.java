package com.wu.framework.inner.lazy.database.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Role;

import javax.sql.DataSource;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/6/26 01:57
 */
@Slf4j
@ConditionalOnBean(value = DataSource.class)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyOperationBeanAutoConfiguration {


}

