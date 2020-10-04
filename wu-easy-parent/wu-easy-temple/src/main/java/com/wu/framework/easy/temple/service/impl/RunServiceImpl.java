package com.wu.framework.easy.temple.service.impl;

import com.wu.framework.easy.stereotype.upsert.dynamic.QuickEasyUpsert;
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



    @QuickEasyUpsert
    @Override
    public List<UserLog> run(Integer size) {
        List<UserLog> userLogList = new ArrayList<>();
        size = size == null ? 10000 : size;
        UserLog userLog = new UserLog();
        for (int i = 0; i < size; i++) {
            userLog.setCurrentTime(LocalDateTime.now());
            userLog.setContent("创建时间:" + userLog.getCurrentTime());
            userLog.setUserId(i);
            userLogList.add(userLog);
        }
        return userLogList;
    }

    @Override
    public List<UserLog> run1() {
        return run(null);
    }

    @Override
    public void run2(Integer size) {
        run(size);
    }
}
