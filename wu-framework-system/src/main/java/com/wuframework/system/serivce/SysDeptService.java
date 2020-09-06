package com.wuframework.system.serivce;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wuframework.system.common.dto.DeptDTO;
import com.wuframework.system.common.entity.SysDept;
import com.wuframework.system.common.entity.DefaultSysUser;
import com.wuframework.system.common.vo.*;

import java.util.List;

/**
 * @author maohuanhuan
 * @Date: 2018/7/11 09:50
 * @Description:
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 部门tree
     *
     * @return
     */
    List<TreeNode> listDept();

    /**
     * 查询当前用户部门所在的所有机构
     *
     * @param deptType 部门级别（1，2····）
     * @param user     当前用户
     * @return
     */
    List<TreeNode> getDeptListAllTree(List<String> deptType, DefaultSysUser user);

    /**
     * 获取组织架构-树（根据部门级别）
     *
     * @param deptType 部门级别（1，2····）
     * @param user     当前用户
     * @param optional
     * @return
     */
    List<TreeNode> selectListDeptSelfTree(String deptType, DefaultSysUser user, Boolean optional);

    /**
     * 根据部门ID查询部门信息
     *
     * @param deptId 部门ID
     * @return
     */
    SysDept getDeptById(Long deptId);

    /**
     * 刷新Redis部门数据
     *
     * @param user 当前用户
     */
    void refreshRedisDept(DefaultSysUser user);

    /**
     * 保存部门
     *
     * @param dept
     * @param sysUser
     * @return
     */
    void saveDept(SysDept dept, DefaultSysUser sysUser);

    /**
     * 根据部门ID修改部门
     *
     * @param dept 部门
     * @return
     */
    void updateDeptById(SysDept dept);

    /**
     * 根据部门ID禁用部门
     *
     * @param deptId 部门ID
     * @return
     */
    Integer batchDeleteDeptById(Long[] deptId);

    /**
     * 根据部门ID删除部门
     *
     * @param deptId 部门ID
     * @return
     */
    Integer deleteDeptById(Long deptId);

    /**
     * 根据部门ID，获取当前部门所属辖区
     *
     * @param deptId 部门ID
     * @return
     */
    String getJurisdiction(Long deptId);

    /**
     * 根据部门ID查询所有父级节点
     *
     * @param deptId   部门ID
     * @param deptType 部门类型
     * @return
     */
    List<DeptVO> selectDeptParentList(Long deptId, Integer deptType);

    /**
     * 根据部门编号查询子节点部门序列
     *
     * @param deptId   部门编号
     * @param deptType 部门类型
     * @return
     */
    List<SysDept> getDeptChildrenByParentId(Integer deptId, Integer deptType);

    /**
     * 根据检测点编号获取检测市场
     *
     * @param deptId 检测点编号
     * @return
     */
    List<SysDept> getDeptListByDetection(Long deptId);

    /**
     * 根据所属部门获取子市场
     *
     * @param deptId 科室编号编号
     * @return
     */
    List<SysDept> getMarketListByDept(String deptId);

    /**
     * 根据所属部门获取检测点清单
     *
     * @param deptId
     * @return
     */
    List<SysDept> getDetectionListByDept(String deptId);

    /**
     * 获取所有市场的地理坐标
     *
     * @param deptIds 部门ids
     * @return
     */
    List<DeptLocationVO> selectListMarketLocation(String deptIds);

    /**
     * 根据部门名称获取部门信息
     *
     * @param deptName 部门名称
     * @return
     */
    SysDept selectDeptByDeptName(String deptName);

    /**
     * 获取时间范围内市场的检验量
     *
     * @param deptId    市场编号
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    List<DeptSampleCountVO> selectListMarketSampleCount(String deptId, String startDate, String endDate);

    /**
     * 查询所有部门的评分
     *
     * @param deptId   部门ID
     * @param deptType 部门类型
     * @return
     */
    List<DeptRankVO> selectListMarketRank(String deptId, Integer deptType);

    /**
     * 根据 父节点id 和 查询目标部门类型 查出父节点下所有的指定类型的子节点,并查出子节点到父节点的级数和路径的每个节点
     *
     * @param deptId   部门ID
     * @param deptType 部门类型
     * @return
     */
    List<DeptNodeVO> selectListDeptChildrenByType(Long deptId, Integer deptType);

    /**
     * 获取所有市场id
     *
     * @return
     */
    List<Long> getMarketId();

    /**
     * 根据检测科室存储市场清单
     *
     * @param detectionId 检测单位
     * @param deptList    部门集合
     * @return
     */
    int saveByDetectionId(Long detectionId, List<String> deptList);

    /**
     * 根据类型查询部门列表
     *
     * @param deptType 部门类型
     * @return
     */
    List<SysDept> selectDeptByDeptType(Integer deptType);

    /**
     * 根据条件查询组织列表
     *
     * @param deptDTO 部门&市场DTO
     * @param page    分页
     * @return
     */
    Page getListDeptByDeptDTO(DeptDTO deptDTO, Page page);

    /**
     * 获取当前数据库中的市级部门
     *
     * @return
     */
    SysDept getTopDept();

    /**
     * 添加部门到redis
     */
    void saveRedisByDept();

    /**
     * 获取部门下一级的所有部门
     *
     * @param deptId
     * @return
     */
    List<SysDept> listNextLevelDept(Integer deptId);

}
