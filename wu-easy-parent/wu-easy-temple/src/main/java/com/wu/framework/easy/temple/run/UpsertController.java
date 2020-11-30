package com.wu.framework.easy.temple.run;

import com.wu.framework.easy.stereotype.upsert.component.IUpsert;
import com.wu.framework.easy.stereotype.upsert.dynamic.EasyUpsertDS;
import com.wu.framework.easy.stereotype.web.EasyController;
import com.wu.framework.easy.temple.domain.UserLog;
import com.wu.framework.easy.temple.service.RunService;
import org.springframework.web.bind.annotation.GetMapping;

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


    private final IUpsert iUpsert;
    private final RunService runService;

    public UpsertController(IUpsert iUpsert, RunService runService) {
        this.iUpsert = iUpsert;
        this.runService = runService;
    }


    @GetMapping("/more")
    public void moreUpsert(Integer limit) {
        for (int i = 1; i <= limit; i++) {
            System.out.println("第:"+i+"次");
            upsert(null);
        }
    }

    @GetMapping()
    public List<UserLog> upsert(Integer size) {
        List<UserLog> userLogList = new ArrayList<>();
        size = size == null ? 100000 : size;
        for (int i = 1; i <= size; i++) {
            UserLog userLog = new UserLog();
            userLog.setCurrentTime(LocalDateTime.now());
            userLog.setContent("创建时间:" + userLog.getCurrentTime());
            userLog.setUserId(i);
            userLogList.add(userLog);
        }
        iUpsert.upsert(userLogList,userLogList,new UserLog());
        return userLogList;
    }

    @GetMapping("/size")
    public void upsertSize(Integer size) {
       runService.run(size);
    }

}
