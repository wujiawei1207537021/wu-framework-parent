package com.wu.framework.authorization.endpoint;


import com.wu.framework.authorization.annotation.AccessLimit;
import com.wu.framework.authorization.domain.AccessTokenRO;
import com.wu.framework.authorization.domain.LoginUserBO;
import com.wu.framework.authorization.login.ILoginService;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Tag(name = "授权服务提供者")
@EasyController("/token")
public class TokenKeyEndpoint {

    private final ILoginService ILoginService;

    public TokenKeyEndpoint(com.wu.framework.authorization.login.ILoginService iLoginService) {
        ILoginService = iLoginService;
    }

    /**
     * 获取令牌
     *
     * @param loginUserBO 登陆用户信息
     * @return Result 返回数据
     */
    @ApiOperation(value = "获取令牌")
    @AccessLimit(seconds = 30, maxCount = 3, needLogin = false, requestSuccessLimit = false, checkAccessParam = true, paramType = LoginUserBO.class, paramName = "username")
    @PostMapping("/access_token")
    public Result<AccessTokenRO> accessToken(@RequestBody LoginUserBO loginUserBO) {
        return ILoginService.accessToken(loginUserBO);
    }


    @ApiOperation(value = "解析令牌")
    @RequestMapping(value = "/user/{accessToken}",method = {RequestMethod.GET,RequestMethod.POST})
    public <T> Result<T> user(@PathVariable String accessToken) {
        T user = ILoginService.user(accessToken);
        return ResultFactory.successOf(user);
    }

    @ApiOperation(value = "移除令牌")
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
    @ApiOperation(value = "创建用户")
    @PostMapping("/create_user")
    public Result<AccessTokenRO> createUser(@RequestBody LoginUserBO loginUserBO) {
        return ILoginService.createUser(loginUserBO);
    }

}
