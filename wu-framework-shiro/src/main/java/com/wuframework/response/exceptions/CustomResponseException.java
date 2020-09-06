package com.wuframework.response.exceptions;

import com.wuframework.response.ResultCode;

/**
 * @ Description   :  自定义返回异常
 * @ Author        :  wujiawei
 * @ CreateDate    :  2020/3/23 0023 11:20
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2020/3/23 0023 11:20
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 */


public class CustomResponseException extends RuntimeException implements ResultCode {
    public CustomResponseException(String message) {
        super(message);
    }

    public CustomResponseException(Throwable cause) {
        super(cause);
    }
    public CustomResponseException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code=resultCode.getCode();
    }

    private Integer code;
    /**
     * 错误编码
     *
     * @return
     */
    @Override
    public Integer getCode() {
        return this.code;
    }
}
