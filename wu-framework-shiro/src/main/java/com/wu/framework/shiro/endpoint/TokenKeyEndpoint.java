package com.wu.framework.shiro.endpoint;


import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.response.Result;
import com.wu.framework.shiro.annotation.AccessLimit;
import com.wu.framework.shiro.domain.AccessTokenRO;
import com.wu.framework.shiro.domain.LoginUserBO;
import com.wu.framework.shiro.login.ILoginService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

@EasyController("/token")
public class TokenKeyEndpoint {

    @Resource
    ILoginService ILoginService;

    /**
     * 获取令牌
     *
     * @param loginUserBO
     * @return
     */
    @AccessLimit(seconds = 30, maxCount = 3, needLogin = false, requestSuccessLimit = false, checkAccessParam = true, paramType = LoginUserBO.class, paramName = "username")
    @PostMapping("/access_token")
    public Result<AccessTokenRO> accessToken(@RequestBody LoginUserBO loginUserBO) {
        return ILoginService.accessToken(loginUserBO);
    }


    @PostMapping("/user/{accessToken}")
    public <T> T user(@PathVariable String accessToken) {
        return ILoginService.user(accessToken);
    }

    @AccessLimit(seconds = 30, maxCount = 1, needLogin = false, checkAccessParam = true, paramName = "accessToken")
    @DeleteMapping("/{accessToken}")
    public Result removeAccessToken(@PathVariable String accessToken) {
        return ILoginService.removeAccessToken(accessToken);
    }

    /**
     * 创建用户
     *
     * @param
     * @return
     * @author Jiawei Wu
     * @date 2021/1/6 8:33 下午
     **/
    @PostMapping("/create_user")
    public Result<AccessTokenRO> createUser(@RequestBody LoginUserBO loginUserBO) {
        return ILoginService.createUser(loginUserBO);
    }

}
