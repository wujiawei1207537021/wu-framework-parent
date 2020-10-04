package com.wu.framework.shiro.endpoint;

import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.shiro.annotation.AccessLimit;
import com.wu.framework.shiro.domain.LoginUserBO;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@Api(tags = "endpoint test")
//@CustomController("/endpoint/test")
public class EndpointTest {

    @AccessLimit(seconds = 30, maxCount = 3, needLogin = false, checkAccessParam = true, paramName = "username")
    @ApiOperation("demo1")
    @PostMapping("/demo1")
    public Result demo1(String username) {
        System.out.println("当前接口 限制 30s内 访问3次到达上线");
        System.out.println("当前接口 不需要登录");
        System.out.println("当前接口 访问限制方式 访问成功");
        System.out.println("当前接口 针对入参数 username： " + username + "进行限制");
        return ResultFactory.successOf();
    }

    @AccessLimit(seconds = 30, maxCount = 3, needLogin = false, requestSuccessLimit = false, checkAccessParam = true, paramType = LoginUserBO.class, paramName = "username")
    @ApiOperation("demo2")
    @PostMapping("/demo2")
    public Result demo2(@RequestBody LoginUserBO loginUserBO) {
        System.out.println("当前接口 限制 30s内 访问3次到达上线");
        System.out.println("当前接口 不需要登录");
        System.out.println("当前接口 访问限制方式 发生错误");
        System.out.println("当前接口 针对入参数 username： " + loginUserBO.getUsername() + "进行限制");
        return ResultFactory.successOf();
    }

    @AccessLimit(seconds = 30, maxCount = 3)
    @ApiOperation("demo3")
    @PostMapping("/demo3")
    public Result demo3() {
        System.out.println("当前接口 限制 30s内 访问3次到达上线");
        System.out.println("当前接口 需要登录");
        System.out.println("当前接口 访问限制方式 请求成功");
        System.out.println("当前接口 不针对入参数 进行限制");
        return ResultFactory.successOf();
    }
}
