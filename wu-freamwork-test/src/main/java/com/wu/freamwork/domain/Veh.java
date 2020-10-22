package com.wu.freamwork.domain;

import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import com.wu.framework.easy.stereotype.upsert.EasyTableField;
import lombok.Data;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2020/10/19 上午9:00
 */
@Data
public class Veh {

    @EasyExcelFiled(name = "车牌号")
    private String plateNum;

    @EasyExcelFiled(name = "车牌颜色")
    private String plateColor;

    @EasyExcelFiled(name = "车辆类型")
    @EasyTableField(name = "entity_cate")
    private String type;

    @EasyExcelFiled(name = "数据来源")
    private String dataSource;
}
