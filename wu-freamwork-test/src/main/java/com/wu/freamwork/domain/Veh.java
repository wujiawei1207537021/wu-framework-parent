package com.wu.freamwork.domain;

import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import com.wu.framework.easy.stereotype.upsert.EasySmartField;
import lombok.Data;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2020/10/19 上午9:00
 */
@Data
public class Veh {

    @EasyExcelFiled(name = "车牌号")
    private String plateNum;

    @EasyExcelFiled(name = "车牌颜色")
    private String plateColor;

    @EasyExcelFiled(name = "车辆类型")
    @EasySmartField(name = "entity_cate")
    private String type;

    @EasyExcelFiled(name = "数据来源")
    private String dataSource;
}
