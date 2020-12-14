package com.wu.framework.easy.temple.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.wu.framework.easy.excel.stereotype.EasyExcelBean;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * description 复杂Excel导出
 *
 * @author Jia wei Wu
 * @date 2020/10/6 下午9:17
 */
@Data
public class ComplexUseExcel {
    @JSONField(name = "很复杂的外层id")
    private Integer id;

    @JSONField(name = "很复杂的外层当前时间")
    private LocalDateTime currentTime;

    @JSONField(name = "很复杂的外层描述")
    private String desc = "很复杂的外层";

    @JSONField(name = "很复杂的外层类型")
    private String type = "复杂excel导出类型";

    @EasyExcelBean
    private SmartExcel smartExcel;

    @EasyExcelBean
    private List<UseExcel> useExcelList;

//
//    @JSONField(tableName = "例外")
//    private String makeAnException="例外";
}
