package com.wu.framework.easy.excel.processor;

/**
 * description 常用对象Excel 处理器
 *
 * @author 吴佳伟
 * @date 2023/06/29 20:38
 */
public class BeanEasyExcelProcessor extends AbstractNormalMapEasyExcelProcessor implements EasyExcelProcessor {

    /**
     * 是否支持excel 处理
     *
     * @param excelBean
     * @return
     */
    @Override
    public boolean support(Object excelBean) {
        return excelBean != null;
    }
}
