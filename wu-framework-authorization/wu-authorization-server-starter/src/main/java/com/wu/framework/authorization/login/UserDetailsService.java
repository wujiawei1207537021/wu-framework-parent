package com.wu.framework.authorization.login;

import com.wu.framework.authorization.model.UserDetails;
import com.wu.framework.authorization.domain.LoginUserBO;

/**
 * @ Description : 使用当前框架所需要实现的方法
 * @ Author : wujiawei
 * @ CreateDate : 2019/12/17 0017 11:45
 * @ UpdateUser : wujiawei
 * @ UpdateDate : 2020/02/17 0017 11:45
 * @ UpdateRemark : 二次开发实现当前接口需要在AuthorizationServerEndpointsConfigurer中注入UserDetails 实现类
 * @ Version : 1.1
 */
public interface UserDetailsService {
    /**
     * 通过用户账号获取用户信息
     *
     * @param userName
     * @return
     */
    UserDetails loadUserByUsername(String userName);

    /**
     * 创建用户
     *
     * @param
     * @return
     * @author Jiawei Wu
     * @date 2021/1/6 8:37 下午
     **/
    void createUser(LoginUserBO loginUserBO);


    UserDetails loadUserById(String userId);
}
