package com.wu.framework.easy.excel.processor;

import com.wu.framework.easy.excel.endpoint.EasyExcelPoint;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/06/29 20:42
 */
public interface EasyExcelProcessor {

    /**
     * 是否支持excel 处理
     *
     * @param excelBean
     * @return
     */
    boolean support(Object excelBean);


    /**
     * 导出excel
     *
     * @param excelBean      excel 对象
     * @param easyExcelPoint Excel 的配置信息
     * @return
     */
    byte[] exportExcel(Object excelBean, EasyExcelPoint easyExcelPoint);

    /**
     * 导出excel到文件
     *
     * @param excelBean        excel 对象
     * @param easyExcelPoint   Excel 的配置信息
     * @param fileOutputStream 文件输出流
     * @return
     */
    void exportExcel(Object excelBean, EasyExcelPoint easyExcelPoint, FileOutputStream fileOutputStream) throws IOException;

    /**
     * 导入excel
     *
     * @param excel
     * @return
     */
    <T> List<T> importExcel(Object excel);

    /**
     * 优先级 数字越大优先级越高
     *
     * @return
     */
    int order();
}
