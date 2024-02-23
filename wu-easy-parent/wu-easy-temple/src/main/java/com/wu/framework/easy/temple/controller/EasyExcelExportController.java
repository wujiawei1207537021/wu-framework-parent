package com.wu.framework.easy.temple.controller;


import com.alibaba.fastjson.annotation.JSONField;
import com.wu.framework.easy.excel.stereotype.EasyExcel;
import com.wu.framework.easy.excel.toolkit.DynamicEasyExcelContextHolder;
import com.wu.framework.easy.excel.util.EasyExcelUtil;
import com.wu.framework.easy.temple.EasyExcelTemp;
import com.wu.framework.easy.temple.domain.ComplexUseExcel;
import com.wu.framework.easy.temple.domain.SmartExcel;
import com.wu.framework.easy.temple.domain.excel.*;
import com.wu.framework.inner.layer.util.DataTransformUntil;
import com.wu.framework.inner.layer.web.EasyController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2020/9/18 下午11:32
 */
@Tag(name = "导出注解测试")
@EasyController("/easy/excel/export")
public class EasyExcelExportController {


    @EasyExcel(fileName = "导出数据")
    @ApiOperation(tags = "导出用户角色（一对多）", value = "使用原生注解有效")
    @GetMapping("/run/{size}")
    public List<UseUserExcel> run(@PathVariable Integer size) {
        List<UseUserExcel> useUserExcelList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            UseUserExcel useUserExcel = new UseUserExcel();
            useUserExcel.setCurrentTime(LocalDateTime.now());
            useUserExcel.setDesc("默认方式导出数据");
            useUserExcel.setExcelId(i);
            useUserExcel.setType("默认方式双注解导出");
            useUserExcelList.add(useUserExcel);
        }
        return useUserExcelList;
    }

    @EasyExcel(fileName = "导出数据(删除特殊字段desc)")
    @ApiOperation(tags = "导出用户角色（一对多）", value = "使用原生注解有效导出数据(删除特殊字段)")
    @GetMapping("/run/delete/{size}")
    public List<UseUserExcel> runDelete(@PathVariable Integer size) {
        List<UseUserExcel> useUserExcelList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            UseUserExcel useUserExcel = new UseUserExcel();
            useUserExcel.setCurrentTime(LocalDateTime.now());
            useUserExcel.setDesc("默认方式导出数据");
            useUserExcel.setExcelId(i);
            useUserExcel.setType("默认方式双注解导出");
            useUserExcelList.add(useUserExcel);
        }
        DynamicEasyExcelContextHolder.push(Arrays.asList("desc"));
        return useUserExcelList;
    }

    @EasyExcel(fileName = "导出数据多个工作簿", multipleSheet = true)
    @ApiOperation(tags = "导出用户角色（一对多）", value = "使用原生注解有效多个工作簿")
    @GetMapping("/run1/{size}")
    public List<UseUserExcel> run1(@PathVariable Integer size) {
        return run(size);
    }

    @EasyExcel(fileName = "非原生注解导出数据", fieldColumnAnnotation = JSONField.class, fieldColumnAnnotationAttribute = "name", multipleSheet = true, limit = 1000, sheetShowContext = EasyExcel.SheetShowContext.TEXT)
    @ApiOperation(tags = "导出用户角色（一对多）", value = "非原生注解导出数据")
    @GetMapping("/run2/{size}")
    public List<UseUserExcel> run2(@PathVariable Integer size) {
        List<UseUserExcel> useUserExcelList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            UseUserExcel useUserExcel = new UseUserExcel();
            useUserExcel.setCurrentTime(LocalDateTime.now());
            useUserExcel.setDesc("自定义字段注解方式导出数据");
            useUserExcel.setExcelId(i);
            useUserExcel.setType("自定义字段注解导出");
            useUserExcelList.add(useUserExcel);
        }
        return useUserExcelList;
    }

    @EasyExcel(fileName = "导出所有字段", useAnnotation = false)
    @ApiOperation(tags = "导出用户角色（一对多）", value = "导出所有字段")
    @GetMapping("/run3/{size}")
    public List<UseUserExcel> run3(@PathVariable Integer size) {
        List<UseUserExcel> useUserExcelList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            UseUserExcel useUserExcel = new UseUserExcel();
            useUserExcel.setCurrentTime(LocalDateTime.now());
            useUserExcel.setDesc("不使用注解导出所有字段");
            useUserExcel.setExcelId(i);
            useUserExcel.setType("导出所有字段");
            useUserExcelList.add(useUserExcel);
        }
        return useUserExcelList;
    }

    @EasyExcelTemp(fileName = "自定义注解导出")
    @ApiOperation(tags = "导出用户角色（一对多）", value = "自定义注解导出")
    @GetMapping("/run4/{size}")
    public List<UseUserExcel> run4(@PathVariable Integer size) {
        List<UseUserExcel> useUserExcelList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            UseUserExcel useUserExcel = new UseUserExcel();
            useUserExcel.setCurrentTime(LocalDateTime.now());
            useUserExcel.setDesc("自定义注解导出");
            useUserExcel.setExcelId(i);
            useUserExcel.setType("自定义注解导出");
            useUserExcelList.add(useUserExcel);
        }
        return useUserExcelList;
    }

    @EasyExcel(fileName = "复杂数据导出", isComplicated = true, fieldColumnAnnotation = JSONField.class, sheetShowContext = EasyExcel.SheetShowContext.TEXT)
    @ApiOperation(tags = "导出用户角色（一对多）", value = "复杂数据导出")
    @GetMapping("/run5/{size}")
    public List<ComplexUseExcel> run5(@PathVariable Integer size) {
        List<UseUserExcel> useUserExcelList = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            UseUserExcel useUserExcel = new UseUserExcel();
            useUserExcel.setCurrentTime(LocalDateTime.now());
            useUserExcel.setDesc("复杂数据导出内部数据");
            useUserExcel.setExcelId(i);
            useUserExcel.setType("复杂数据导出内部数据");
            useUserExcelList.add(useUserExcel);
        }

        List<ComplexUseExcel> complexUseExcelList = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            ComplexUseExcel complexUseExcel = new ComplexUseExcel();
            complexUseExcel.setCurrentTime(LocalDateTime.now());
            complexUseExcel.setDesc("复杂数据导出需要合并单元格");
            complexUseExcel.setId(i);
            complexUseExcel.setType("复杂数据导出需要合并单元格");
            complexUseExcel.setUseUserExcelList(useUserExcelList);
            complexUseExcel.setSmartExcel(new SmartExcel());
            complexUseExcelList.add(complexUseExcel);
        }
        return complexUseExcelList;
    }


    @EasyExcel(fileName = "导出数据")
    @ApiOperation(tags = "导出用户角色（一对多）", value = "使用原生注解有效(自定义一导出文件名称)")
    @GetMapping("/run6/{size}")
    public List<UseUserExcel> run6(@PathVariable Integer size, @RequestParam String fileName) {
        EasyExcelUtil.modifyCurrentMethodEasyExcelFileName(this.getClass(), fileName);
        return run(size);
    }


    @EasyExcel(fileName = "导出Excel模版-A")
    @ApiOperation(tags = "导出用户角色（一对多）", value = "导出Excel模版-A")
    @GetMapping(value = "/export/easyExport")
    public List<UseUserExcel> easyExport() {
        List<UseUserExcel> useUserExcels = DataTransformUntil.simulationBeanList(UseUserExcel.class, 10);
        for (UseUserExcel useUserExcel : useUserExcels) {
            useUserExcel.setUseUserRoleExcelList(DataTransformUntil.simulationBeanList(UseUserRoleExcel.class, 10));
        }
        return useUserExcels;
    }


    @EasyExcel(fileName = "导出Excel模版-B")
    @ApiOperation(tags = "导出商品含价格", value = "导出Excel模版-B")
    @GetMapping(value = "/export/exportGoods")
    public List<UseGoodsExcel> exportGoods() {
        List<UseGoodsExcel> useGoodsExcelList = DataTransformUntil.simulationBeanList(UseGoodsExcel.class, 5);
        for (UseGoodsExcel useGoodsExcel : useGoodsExcelList) {
            UseGoodsExcel.UseGoodsPriceExcel useGoodsPriceExcel = DataTransformUntil.simulationBean(UseGoodsExcel.UseGoodsPriceExcel.class);
            useGoodsPriceExcel.setPrice(100.00);
            useGoodsPriceExcel.setUnit("元");

            useGoodsExcel.setUseGoodsPriceExcel(useGoodsPriceExcel);
        }
        return useGoodsExcelList;
    }


    @EasyExcel(fileName = "导出Excel模版-C")
    @ApiOperation(tags = "导出账户信息", value = "导出Excel模版-C")
    @GetMapping(value = "/export/exportAccount")
    public List<UseAccountExcel> exportAccount() {
        return DataTransformUntil.simulationBeanList(UseAccountExcel.class, 5);
    }

    @EasyExcel(fileName = "导出用户包含应用、模块、菜单、按钮Excel")
    @ApiOperation(tags = "导出用户包含应用、模块、菜单、按钮Excel", value = "导出用户包含应用、模块、菜单、按钮Excel")
    @GetMapping(value = "/export/exportUserAPPModuleMenuButton")
    public List<UserAPPModuleMenuButtonExcel> exportUserAPPModuleMenuButton() {
        UserAPPModuleMenuButtonExcel userAPPModuleMenuButtonExcel = new UserAPPModuleMenuButtonExcel();
        userAPPModuleMenuButtonExcel.setUserName("张三");
        userAPPModuleMenuButtonExcel.setEmail("120@qq.com");
        userAPPModuleMenuButtonExcel.setMobile("157xxxx0987");
        userAPPModuleMenuButtonExcel.setPosition("小白");
        // 应用
        UserAPPModuleMenuButtonExcel.AuthByApplication authByApplication = new UserAPPModuleMenuButtonExcel.AuthByApplication();
        authByApplication.setApplicationCode("demo");
        authByApplication.setApplicationName("demoApp");

        UserAPPModuleMenuButtonExcel.AuthByModule authByModule = new UserAPPModuleMenuButtonExcel.AuthByModule();
        authByModule.setModuleName("测试模块");
        authByModule.setModuleId(1L);
        UserAPPModuleMenuButtonExcel.AuthByMenu authByMenu = new UserAPPModuleMenuButtonExcel.AuthByMenu();
//        authByMenu.setMenuName()
//        authByModule.setAuthByMenuList()
//        authByApplication.setAuthByModuleList();

        return Arrays.asList(userAPPModuleMenuButtonExcel);
    }

}

