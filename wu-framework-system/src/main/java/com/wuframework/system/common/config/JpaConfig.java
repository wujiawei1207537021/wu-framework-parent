package com.wuframework.system.common.config;

import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class JpaConfig {


//    @Bean
//    public JpaProperties jpaProperties(){
//        JpaProperties jpaProperties=new JpaProperties();
//        jpaProperties.setGenerateDdl(true);
//        return jpaProperties;
//    }

    @Bean
    public HibernateProperties hibernateProperties() {
        HibernateProperties hibernateProperties = new HibernateProperties();
        hibernateProperties.setDdlAuto("update");
        return hibernateProperties;
    }

}
