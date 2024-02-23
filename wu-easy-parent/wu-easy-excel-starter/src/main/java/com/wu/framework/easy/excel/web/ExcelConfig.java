package com.wu.framework.easy.excel.web;

import com.wu.framework.easy.excel.adapter.ExcelExcelServiceAdapter;
import com.wu.framework.easy.excel.processor.BeanEasyExcelProcessor;
import com.wu.framework.easy.excel.processor.EasyExcelProcessor;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * description 未完成
 *
 * @author Jia wei Wu
 * @date 2021/4/23 上午10:10
 */
public class ExcelConfig {


//    /**
//     * 实体转换成excel 处理器
//     * @return
//     */
//    @Bean
//    public AbstractNormalBeanEasyExcelProcessor normalBeanEasyExcelProcessor(){
//        return new AbstractNormalBeanEasyExcelProcessor();
//    }

//    /**
//     * map 转出成Excel处理器
//     * @return
//     */
//    @Bean
//    public AbstractNormalMapEasyExcelProcessor normalMapEasyExcelProcessor(){
//        return new AbstractNormalMapEasyExcelProcessor();
//    }

    /**
     * bean 转出成Excel处理器
     *
     * @return
     */
    @Bean
    public BeanEasyExcelProcessor beanEasyExcelProcessor() {
        return new BeanEasyExcelProcessor();
    }

    @Bean
    public ExcelExcelServiceAdapter excelExcelServiceAdapter(List<EasyExcelProcessor> easyExcelProcessorList) {
        return new ExcelExcelServiceAdapter(easyExcelProcessorList);
    }

}
