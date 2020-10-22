package com.wuframework.system.controller;


import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.response.mark.ValidType;
import com.wuframework.shiro.annotation.CustomController;
import com.wuframework.shiro.annotation.RequiredRole;
import com.wuframework.system.common.entity.SysDict;
import com.wuframework.system.common.entity.SysDictType;
import com.wuframework.system.serivce.SysDictService;
import com.wuframework.system.serivce.SysDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Xiongxz
 * @since 2019-02-14
 */
@Api(tags = "系统字典类型管理模块")
@CustomController({"/sysdicttype", "/system/dict/type"})
public class SysDictTypeController {

    @Resource
    private SysDictTypeService sysDictTypeService;

    @Resource
    private SysDictService sysDictService;

    @RequiredRole(orRoles = {"super_admin", "admin"})
    @ApiOperation(value = "添加字典类别")
    @PostMapping()
    public Result saveSysDictType(@Validated(ValidType.Create.class) @RequestBody SysDictType sysDictType) {
        return this.sysDictTypeService.saveSysDictType(sysDictType);
    }

    @RequiredRole(orRoles = {"super_admin", "admin"})
    @ApiOperation(value = "修改字典类别", notes = "修改字典类型，相应字典类别的字典会被同时修改")
    @PutMapping()
    public Result updateSysDictType(@Validated(ValidType.Update.class) @RequestBody SysDictType sysDictType) {
        return this.sysDictTypeService.updateSysDictType(sysDictType);
    }

    @RequiredRole(orRoles = {"super_admin", "admin"})
    @ApiOperation(value = "删除字典类别", notes = "根据字典类别删除该字典信息")
    @PutMapping(value = "/{typeId}")
    public Result deleteSysDictType(@ApiParam(name = "typeId", value = "字典类别ID", required = true)
                                    @PathVariable(value = "typeId") String typeId) {
        return this.sysDictTypeService.deleteSysDictTypeAndDict(typeId);

    }

    @ApiOperation(value = "该字典类别是否存在", notes = "true存在，false不存在")
    @GetMapping("/{typeId}")
    public Result<Boolean> isExist(@ApiParam(name = "typeId", value = "字典类别ID", required = true)
                                   @PathVariable(value = "typeId") String typeId) {
        return this.sysDictTypeService.isExist(typeId);
    }

    @ApiOperation(value = "获取字典类型列表")
    @GetMapping()
    public Result<List<SysDictType>> listSysDictType() {
        return this.sysDictTypeService.selectSysDictTypeList();
    }


    @ApiOperation(value = "获取字典信息列表，已map形式返回")
    @GetMapping("/map")
    public Result<Map<String, List<SysDict>>> listDictByType() {
        List<SysDictType> sysDictTypeList = this.sysDictTypeService.selectSysDictTypeList().getData();
        Map<String, List<SysDict>> deptMap = sysDictTypeList.stream()
                .collect(Collectors.toMap(SysDictType::getTypeId,
                        dictType -> this.sysDictService.listSysDictByTypeOrAll(dictType.getTypeId())));
        return ResultFactory.successOf(deptMap);
    }
}
