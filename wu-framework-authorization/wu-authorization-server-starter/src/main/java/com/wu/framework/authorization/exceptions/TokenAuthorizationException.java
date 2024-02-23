package com.wu.framework.authorization.exceptions;


/**
 * @ Description : 令牌授权异常 @ Author : wujiawei @ CreateDate : 2019/12/17 0017 14:07 @ UpdateUser :
 * wujiawei @ UpdateDate : 2019/12/17 0017 14:07 @ UpdateRemark : 修改内容 @ Version : 1.0
 */
public class TokenAuthorizationException extends AuthorizationException {
    public TokenAuthorizationException(String message) {
        this(message, null);
    }

    public TokenAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
