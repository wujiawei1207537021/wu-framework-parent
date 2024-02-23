package com.wu.framework.authorization.login;

import com.wu.framework.authorization.domain.LoginUserBO;
import com.wu.framework.authorization.model.User;
import com.wu.framework.authorization.model.UserDetails;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.util.DigestUtils;

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
        return lazyOperation.executeSQLForBean(String.format("select * from sys_user where user_name='%s'", userName), User.class);
    }

    /**
     * 创建用户
     *
     * @param loginUserBO@return
     * @author Jiawei Wu
     * @date 2021/1/6 8:37 下午
     **/
    @Override
    public void createUser(LoginUserBO loginUserBO) {
        String md5Password = DigestUtils.md5DigestAsHex(loginUserBO.getPassword().getBytes());
        loginUserBO.setPassword(md5Password);
        lazyOperation.smartUpsert(loginUserBO);
    }

    @Override
    public UserDetails loadUserById(String userId) {
        return lazyOperation.executeSQLForBean(String.format("select * from sys_user where id='%s'", userId), User.class);
    }
}
