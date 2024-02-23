package com.wu.framework.easy.excel.adapter;

import com.wu.framework.easy.excel.endpoint.EasyExcelPoint;
import com.wu.framework.easy.excel.processor.EasyExcelProcessor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * description excel 导出适配器
 *
 * @author 吴佳伟
 * @date 2023/06/29 20:40
 */
public class ExcelExcelServiceAdapter {

    private final List<EasyExcelProcessor> easyExcelProcessors;

    public ExcelExcelServiceAdapter(List<EasyExcelProcessor> easyExcelProcessors) {
        this.easyExcelProcessors = easyExcelProcessors;
    }


    /**
     * 导出excel
     *
     * @param excelBean
     * @param easyExcelPoint
     * @return
     */
    public byte[] exportExcel(Object excelBean, EasyExcelPoint easyExcelPoint) {
        List<EasyExcelProcessor> easyExcelProcessorList = easyExcelProcessors.stream().sorted(Comparator.comparing(EasyExcelProcessor::order).reversed()).collect(Collectors.toList());

        for (EasyExcelProcessor easyExcelProcessor : easyExcelProcessorList) {
            if (easyExcelProcessor.support(excelBean)) {
                return easyExcelProcessor.exportExcel(excelBean, easyExcelPoint);
            }
        }

        throw new RuntimeException("无法导出Excel");
    }


    /**
     * 导出excel 到本地文件
     *
     * @param excelBean
     * @param easyExcelPoint
     * @return
     */
    public void exportExcel(Object excelBean, EasyExcelPoint easyExcelPoint, FileOutputStream fileOutputStream) throws IOException {
        List<EasyExcelProcessor> easyExcelProcessorList = easyExcelProcessors.stream().sorted(Comparator.comparing(EasyExcelProcessor::order).reversed()).toList();

        for (EasyExcelProcessor easyExcelProcessor : easyExcelProcessorList) {
            if (easyExcelProcessor.support(excelBean)) {
                easyExcelProcessor.exportExcel(excelBean, easyExcelPoint, fileOutputStream);
                return;
            }
        }

        throw new RuntimeException("无法导出Excel");
    }

    /**
     * 导入excel
     *
     * @param excel
     * @return
     */
    public <T> List<T> importExcel(Object excel) {
        List<EasyExcelProcessor> easyExcelProcessorList = easyExcelProcessors.stream().sorted(Comparator.comparing(EasyExcelProcessor::order).reversed()).toList();

        for (EasyExcelProcessor easyExcelProcessor : easyExcelProcessorList) {
            if (easyExcelProcessor.support(excel)) {
                return easyExcelProcessor.importExcel(excel);
            }
        }

        throw new RuntimeException("无法导入Excel");
    }

    ;


}
