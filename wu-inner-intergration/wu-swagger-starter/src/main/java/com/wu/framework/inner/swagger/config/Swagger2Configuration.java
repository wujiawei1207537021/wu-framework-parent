package com.wu.framework.inner.swagger.config;


import com.google.common.collect.Lists;
import com.wu.framework.inner.swagger.config.pro.WuSwagger2Properties;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableSwagger2
@ConditionalOnMissingBean(Docket.class)
public class Swagger2Configuration {


    @Autowired
    private WuSwagger2Properties wuSwagger2Properties;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return wuSwagger2Properties;
//        return new ApiInfoBuilder()
//                .title(title)
//                .description(description)
//                .contact(new Contact(contactName, contactUrl, contactEmail))
//                .version(version)
//                .build();
    }

    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeys = new ArrayList<>();
        apiKeys.add(new ApiKey("用户名称", "username", "header"));
        apiKeys.add(new ApiKey("用户Id", "userId", "header"));
        apiKeys.add(new ApiKey("用户是否超管", "isAdmin", "header"));
        apiKeys.add(new ApiKey("用户角色id列表", "roleIds", "header"));
        return apiKeys;
    }

    private List<SecurityContext> securityContexts() {
        return Lists.newArrayList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .build()
        );
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("用户名称", authorizationScopes),
                new SecurityReference("用户Id", authorizationScopes),
                new SecurityReference("用户是否超管", authorizationScopes),
                new SecurityReference("用户角色id列表", authorizationScopes)
        );
    }
}
