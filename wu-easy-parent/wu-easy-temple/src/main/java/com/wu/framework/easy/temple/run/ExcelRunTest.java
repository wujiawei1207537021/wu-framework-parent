package com.wu.framework.easy.temple.run;


import com.alibaba.fastjson.annotation.JSONField;
import com.wu.framework.easy.excel.stereotype.EasyExcel;
import com.wu.framework.easy.stereotype.web.EasyController;
import com.wu.framework.easy.temple.EasyExcelTemp;
import com.wu.framework.easy.temple.domain.ComplexUseExcel;
import com.wu.framework.easy.temple.domain.SmartExcel;
import com.wu.framework.easy.temple.domain.UseExcel;
import com.wu.framework.easy.temple.service.RunService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/9/18 下午11:32
 */
@Api(tags = "导出注解测试")
@EasyController("/excel")
public class ExcelRunTest {

    private final RunService runService;

    public ExcelRunTest(RunService runService) {
        this.runService = runService;
    }


    @EasyExcel(fileName = "导出数据")
    @ApiOperation(tags = "导出注解测试", value = "使用原生注解有效")
    @GetMapping("/run/{size}")
    public List<UseExcel> run(@PathVariable Integer size) {
        List<UseExcel> useExcelList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            UseExcel useExcel = new UseExcel();
            useExcel.setCurrentTime(LocalDateTime.now());
            useExcel.setDesc("默认方式导出数据");
            useExcel.setId(i);
            useExcel.setType("默认方式双注解导出");
            useExcelList.add(useExcel);
        }
        return useExcelList;
    }

    @EasyExcel(fileName = "导出数据多个工作簿",multipleSheet = true)
    @ApiOperation(tags = "导出注解测试", value = "使用原生注解有效多个工作簿")
    @GetMapping("/run1/{size}")
    public List<UseExcel> run1(@PathVariable Integer size) {
        return run(size);
    }

    @EasyExcel(fileName = "非原生注解导出数据", fieldColumnAnnotation = JSONField.class, fieldColumnAnnotationAttribute = "name", multipleSheet = true, limit = 1000, sheetShowContext = EasyExcel.SheetShowContext.TEXT)
    @ApiOperation(tags = "导出注解测试", value = "非原生注解导出数据")
    @GetMapping("/run2/{size}")
    public List<UseExcel> run2(@PathVariable Integer size) {
        List<UseExcel> useExcelList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            UseExcel useExcel = new UseExcel();
            useExcel.setCurrentTime(LocalDateTime.now());
            useExcel.setDesc("自定义字段注解方式导出数据");
            useExcel.setId(i);
            useExcel.setType("自定义字段注解导出");
            useExcelList.add(useExcel);
        }
        return useExcelList;
    }

    @EasyExcel(fileName = "导出所有字段", useAnnotation = false)
    @ApiOperation(tags = "导出注解测试", value = "导出所有字段")
    @GetMapping("/run3/{size}")
    public List<UseExcel> run3(@PathVariable Integer size) {
        List<UseExcel> useExcelList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            UseExcel useExcel = new UseExcel();
            useExcel.setCurrentTime(LocalDateTime.now());
            useExcel.setDesc("不使用注解导出所有字段");
            useExcel.setId(i);
            useExcel.setType("导出所有字段");
            useExcelList.add(useExcel);
        }
        return useExcelList;
    }

    @EasyExcelTemp(fileName = "自定义注解导出")
    @ApiOperation(tags = "导出注解测试", value = "自定义注解导出")
    @GetMapping("/run4/{size}")
    public List<UseExcel> run4(@PathVariable Integer size) {
        List<UseExcel> useExcelList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            UseExcel useExcel = new UseExcel();
            useExcel.setCurrentTime(LocalDateTime.now());
            useExcel.setDesc("自定义注解导出");
            useExcel.setId(i);
            useExcel.setType("自定义注解导出");
            useExcelList.add(useExcel);
        }
        return useExcelList;
    }

    @EasyExcel(fileName = "复杂数据导出", isComplicated = true, fieldColumnAnnotation = JSONField.class, sheetShowContext = EasyExcel.SheetShowContext.TEXT)
    @ApiOperation(tags = "导出注解测试", value = "复杂数据导出")
    @GetMapping("/run5/{size}")
    public List<ComplexUseExcel> run5(@PathVariable Integer size) {

        List<UseExcel> useExcelList = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            UseExcel useExcel = new UseExcel();
            useExcel.setCurrentTime(LocalDateTime.now());
            useExcel.setDesc("复杂数据导出内部数据");
            useExcel.setId(i);
            useExcel.setType("复杂数据导出内部数据");
            useExcelList.add(useExcel);
        }

        List<ComplexUseExcel> complexUseExcelList = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            ComplexUseExcel complexUseExcel = new ComplexUseExcel();
            complexUseExcel.setCurrentTime(LocalDateTime.now());
            complexUseExcel.setDesc("复杂数据导出需要合并单元格");
            complexUseExcel.setId(i);
            complexUseExcel.setType("复杂数据导出需要合并单元格");
            complexUseExcel.setUseExcelList(useExcelList);
            complexUseExcel.setSmartExcel(new SmartExcel());
            complexUseExcelList.add(complexUseExcel);
        }
        return complexUseExcelList;
    }
}
