package com.wuframework.response.handler;


import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.response.enmus.DefaultResultCode;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalTokenAccessExceptionHandler(令牌授权异常)
 */
@RestControllerAdvice
public class GlobalTokenAccessExceptionHandler {

    /**
     * 未授权
     *
     * @param exception
     * @return
     */
    @ExceptionHandler({UnauthorizedException.class})
    public Result unauthorizedException(UnauthorizedException exception) {
        exception.printStackTrace();
        return ResultFactory.of(DefaultResultCode.TOKEN_INVALID, exception.getMessage());
    }

    /**
     * 权限错误
     *
     * @param exception
     * @return
     */
    @ExceptionHandler({ShiroException.class})
    public Result shiroException(ShiroException exception) {
        exception.printStackTrace();
        return ResultFactory.of(DefaultResultCode.TOKEN_INVALIDATION, exception.getMessage());
    }

}
