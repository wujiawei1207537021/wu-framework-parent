package com.wu.framework.shiro.model;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.LazyTable;
import lombok.Data;

import java.util.List;

/**
 * @author wjw
 */
@Data
@LazyTable(tableName = "sys_user")
public class User extends AbstractUserDetails {


    protected String username;

    protected String password;

    protected List permissionList;

    private String id;

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
