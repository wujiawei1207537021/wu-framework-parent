package com.wu.framework.database.generator.controller;

import com.wu.framework.easy.excel.stereotype.EasyExcel;
import com.wu.framework.easy.stereotype.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * description 导出结果
 *
 * @author Jiawei Wu
 * @date 2020/12/29 上午11:01
 */
@EasyController("/export")
public class ExportResultController {

    private final LazyOperation lazyOperation;


    public ExportResultController(LazyOperation lazyOperation) {
        this.lazyOperation = lazyOperation;
    }


    /**
     * description 导出查询结果
     *
     * @param
     * @return
     * @exception/throws
     * @author Jiawei Wu
     * @date 2020/12/29 上午11:04
     */
    @EasyExcel
    @GetMapping("/easy")
    public List<Map> easyExport(@RequestParam String sql) {
        List<Map> mapList = lazyOperation.executeSQL(sql, Map.class);
        return mapList;
    }


}
