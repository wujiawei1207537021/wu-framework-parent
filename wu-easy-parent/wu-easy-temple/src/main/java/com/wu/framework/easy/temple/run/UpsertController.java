package com.wu.framework.easy.temple.run;

import com.wu.framework.easy.stereotype.upsert.dynamic.QuickEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
import com.wu.framework.easy.stereotype.web.EasyController;
import com.wu.framework.easy.temple.domain.UserLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2020/11/7 下午5:57
 */
@EasyController("/upsert")
public class UpsertController {

    @Autowired
    private DataSource dataSource;

    @QuickEasyUpsert(type = EasyUpsertType.MySQL)
    @GetMapping()
    public List<UserLog> upsert(Integer size) {
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
