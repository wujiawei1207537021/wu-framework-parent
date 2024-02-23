package com.wu.framework.easy.temple.controller;

import com.wu.framework.easy.temple.domain.UpsertBinary;
import com.wu.framework.easy.temple.domain.UserLog;
import com.wu.framework.easy.temple.domain.bo.ExtractBo;
import com.wu.framework.easy.temple.domain.bo.MoreExtractBo;
import com.wu.framework.easy.temple.domain.excel.UseUserExcel;
import com.wu.framework.easy.upsert.EasyUpsertMySQL;
import com.wu.framework.easy.upsert.autoconfigure.dynamic.QuickEasyUpsert;
import com.wu.framework.easy.upsert.autoconfigure.enums.EasyUpsertType;
import com.wu.framework.easy.upsert.core.dynamic.IUpsert;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2020/11/7 下午5:57
 */
@EasyController("/upsert/MySQL")
public class UpsertMySQLController {


    private final IUpsert iUpsert;

    public UpsertMySQLController(IUpsert iUpsert) {
        this.iUpsert = iUpsert;
    }

    /**
     * description IUpsert操作数据入DB
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/4/15 上午9:50
     */
    @EasyUpsertMySQL(name = "dataSourceH2")
    @ApiOperation(tags = "MySQL快速插入数据", value = "IUpsert操作数据入DB")
    @GetMapping()
    public void upsert(@RequestParam(required = false, defaultValue = "100") Integer size) {
        List<UserLog> userLogList = createUserLog(size);
        iUpsert.upsert(userLogList, userLogList, new UserLog());
    }


    /**
     * description 使用注解实现数据插入
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/4/19 上午10:11
     */
    @QuickEasyUpsert(type = EasyUpsertType.MySQL)
    @ApiOperation(tags = "MySQL快速插入数据", value = "使用注解实现数据插入")
    @GetMapping("/quick/size")
    public List<UserLog> quickUpsertSize(@RequestParam(required = false, defaultValue = "100") Integer size) {
        return createUserLog(size);
    }

    @QuickEasyUpsert(type = EasyUpsertType.MySQL)
    @ApiOperation(tags = "MySQL快速插入数据", value = "复杂数据EasyHashMap")
    @GetMapping("/quick/easyHashMap")
    public List<EasyHashMap> quickEasyHashMap(@RequestParam(required = false, defaultValue = "1000") Integer size) {
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


    @QuickEasyUpsert(type = EasyUpsertType.MySQL)
    @ApiOperation(tags = "MySQL快速插入数据", value = "复杂数据DB")
    @GetMapping("/quick/complexData")
    public ExtractBo quickComplexData() {
        UserLog userLog = new UserLog();
        userLog.setCurrentTime(LocalDateTime.now());
        userLog.setContent("创建时间:" + userLog.getCurrentTime());
        userLog.setUserId(1);

        UseUserExcel useUserExcel = new UseUserExcel();
        useUserExcel.setCurrentTime(LocalDateTime.now());
        useUserExcel.setDesc("默认方式导出数据");
        useUserExcel.setExcelId(2);
        useUserExcel.setType("默认方式双注解导出");

        ExtractBo extractBo = new ExtractBo();
        extractBo.setUserLog(userLog);
        extractBo.setUseUserExcel(useUserExcel);

//        extractData(null, extractBo);
        return extractBo;
    }

    @QuickEasyUpsert(type = EasyUpsertType.MySQL)
    @ApiOperation(tags = "MySQL快速插入数据", value = "复杂数据DB")
    @GetMapping("/quick/moreExtractBo")
    public MoreExtractBo quickMoreExtractBo() {
        MoreExtractBo moreExtractBo = new MoreExtractBo();
        ExtractBo extractBo = quickComplexData();
        moreExtractBo.setExtractBo(extractBo);
        moreExtractBo.setUseUserExcel(extractBo.getUseUserExcel());
        moreExtractBo.setUserLog(extractBo.getUserLog());
        moreExtractBo.setUserLogList(createUserLog(1000));
        return moreExtractBo;
    }


    /**
     * description quickBinary 或者文件类型数据插入
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/4/19 上午10:11
     */
    @QuickEasyUpsert(type = EasyUpsertType.MySQL)
    @ApiOperation(tags = "MySQL快速插入数据", value = "quickBinary 数据插入")
    @GetMapping("/quick/binary")
    public List<UpsertBinary> quickBinary(@RequestParam(required = false, defaultValue = "1000") Integer size) {
        List<UpsertBinary> upsertBinaryList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            upsertBinaryList.add(new UpsertBinary());
        }
        return upsertBinaryList;
    }


    /**
     * description 创建测试数据
     *
     * @param
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/4/19 上午10:09
     */
    public List<UserLog> createUserLog(Integer size) {
        return UserLog.createUserLogList(size);
    }


}
