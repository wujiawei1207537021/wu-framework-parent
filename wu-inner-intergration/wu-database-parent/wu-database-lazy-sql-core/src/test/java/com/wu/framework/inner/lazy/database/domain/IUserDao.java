package com.wu.framework.inner.lazy.database.domain;

import com.wu.framework.inner.lazy.stereotype.Select;

import java.util.List;


public interface IUserDao {
    //@Select("select * from user")
    List<DataBaseUser> findAll();

    @Select("select id,username from user")
    List<DataBaseUser> selectAll();
}
