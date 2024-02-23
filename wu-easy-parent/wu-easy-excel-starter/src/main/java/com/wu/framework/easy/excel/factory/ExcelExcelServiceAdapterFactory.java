package com.wu.framework.easy.excel.factory;

import com.wu.framework.easy.excel.adapter.ExcelExcelServiceAdapter;
import com.wu.framework.easy.excel.processor.BeanEasyExcelProcessor;

import java.util.List;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/08/03 11:14
 */
public class ExcelExcelServiceAdapterFactory {

    /**
     * 创建Excel适配器
     *
     * @return
     */
    public static ExcelExcelServiceAdapter excelExcelServiceAdapter() {
        BeanEasyExcelProcessor beanEasyExcelProcessor = new BeanEasyExcelProcessor();
        return new ExcelExcelServiceAdapter(List.of(beanEasyExcelProcessor));
    }
}
