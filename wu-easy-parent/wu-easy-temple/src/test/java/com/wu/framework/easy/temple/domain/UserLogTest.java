package com.wu.framework.easy.temple.domain;


import com.wu.framework.easy.temple.service.impl.UserLogServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@RunWith(SpringRunner.class)
@SpringBootTest
public  class UserLogTest {

    @Test
    public void insert(){
        UserLog userLog=new UserLog();
        userLog.setType("2");
        userLog.insert();
    }

    @Resource
    private UserLogServiceImpl userLogService;

    @Test
    public void save(){
        UserBo userLog=new UserBo();
        userLog.setType("2");
        UserLog userLog1=new UserLog();
        BeanUtils.copyProperties(userLog,userLog1);
        userLogService.save(userLog1);
    }

}