package com.wuframework.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.wuframework.system.common.vo.TreeNode;
import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.response.enmus.DefaultResultCode;
import com.wuframework.shiro.annotation.AccessTokenUser;
import com.wuframework.shiro.annotation.CustomController;
import com.wuframework.db.annotation.RequestPage;
import com.wuframework.system.common.dto.DeptDTO;
import com.wuframework.system.common.entity.DefaultSysUser;
import com.wuframework.system.common.entity.SysDept;
import com.wuframework.system.serivce.SysDeptService;
import com.wuframework.system.serivce.SysUserService;
import com.wuframework.system.utils.BusinessUtils;
import com.wuframework.system.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author : Xiongxz
 * @Date: 2018/7/10 18:13
 * @Description:
 */
@Api(tags = "系统组织架构模块", description = "组织架构接口")
@CustomController("/depts")
public class SysDeptController {

    private SysDeptService deptService;

    private SysUserService sysUserService;


    @Autowired
    public SysDeptController(SysUserService defaultSysUserService, SysUserService sysUserService,
                             SysDeptService defaultSysDeptService,SysDeptService sysDeptService){
        if(ObjectUtils.isEmpty(sysUserService)){
            this.sysUserService=defaultSysUserService;
        }else {
            this.sysUserService=sysUserService;
        }
        if(ObjectUtils.isEmpty(sysDeptService)){
            this.deptService=defaultSysDeptService;
        }else {
            this.deptService=sysDeptService;
        }
    }


    @ApiOperation(value = "获取组织架构-树", notes = "获取所有部门树")
    @GetMapping()
    public List<TreeNode> listDept(@AccessTokenUser DefaultSysUser sysUser) {
        return this.deptService.listDept();
    }

    @ApiOperation(value = "获取组织架构-树（根据部门级别）", notes = "根据deptType过滤类别（1，2···），all为true查询子部门（含自己），false查询全部(未完成)")
    @GetMapping(value = {"/node/{deptType}/tree", "/node/{deptType}/tree/{optional}", "/node/tree"})
    public List<TreeNode> listDeptSelfTree(@ApiParam(name = "deptType", value = "部门类型", required = false)
                                           @PathVariable(value = "deptType", required = false) String deptType,
                                           @PathVariable(value = "optional", required = false) Boolean optional,
                                           @AccessTokenUser DefaultSysUser user) {
        if (StringUtils.isBlank(deptType)) {
            // 查询当前用户能够查看的数据类型
            List<String> dataTypeList = BusinessUtils.getFilterTypeList(user);
            if (!ObjectUtils.isEmpty(dataTypeList)) {
                deptType = String.join(",", dataTypeList);
            }
        }
        return this.deptService.selectListDeptSelfTree(deptType, user, optional);
    }

    @ApiOperation(value = "根据条件获取部门或市场列表-分页（可选）", notes = "获取部门或者市场")
    @GetMapping("/list")
    public Result getListDeptByDeptDTO(@ModelAttribute("deptDTO") DeptDTO deptDTO, @RequestPage Page page, @AccessTokenUser DefaultSysUser sysUser) {
        if (deptDTO.getDeptType() != null) {
            deptDTO.setDeptIds(RedisUtils.redisUtils.getChildDeptListStrFromRedisByParentId(sysUser.getDeptId()));
        }
        return ResultFactory.successOf(this.deptService.getListDeptByDeptDTO(deptDTO, page));
    }

    @ApiOperation(value = "根据用户和部门类型查询指定子部门ID集合", notes = "deptType(1,2···)-字符串")
    @GetMapping("/depttype/{deptType}/list")
    public Result listDeptIdByDeptType(@ApiParam(name = "deptType", value = "部门类型", required = false)
                                       @PathVariable(value = "deptType", required = false) String deptType, @AccessTokenUser DefaultSysUser sysUser) {
        List<String> deptTypes = Arrays.asList(deptType.split(","));
        return ResultFactory.successOf(RedisUtils.redisUtils.getChildDeptListStrFromRedisByParentId(sysUser.getDeptId()));
    }

