package com.wu.framework.easy.temple.run;

import com.wu.framework.easy.stereotype.upsert.component.IUpsert;
import com.wu.framework.easy.stereotype.upsert.converter.SQLConverter;
import com.wu.framework.easy.stereotype.upsert.dynamic.EasyUpsertDS;
import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
import com.wu.framework.easy.stereotype.web.EasyController;
import com.wu.framework.easy.temple.domain.DynGpsVehRun;
import com.wu.framework.easy.temple.domain.UserLog;
import com.wu.framework.easy.temple.service.RunService;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
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
            System.out.println("第:" + i + "次");
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
        iUpsert.upsert(userLogList, userLogList, new UserLog());
        return userLogList;
    }

    @GetMapping("/size")
    public void upsertSize(Integer size) {
        runService.run(size);
    }


    /**
     * description 大数据分区测试
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2020/12/7 下午6:32
     */
    @EasyUpsertDS(type = EasyUpsertType.ES)
    @GetMapping("/bigDataPartitionTest")
    public void bigDataPartitionTest(Integer size) {
        List<DynGpsVehRun> dynGpsVehRunList = new ArrayList<>();
        DynGpsVehRun dynGpsVehRun=new DynGpsVehRun();
        dynGpsVehRun.setRunId("闽E06F57_1403_net_car");
        dynGpsVehRun.setPlateNum("闽E06F57");
        dynGpsVehRun.setGpsTimestamp(1605231662000L);
        /**
         * gps_time": "2020-11-13 09:41:02",
         * "location": "",
         * "lng": ,
         * "lat": ,
         * "speed": 23,
         * "direction": 279,
         * "alarm": 0,
         * "industry": "090",
         * "business_type": 9,
         * "mars_lat": 24.51603155461966,
         * "mars_lng": 117.65594955014237,
         * "gps_vdate": "2020-11-13",
         * "gps_vtime": "",
         * "business_scope_code": "1403",
         * "ve2": null,
         * "ve3": null,
         * "altitude": null,
         * "@timestamp@": "2020-11-13T09:38:50.350+0800"
         */
        dynGpsVehRun.setGpsTime("2020-11-13 09:41:02");
        dynGpsVehRun.setLocation("24.519004,117.651156");
        dynGpsVehRun.setLng(117.651156);
        dynGpsVehRun.setLat(24.519004);
        dynGpsVehRun.setSpeed(23.00);
        dynGpsVehRun.setDirection(279L);
        dynGpsVehRun.setAlarm(0L);
        dynGpsVehRun.setIndustry("090");
        dynGpsVehRun.setBusinessType(9);
        dynGpsVehRun.setMarsLat(24.51603155461966);
        dynGpsVehRun.setMarsLng(117.65594955014237);
        dynGpsVehRun.setGpsVdate("2020-11-13");
        dynGpsVehRun.setGpsVtime("09:41:02");
        dynGpsVehRun.setBusinessScopeCode("1403");

        for (int i = 0; i <size ; i++) {
            dynGpsVehRunList.add(dynGpsVehRun);
        }
        iUpsert.upsert(dynGpsVehRunList);
    }



}
