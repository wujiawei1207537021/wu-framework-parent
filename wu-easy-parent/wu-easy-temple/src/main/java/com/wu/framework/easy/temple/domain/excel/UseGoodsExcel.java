package com.wu.framework.easy.temple.domain.excel;

import com.alibaba.fastjson.annotation.JSONField;
import com.wu.framework.easy.excel.stereotype.EasyExcelBean;
import com.wu.framework.easy.excel.stereotype.EasyExcelFiled;
import com.wu.framework.easy.upsert.autoconfigure.EasySmartField;
import com.wu.framework.inner.layer.util.DataTransformUntil;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * description 商品信息
 *
 * @author 吴佳伟
 * @date 2023/07/02 11:39
 */
@Data
public class UseGoodsExcel {

    @EasyExcelBean()
    UseGoodsPriceExcel useGoodsPriceExcel = DataTransformUntil.simulationBean(UseGoodsPriceExcel.class);
    @EasyExcelFiled(name = "原生注解-商品ID")
    @JSONField(name = "JSONField注解-商品ID")
    private Integer goodsId;
    @EasySmartField(name = "`current_time`")
    @EasyExcelFiled(name = "原生注解-商品创建时间")
    @JSONField(name = "JSONField注解-商品创建时间")
    private LocalDateTime currentTime;
    @EasySmartField(name = "`orderByDesc`")
    @EasyExcelFiled(name = "原生注解-商品描述", fieldMerge = EasyExcelFiled.EasyExcelFieldMerge.VERTICAL)
    @JSONField(name = "JSONField注解-商品描述")
    private String desc;
    @EasyExcelFiled(name = "原生注解-商品类型", fieldMerge = EasyExcelFiled.EasyExcelFieldMerge.VERTICAL)
    @JSONField(name = "JSONField注解-商品类型")
    private String type;

    @Data
    public static final class UseGoodsPriceExcel {

        @EasyExcelFiled(name = "原生注解-商品价格")
        @JSONField(name = "JSONField注解-商品价格")
        private Double price;

        @EasyExcelFiled(name = "原生注解-商品价格(单位)")
        @JSONField(name = "JSONField注解-商品价格（单位）")
        private String unit;
    }
}