    @ApiOperation(value = "根据用户和部门类型查询指定子部门实体类集合", notes = "deptType(1,2···)-字符串")
    @GetMapping("/{deptType}/list")
    public Result listDeptFromRedisByDeptType(@ApiParam(name = "deptType", value = "部门类型", required = false)
                                              @PathVariable(value = "deptType", required = false) String deptType, @AccessTokenUser DefaultSysUser sysUser) {
        List<String> deptTypes = Arrays.asList(StringUtils.split(deptType, ","));
        return ResultFactory.successOf(RedisUtils.redisUtils.selectDeptListFromRedisByDeptType(sysUser.getDeptId(), deptTypes));
    }

    @ApiOperation(value = "新增部门&市场", notes = "添加单个部门或市场")
    @PostMapping("")
    public Result saveDept(@RequestBody SysDept dept, @AccessTokenUser DefaultSysUser sysUser) {
        this.deptService.saveDept(dept, sysUser);
        return ResultFactory.successOf();
    }

    @ApiOperation(value = "更新部门&市场", notes = "更新单个部门或市场")
    @PutMapping("")
    public Result updateDeptById(@RequestBody SysDept dept) {
        this.deptService.updateDeptById(dept);
        return ResultFactory.successOf();
    }

    @Deprecated
    @ApiOperation(value = "删除部门-物理删除", notes = "删除单个部门")
    @DeleteMapping("/{deptId}")
    public Result deleteDepById(@ApiParam(name = "deptId", value = "部门ID", required = true)
                                @PathVariable(value = "deptId", required = true) @Min(value = 1, message = "部门ID必须大于0") Long deptId) {
        int row = this.deptService.deleteDeptById(deptId);
        if (row > 0) {
            return ResultFactory.successOf(row);
        }
        return ResultFactory.of(DefaultResultCode.RESOURCE_NOT_FOUNT);
    }

    @ApiOperation(value = "删除部门-逻辑删除", notes = "修改enabled状态为0（禁用状态）")
    @PutMapping("/batchdelete")
    public Result batchDeleteDeptById(@ApiParam(name = "deptId", value = "部门ID", required = true)
                                      @RequestBody Map<String, Long[]> deptId) {
        Long[] deptIds = deptId.get("deptId");
        int row = this.deptService.batchDeleteDeptById(deptIds);
        if (row == deptIds.length) {
            return ResultFactory.successOf(row);
        }
        return ResultFactory.of(DefaultResultCode.RESOURCE_NOT_FOUNT);
    }

    @Deprecated
    @ApiOperation(value = "根据部门ID，获取当前部门所属辖区")
    @GetMapping("/jurisdiction")
    public Result getJurisdiction(@ApiParam(name = "deptId", value = "部门ID", required = true)
                                  @RequestParam(value = "deptId") @Min(value = 1, message = "部门ID必须大于0") Long deptId) {
        return ResultFactory.successOf(this.deptService.getJurisdiction(deptId));
    }

    /**
     * 根据部门编号与分类编号查找该部门下该类型部门的集合
     *
     * @param deptId   部门编号
     * @param deptType 部门类型
     * @return
     */
    @GetMapping("/children/{deptId}/{deptType}")
    public Result getDeptChildrenByRootId(@PathVariable(value = "deptId") Integer deptId,
                                          @PathVariable(value = "deptType") Integer deptType) {
        return ResultFactory.successOf(deptService.getDeptChildrenByParentId(deptId, deptType));
    }

    /**
     * 根据检测点编号查询被检测单位数据
     *
     * @param detectionId 检测点编号
     * @return
     */
    @GetMapping("/detection/{detectionId}")
    public Result getDeptListByDetection(@PathVariable(value = "detectionId") Long detectionId) {
        return ResultFactory.successOf(deptService.getDeptListByDetection(detectionId));
    }

    /**
     * 获取采样市场
     *
     * @return
     */
    @GetMapping("/market")
    public Result market(@AccessTokenUser DefaultSysUser user) {
        List<SysDept> deptList = deptService.getMarketListByDept(user.getDeptId().toString());
        return ResultFactory.successOf(deptList);
    }

