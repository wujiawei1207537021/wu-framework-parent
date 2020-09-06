package com.wuframework.system.controller;

import com.wuframework.shiro.annotation.CustomController;
import com.wuframework.system.persistence.mapper.SysDeptMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;

/**
 * <p>
 * 系统配置参数表 前端控制器
 * </p>
 *
 * @author Xiongxz
 * @since 2019-05-19
 */
@Api(tags = "系统配置")
@CustomController({"/system/config"})
public class SysConfigController {


    @Resource
    private SysDeptMapper sysDeptMapper;
    @ApiOperation("测试cc")
    @PostMapping
    public void cc() {
//        sysDeptMapper.delete(null);
    }

//    @RequiredPermission()
    @ApiOperation("测试cc")
    @GetMapping("/cc")
    public void cc1() {
        System.out.println("ccc");
    }
}
