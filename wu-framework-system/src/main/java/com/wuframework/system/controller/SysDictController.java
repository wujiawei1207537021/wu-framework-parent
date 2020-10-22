package com.wuframework.system.controller;

import com.alibaba.fastjson.JSON;
import com.wu.framework.inner.swagger.annotation.CustomController;
import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.response.enmus.DefaultResultCode;
import com.wuframework.shiro.annotation.AccessTokenUser;
import com.wuframework.shiro.annotation.RequiredRole;
import com.wuframework.system.common.consts.CharValidConsts;
import com.wuframework.system.common.entity.DefaultSysUser;
import com.wuframework.system.common.entity.SysDict;
import com.wuframework.system.common.vo.SysDictVO;
import com.wuframework.system.serivce.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author Xiongxz
 * @since 2019-02-14
 */
@Api(tags = "系统字典管理模块")
@CustomController({"/sysdict", "/system/dict"})
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

    @RequiredRole(orRoles = {"super_admin", "admin"})
    @ApiOperation(value = "添加字典信息")
    @PostMapping()
    public Result saveDict(@RequestBody SysDict sysDict, @AccessTokenUser DefaultSysUser sysUser) {
        return this.sysDictService.saveSysDict(sysUser, sysDict);
    }

    @RequiredRole(orRoles = {"super_admin", "admin"})
    @ApiOperation(value = "修改字典信息")
    @PutMapping()
    public Result updateDict(@RequestBody SysDict sysDict, @AccessTokenUser DefaultSysUser sysUser) {
        sysDict.setUpdateBy(sysUser.getUserId());
//        sysDict.setUpdateDate(new Date());
        int row = this.sysDictService.updateSysDict(sysDict);
        if (row > 0) {
            return ResultFactory.successOf(row);
        }
        return ResultFactory.of(DefaultResultCode.RESOURCE_NOT_FOUNT);
    }

    @RequiredRole(orRoles = {"super_admin", "admin"})
    @ApiOperation(value = "删除字典信息-逻辑删除")
    @PutMapping("/batchdelete")
    public Result batchDeleteDict(@ApiParam(name = "dictId", value = "字典信息ID-（数组）", required = true)
                                  @RequestBody Map<String, Long[]> dictId) {
        Long[] dictIds = dictId.get("dictId");
        int row = this.sysDictService.batchDeleteSysDict(dictIds);
        if (row == dictIds.length) {
            return ResultFactory.successOf(row);
        }
        return ResultFactory.of(DefaultResultCode.RESOURCE_NOT_FOUNT);
    }

    @ApiOperation(value = "获取字典信息", notes = "获取全部字典信息 or 根据字典类型获取相关字典信息")
    @GetMapping("/{dictType}")
    public Result listDictByTypeOrAll(@ApiParam(name = "dictType", value = "根据字典类型获取相关字典信息,为' '查询全部", required = false)
                                      @PathVariable(value = "dictType", required = false) String dictType) {
        if (Objects.equals(dictType, CharValidConsts.VALID_UNDEFINED)) {
            return ResultFactory.of(DefaultResultCode.PERSISTENT_DATA_ERROR, "dictType Can not be undefined");
        }
        return ResultFactory.successOf(this.sysDictService.listSysDictByTypeOrAll(dictType));
    }

    @ApiOperation(value = "获取单个字典信息", notes = "根据type和value获取字典信息")
    @GetMapping("/{dictType}/{value}")
    public Result dictByTypeAndValue(@ApiParam(name = "dictType", value = "字典类型", required = true)
                                     @PathVariable(value = "dictType", required = true) String dictType,
                                     @ApiParam(name = "value", value = "该字典的value值", required = true)
                                     @PathVariable(value = "value", required = true) String value) {
        if (StringUtils.isAnyBlank(dictType, value)) {
            return ResultFactory.of(DefaultResultCode.MISS_PARAMETER, "dictType or value Can not be empty");
        }
        SysDictVO sysDictVO = JSON.parseObject(JSON.toJSONString(this.sysDictService.getSysDictByTypeAndValue(dictType, value)), SysDictVO.class);
        return ResultFactory.successOf(sysDictVO);
    }


    @ApiOperation(value = "获取所有字典并转换成树")
    @GetMapping("/all")
    public Result tree() {
        return sysDictService.all();
    }


}