    /**
     * 获取辖区检测点数据
     *
     * @param user
     * @return
     */
    @GetMapping("/detection")
    public Result detection(@AccessTokenUser DefaultSysUser user) {
        List<SysDept> deptList = deptService.getDetectionListByDept(user.getDeptId().toString());

        return ResultFactory.successOf(deptList);
    }

    @ApiOperation(value = "获取所有市场的经纬度和地理位置信息")
    @GetMapping("/market/location")
    public Result getMarketLocation(@AccessTokenUser DefaultSysUser sysUser) {
        String deptIds = RedisUtils.redisUtils.getChildDeptListStrFromRedisByParentId(sysUser.getDeptId());
        if (StringUtils.isBlank(deptIds)) {
            deptIds = sysUser.getDeptId().toString();
        }
        return ResultFactory.successOf(this.deptService.selectListMarketLocation(deptIds));
    }

    @ApiOperation(value = "获取每个市场的检验样本数量")
    @GetMapping("/sampleCount/{deptType}/{startDate}/{endDate}")
    public Result getMarketSampleCount(@AccessTokenUser DefaultSysUser sysUser,
                                       @PathVariable(value = "deptType", required = false) String deptType,
                                       @PathVariable(value = "startDate") String sDate,
                                       @PathVariable(value = "endDate") String eDate) {
        String deptList = RedisUtils.redisUtils.getChildDeptListStrFromRedisByParentId(sysUser.getDeptId());
        return ResultFactory.successOf(this.deptService.selectListMarketSampleCount(deptList, sDate, eDate));
    }

    @ApiOperation(value = "获取市场分数排名", notes = "根据评价评分获取市场的排名")
    @GetMapping(value = {"/market/rank", "/market/rank/{deptType}"})
    public Result getMarketRank(@AccessTokenUser DefaultSysUser user,
                                @PathVariable(value = "deptType", required = false) Integer deptType) {
        return ResultFactory.successOf(deptService.selectListMarketRank(user.getDeptId().toString(), deptType));
    }

    @ApiOperation(value = "获取当前用户的部门级别")
    @GetMapping("/depttype")
    public Result getUserDeptType(@AccessTokenUser DefaultSysUser user) {
        return ResultFactory.successOf(this.deptService.getDeptById(user.getDeptId().longValue()));
    }

    @ApiOperation(value = "获取所有市场id")
    @GetMapping("/marketId")
    public Result getMarketId() {
        return ResultFactory.successOf(deptService.getMarketId());
    }

    @ApiOperation(value = "根据检测室编号保存市场的编号")
    @PostMapping("/detection/{detectionId}")
    public Result saveDeptListByDetection(@ApiParam(name = "detectionId", value = " 检测室编号", required = true) @PathVariable Long detectionId,
                                          @ApiParam(name = "deptList", value = "部门清单-（数组）") @RequestBody List<String> deptList) {
        int row = this.deptService.saveByDetectionId(detectionId, deptList);
        boolean existed = row == deptList.size() || (!ObjectUtils.isEmpty(deptList) && row == 0);
        if (existed) {
            return ResultFactory.successOf(row);
        }
        return ResultFactory.of(DefaultResultCode.PERSISTENT_DATA_ERROR);
    }

    @ApiOperation(value = "根据ID获部门")
    @GetMapping("/{deptId}")
    public Result selectSysDeptById(@PathVariable Integer deptId, @AccessTokenUser DefaultSysUser sysUser) {
        try {
            return ResultFactory.successOf(this.deptService.selectById(deptId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.of(DefaultResultCode.PERSISTENT_DATA_ERROR);
        }
    }

    /**
     * 获取采样人员
     *
     * @return
     */
    @ApiOperation(value = "根据部门ID获取,所有用户(包括子部门)")
    @GetMapping("/{deptId}/{haveChildren}/users")
    public Result getAllUsers(@PathVariable Integer deptId, @PathVariable Boolean haveChildren) {
        List<DefaultSysUser> userList = sysUserService.getUserListByDept(haveChildren, deptId.toString());
        return ResultFactory.successOf(userList);
    }
}
