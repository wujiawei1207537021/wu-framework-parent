package com.wu.framework.shiro.model;

import lombok.Data;
import java.util.List;

/**
 * @author wjw
 */
//@Entity
@Data
public class User extends AbstractUserDetails {


    protected String username;

    protected String password;

    protected List permissionList;

//    protected List<String> roleSignList;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
