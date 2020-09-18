package com.wu.framework.easy.temple.service.impl;

import com.wu.framework.easy.temple.domain.UserLog;
import com.wu.framework.easy.temple.service.RunService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2020/9/18 下午11:34
 */
@Service
public class RunServiceImpl implements RunService {
    @Override
    public List<UserLog> run1() {
        List<UserLog> userLogList=new ArrayList<>();
        for (int i = 0; i <1000 ; i++) {
            UserLog userLog=new UserLog();
            userLog.setUserId(i);
            userLog.setCurrentTime(LocalDateTime.now());
            userLog.setContent("创建时间:"+userLog.getCurrentTime());
            userLogList.add(userLog);
        }
        return userLogList;
    }
}
