package com.wu.framework.doc.platform.controller;

import com.wu.framework.easy.excel.stereotype.EasyExcel;
import com.wu.framework.inner.layer.web.EasyController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/07/04 23:07
 */
@Tag(name = "DOC-Excel提供者")
@EasyController("/doc/excel")
public class ExcelProvider {
    /**
     * description:json生成Excel
     *
     * @param excelBean excel json 文件
     * @return
     * @author 吴佳伟
     * @date: 4.7.23 23:09
     */
    @EasyExcel(fileName = "json生成Excel")
    @ApiOperation("json生成Excel")
    @PostMapping("/json2Excel")
    public Object json2Excel(@RequestBody Object excelBean) {
        return excelBean;
    }


}
