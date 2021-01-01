package com.wu.framework.easy.temple.run;

import com.wu.framework.easy.stereotype.upsert.component.IUpsert;
import com.wu.framework.easy.stereotype.upsert.converter.EasyAnnotationConverter;
import com.wu.framework.easy.stereotype.upsert.dynamic.EasyUpsertDS;
import com.wu.framework.easy.stereotype.upsert.dynamic.QuickEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.entity.EasyHashMap;
import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
import com.wu.framework.easy.stereotype.web.EasyController;
import com.wu.framework.easy.temple.domain.DynGpsVehRun;
import com.wu.framework.easy.temple.domain.UseExcel;
import com.wu.framework.easy.temple.domain.UserLog;
import com.wu.framework.easy.temple.domain.bo.ExtractBo;
import com.wu.framework.easy.temple.domain.bo.MoreExtractBo;
import com.wu.framework.easy.temple.service.RunService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jia wei Wu
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


    @ApiOperation(tags = "快速插入数据", value = "多数据")
    @GetMapping("/more")
    public void moreUpsert(@RequestParam(required = false, defaultValue = "100") Integer limit) {
        for (int i = 1; i <= limit; i++) {
            System.out.println("第:" + i + "次");
            upsert(100);
        }
    }

    @EasyUpsertDS(type = EasyUpsertType.MySQL)
    @ApiOperation(tags = "快速插入数据", value = "入DB")
    @GetMapping()
    public List<UserLog> upsert(@RequestParam(required = false, defaultValue = "100") Integer size) {
        List<UserLog> userLogList = new ArrayList<>();
        size=size==null?1000:size;
        for (int i = 0; i <= size; i++) {
            UserLog userLog = new UserLog();
            userLog.setCurrentTime(LocalDateTime.now());
            userLog.setContent("创建时间:" + userLog.getCurrentTime());
            userLog.setUserId(i + 1);
            userLogList.add(userLog);
        }
        iUpsert.upsert(userLogList, userLogList, new UserLog());
        return userLogList;
    }

    @EasyUpsertDS(type = EasyUpsertType.MySQL)
    @ApiOperation(tags = "快速插入数据", value = "service 实现类操作数据插入")
    @GetMapping("/size")
    public void upsertSize(@RequestParam(required = false, defaultValue = "100") Integer size) {
        runService.run(size);
    }


    /**
     * description 大数据分区测试
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2020/12/7 下午6:32
     */
    @EasyUpsertDS(type = EasyUpsertType.ES)
    @ApiOperation(tags = "快速插入数据", value = "操作数据入ES")
    @GetMapping("/bigDataPartitionTest")
    public void bigDataPartitionTest(@RequestParam(required = false, defaultValue = "100") Integer size) {
        List<DynGpsVehRun> dynGpsVehRunList = new ArrayList<>();
        DynGpsVehRun dynGpsVehRun = new DynGpsVehRun();
        dynGpsVehRun.setRunId("闽E06F57_1403_net_car");
        dynGpsVehRun.setPlateNum("闽E06F57");
        dynGpsVehRun.setGpsTimestamp(1605231662000L);
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
        for (int i = 0; i < size; i++) {
            dynGpsVehRunList.add(dynGpsVehRun);
        }
        iUpsert.upsert(dynGpsVehRunList);
    }


    @QuickEasyUpsert(type = EasyUpsertType.MySQL)
    @ApiOperation(tags = "快速插入数据", value = "复杂数据DB")
    @GetMapping("/complexData")
    public ExtractBo complexData() {
        UserLog userLog = new UserLog();
        userLog.setCurrentTime(LocalDateTime.now());
        userLog.setContent("创建时间:" + userLog.getCurrentTime());
        userLog.setUserId(1);

        UseExcel useExcel = new UseExcel();
        useExcel.setCurrentTime(LocalDateTime.now());
        useExcel.setDesc("默认方式导出数据");
        useExcel.setExcelId(2);
        useExcel.setType("默认方式双注解导出");

        ExtractBo extractBo = new ExtractBo();
        extractBo.setUserLog(userLog);
        extractBo.setUseExcel(useExcel);

        EasyAnnotationConverter.extractData(null, extractBo);
        return extractBo;
    }

    @QuickEasyUpsert(type = EasyUpsertType.MySQL)
    @ApiOperation(tags = "快速插入数据", value = "复杂数据DB")
    @GetMapping("/moreExtractBo")
    public MoreExtractBo moreExtractBo() {
        MoreExtractBo moreExtractBo = new MoreExtractBo();
        ExtractBo extractBo = complexData();
        moreExtractBo.setExtractBo(extractBo);
        moreExtractBo.setUseExcel(extractBo.getUseExcel());
        moreExtractBo.setUserLog(extractBo.getUserLog());
        moreExtractBo.setUserLogList(runService.run(1000));
        return moreExtractBo;
    }

    @QuickEasyUpsert(type = EasyUpsertType.MySQL)
    @ApiOperation(tags = "快速插入数据", value = "复杂数据EasyHashMap")
    @GetMapping("/easyHashMap")
    public List<EasyHashMap> easyHashMap(
            @RequestParam(required = false, defaultValue = "1000") Integer size) {
        List<EasyHashMap> easyHashMapList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            EasyHashMap easyHashMap = new EasyHashMap("uniqueLabel");
            easyHashMap.put("第一个字段", "第一个字段");
            easyHashMap.put("第二个字段", "第二个字段");
            easyHashMap.put("第三个字段", "第三个字段");
            easyHashMap.put("第四个字段", "第四个字段");
            easyHashMapList.add(easyHashMap);
        }
        return easyHashMapList;
    }


}
