package com.wu.framework.shiro.model;

import java.io.Serializable;
import java.util.List;

/**
 * @ Description : 用户详细信息 @ Author : wujiawei @ CreateDate : 2019/12/13 0013 15:28 @ UpdateUser :
 * wujiawei @ UpdateDate : 2019/12/13 0013 15:28 @ UpdateRemark : 修改内容 @ Version : 1.0
 */
public interface UserDetails extends Serializable {

    /**
     * 获取密码
     *
     * @return 密码
     */
    String getPassword();

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    String getUsername();

    //    boolean isAccountNonExpired();
    //
    //    boolean isAccountNonLocked();
    //
    //    boolean isCredentialsNonExpired();
    //
    //    boolean isEnabled();

    /**
     * 用户权限
     *
     * @return
     */
    List getPermissionList();

    /**
     * 用户角色
     *
     * @return
     */
    List<String> getRoleSignList();

    /**
     * 签名string
     * @return
     */
    default String tosin(){
        return "UserDetails(username="+this.getUsername()+",passWord="+this.getPassword()+")";
    }


}
