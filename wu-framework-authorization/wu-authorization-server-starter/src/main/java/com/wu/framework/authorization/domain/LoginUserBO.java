package com.wu.framework.authorization.domain;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ Description : 登录参数 @ Author : wujiawei @ CreateDate : 2019/12/12 0012 9:03 @ UpdateUser :
 * wujiawei @ UpdateDate : 2019/12/12 0012 9:03 @ UpdateRemark : 修改内容 @ Version : 1.0
 */
@Accessors(chain = true)
@Data
@LazyTable(tableName = "sys_user", perfectTable = true)
public class LoginUserBO {

    @LazyTableFieldId
    private Long id;
    /**
     * 登录用户
     */
    @LazyTableFieldUnique
    protected String username;

    /**
     * 登录密码
     */
    protected String password;

    /**
     * 登录类型
     */
    @LazyTableFieldUnique
    private String scope = "web";
}
