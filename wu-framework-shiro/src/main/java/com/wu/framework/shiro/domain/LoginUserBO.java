package com.wu.framework.shiro.domain;

import lombok.Data;

/**
 * @ Description : 登录参数 @ Author : wujiawei @ CreateDate : 2019/12/12 0012 9:03 @ UpdateUser :
 * wujiawei @ UpdateDate : 2019/12/12 0012 9:03 @ UpdateRemark : 修改内容 @ Version : 1.0
 */
@Data
public class LoginUserBO {

    /**
     * 登录用户
     */
    protected String username;

    /**
     * 登录密码
     */
    protected String password;

    /**
     * 登录类型
     */
    private String scope = "web";
}
