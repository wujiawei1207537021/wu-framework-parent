package com.wu.framework.inner.swagger.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * 下游实现并自定义一
 */

public class DefaultSwaggerConfig {

    @Bean
    public Docket api() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
//        ticketPar.name(shiroProperties.getTokenName()).description("user token")
//                .modelRef(new ModelRef("string")).parameterType("header")
//                .required(false).build();
        pars.add(ticketPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //api路径
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
//                .globalOperationParameters(pars)
                .apiInfo(apiInfo());
//               接口文档 忽略参数DefaultSysUserDetails
//                .ignoredParameterTypes(DefaultSysUserDetails.class);
    }

    private ApiInfo apiInfo() {
        //构建api文档详细函数信息
        return new ApiInfoBuilder()
                //页面标题
                .title("API文档")
                //作者信息
                .contact(new Contact("yuntsoft", "http://www.yuntsoft.com", ""))
                //api文档描述信息
                .description("apidocs")
                //版本信息
//                .version(this.applicationProperties.getVersion())
                .build();
    }
}
