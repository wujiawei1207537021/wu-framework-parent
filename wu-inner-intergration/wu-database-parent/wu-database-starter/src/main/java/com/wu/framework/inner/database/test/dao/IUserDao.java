package com.wu.framework.inner.database.test.dao;

import com.wu.framework.inner.database.stereotype.Select;
import com.wu.framework.inner.database.test.pojo.DataBaseUser;

import java.util.List;


public interface IUserDao {
    //@Select("select * from user")
    List<DataBaseUser> findAll();

    @Select("select id,username from user")
    List<DataBaseUser> selectAll();
}
