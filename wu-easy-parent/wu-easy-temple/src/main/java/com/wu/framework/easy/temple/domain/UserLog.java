package com.wu.framework.easy.temple.domain;

import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import com.wu.framework.easy.upsert.autoconfigure.EasySmart;
import com.wu.framework.easy.upsert.autoconfigure.EasySmartField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2020/9/18 下午11:33
 */
@Data
@EasySmart(perfectTable = true)
public class UserLog implements Serializable {

    @EasySmartField(name = "user_id")
    private Integer userId;

    @EasyExcelFiled(name = "当前时间")
    @EasySmartField(name = "`current_time`")
    private LocalDateTime currentTime;

    @EasyExcelFiled(name = "内容")
    @EasySmartField(name = "`content`")
    private String content;

    @EasyExcelFiled(name = "是否成功")
    @EasySmartField(name = "is_succeed")
    private boolean isSucceed;

    private String type;


    public static List<UserLog> createUserLogList(Integer size) {
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
}
