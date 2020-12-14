package com.wu.framework.easy.temple.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import com.wu.framework.easy.stereotype.upsert.EasySmart;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/9/18 下午11:33
 */
@Data
@EasySmart(perfectTable = true)
public class UseExcel {

    @EasyExcelFiled(name = "原生注解-id")
    @JSONField(name = "JSONField注解-id")
    private Integer excelId;

    @EasyExcelFiled(name = "原生注解-当前时间")
    @JSONField(name = "JSONField注解-当前时间")
    private LocalDateTime currentTime;

    @EasyExcelFiled(name = "原生注解-描述")
    @JSONField(name = "JSONField注解-描述")
    private String desc;

    @EasyExcelFiled(name = "原生注解-类型")
    @JSONField(name = "JSONField注解-类型")
    private String type;

    @EasyExcelFiled(name = "原生注解-是否删除")
    @JSONField(name = "JSONField注解-是否删除")
    private boolean isDelete;
}
