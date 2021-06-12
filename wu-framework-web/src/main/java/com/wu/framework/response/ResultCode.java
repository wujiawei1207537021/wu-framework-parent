package com.wu.framework.response;

/**
 * @ Description : 返回编码 @ Author : wujiawei @ CreateDate : 2019/12/21 0021 15:53 @ UpdateUser :
 * wujiawei @ UpdateDate : 2019/12/21 0021 15:53 @ UpdateRemark : 修改内容 @ Version : 1.0
 */
public interface ResultCode {

    /**
     * 错误编码
     *
     * @return
     */
    Integer getCode();

    /**
     * 错误信息
     *
     * @return
     */
    String getMessage();
}
