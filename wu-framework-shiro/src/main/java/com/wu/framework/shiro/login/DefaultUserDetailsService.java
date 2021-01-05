package com.wu.framework.shiro.login;

import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.shiro.model.User;
import com.wu.framework.shiro.model.UserDetails;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

/**
 * @ Description : 当前框架默认实现方法 @ Author : wujiawei @ CreateDate : 2019/12/17 0017 11:46 @ UpdateUser
 * : wujiawei @ UpdateDate : 2019/12/17 0017 11:46 @ UpdateRemark : 修改内容 @ Version : 1.0
 */
@ConditionalOnMissingBean(UserDetailsService.class)
public class DefaultUserDetailsService implements UserDetailsService {

 private final LazyOperation lazyOperation;

    public DefaultUserDetailsService(LazyOperation lazyOperation) {
        this.lazyOperation = lazyOperation;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        User user = lazyOperation.executeSQLForBean(String.format("select * from sys_user su where su.username=?", userName),User.class);
        return user;
    }
}
