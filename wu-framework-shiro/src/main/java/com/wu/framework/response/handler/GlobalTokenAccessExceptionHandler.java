package com.wu.framework.response.handler;


import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.framework.response.enmus.DefaultResultCode;
import com.wu.framework.shiro.exceptions.ShiroException;
import com.wu.framework.shiro.exceptions.UnauthorizedException;
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
