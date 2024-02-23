package com.wuframework.system.common.config;

import com.wuframework.shiro.config.pro.ShiroProperties;
import com.wuframework.swagger.config.DefaultSwaggerConfig;
import com.wuframework.system.common.entity.DefaultSysUserDetails;
import com.wuframework.system.common.pro.ApplicationProperties;
import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Xiongxz
 * @Date: 2018/6/9 0009 23:30
 * @Description: SwaggerConfig
 * <p>
 * Swagger注解说明：
 * <p>
 * API 将类标记为Swagger资源
 * ApiImplicitParam 表示API操作中的单个参数
 * ApiImplicitParams 一个允许多个ApiImplicitParam对象列表的包装器
 * ApiModel 提供有关Swagger模型的其他信息
 * ApiModelProperty 添加和操作模型属性的数据
 * ApiOperation 描述一个操作或通常针对特定路径的HTTP方法
 * ApiParam 为操作参数添加额外的元数据
 * ApiResponse 描述操作的可能响应
 * ApiResponses 一个允许多个ApiResponse对象列表的包装器
 * Authorization 声明要在资源或操作上使用的授权方案
 * AuthorizationScope 描述OAuth2授权范围
 */

/**
 * 扫描多个包
 *
 * @ComponentScan(basePackages = {"com.yuntsoft.schoolcanteen.controller"})
 */

public class DefaultSystemSwaggerConfig extends DefaultSwaggerConfig {

    @Resource
    private ApplicationProperties applicationProperties;

    @Resource
    private ShiroProperties shiroProperties;


    @Override
    public Docket api() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name(shiroProperties.getTokenName()).description("user token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        pars.add(ticketPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //api路径
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                .apiInfo(apiInfo())
//               接口文档 忽略参数DefaultSysUserDetails
                .ignoredParameterTypes(DefaultSysUserDetails.class);
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
                .version(this.applicationProperties.getVersion())
                .build();
    }
}
