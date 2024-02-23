package com.wu.framework.easy.temple.domain.excel;

import com.alibaba.fastjson.annotation.JSONField;
import com.wu.framework.easy.excel.stereotype.EasyExcelBean;
import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import com.wu.framework.easy.upsert.autoconfigure.EasySmart;
import com.wu.framework.easy.upsert.autoconfigure.EasySmartField;
import com.wu.framework.inner.layer.util.DataTransformUntil;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : 导出用户信息对象
 * @date : 2020/9/18 下午11:33
 */
@Data
@EasySmart(perfectTable = true)
public class UseUserExcel {

    @EasyExcelFiled(name = "原生注解-id")
    @JSONField(name = "JSONField注解-id")
    private Integer excelId;

    @EasySmartField(name = "`current_time`")
    @EasyExcelFiled(name = "原生注解-当前时间")
    @JSONField(name = "JSONField注解-当前时间")
    private LocalDateTime currentTime;

    @EasySmartField(name = "`orderByDesc`")
    @EasyExcelFiled(name = "原生注解-描述", fieldMerge = EasyExcelFiled.EasyExcelFieldMerge.VERTICAL)
    @JSONField(name = "JSONField注解-描述")
    private String desc;

    @EasyExcelFiled(name = "原生注解-类型", fieldMerge = EasyExcelFiled.EasyExcelFieldMerge.VERTICAL)
    @JSONField(name = "JSONField注解-类型")
    private String type;

    @EasyExcelFiled(name = "原生注解-是否删除")
    @JSONField(name = "JSONField注解-是否删除")
    private boolean isDelete;


    @EasyExcelBean
    private List<UseUserRoleExcel> useUserRoleExcelList = DataTransformUntil.simulationBeanList(UseUserRoleExcel.class, 10);
}
