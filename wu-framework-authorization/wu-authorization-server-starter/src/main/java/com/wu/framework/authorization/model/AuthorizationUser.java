package com.wu.framework.authorization.model;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import lombok.Data;

import java.util.List;

/**
 * @author wjw
 */
@Data
@LazyTable(tableName = "sys_user")
public class AuthorizationUser extends AbstractUserDetails {


    @LazyTableFieldUnique
    protected String username;

    protected String password;

    protected List permissionList;

    private Long id;

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
