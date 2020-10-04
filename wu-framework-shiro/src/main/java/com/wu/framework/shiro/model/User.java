package com.wu.framework.shiro.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author wjw
 */
//@Entity
@Data
public class User extends AbstractUserDetails {

    @Id
    protected String username;

    protected String password;

    @Transient
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
