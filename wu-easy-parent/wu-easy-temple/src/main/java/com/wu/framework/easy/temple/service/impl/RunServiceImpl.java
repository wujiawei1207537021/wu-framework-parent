package com.wu.framework.easy.temple.service.impl;

import com.wu.framework.easy.stereotype.upsert.dynamic.QuickEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
import com.wu.framework.easy.temple.domain.UpsertBinary;
import com.wu.framework.easy.temple.domain.UserLog;
import com.wu.framework.easy.temple.service.RunService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/9/18 下午11:34
 */

@QuickEasyUpsert(type = EasyUpsertType.ES)
@Service
public class RunServiceImpl implements RunService {

    @Override
    public List<UserLog> run(Integer size) {
        List<UserLog> userLogList = new ArrayList<>();
        size = size == null ? 10000 : size;
        for (int i = 0; i < size; i++) {
            UserLog userLog = new UserLog();
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

    @Override
    public List binary(Integer size) {
        List<UpsertBinary> upsertBinaryList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            upsertBinaryList.add(new UpsertBinary());
        }
        return upsertBinaryList;
    }

    public static void main(String[] args) {
        List<UserLog> userLogList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            UserLog userLog = new UserLog();
            userLog.setCurrentTime(LocalDateTime.now());
            userLog.setContent("创建时间:" + userLog.getCurrentTime());
            userLog.setUserId(i);
            userLogList.add(userLog);
        }
        userLogList.forEach(userLog -> userLog.setUserId(1));
        System.out.println(userLogList);

    }
}
