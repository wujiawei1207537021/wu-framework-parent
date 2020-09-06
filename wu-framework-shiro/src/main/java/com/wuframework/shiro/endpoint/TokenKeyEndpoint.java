package com.wuframework.shiro.endpoint;


import com.wuframework.response.Result;
import com.wuframework.shiro.login.LoginService;
import com.wuframework.shiro.annotation.AccessLimit;
import com.wuframework.shiro.domain.AccessToken;
import com.wuframework.shiro.domain.LoginUserBO;
import com.wu.framework.inner.swagger.annotation.CustomController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

@Api(tags = "获取令牌接口")
@CustomController("/token")
public class TokenKeyEndpoint {

    @Resource
    LoginService loginService;

    /**
     * 获取令牌
     *
     * @param loginUserBO
     * @return
     */
    @AccessLimit(seconds = 30,maxCount = 3,needLogin = false,requestSuccessLimit = false,checkAccessParam = true,paramType = LoginUserBO.class,paramName = "username")
    @ApiOperation("获取令牌")
    @PostMapping("/access_token")
    public Result<AccessToken> accessToken(@RequestBody LoginUserBO loginUserBO) {
        return loginService.accessToken(loginUserBO);
    }


    @ApiOperation("测试解析令牌")
    @PostMapping("/user/{accessToken}")
    public <T> T user(@PathVariable String accessToken) {
        return loginService.user(accessToken);
    }

    @AccessLimit(seconds = 30,maxCount = 1,needLogin = false,checkAccessParam = true,paramName = "accessToken")
    @ApiOperation("移出令牌")
    @DeleteMapping("/{accessToken}")
    public Result removeAccessToken(@PathVariable String accessToken) {
        return loginService.removeAccessToken(accessToken);
    }
}
