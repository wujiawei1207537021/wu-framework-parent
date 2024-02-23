package com.wuframework.system.persistence.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wuframework.system.common.dto.DeptDTO;
import com.wuframework.system.common.entity.SysDept;
import com.wuframework.system.common.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 部门管理 Mapper 接口
 * </p>
 *
 * @author Xiongxz
 * @since 2018-07-11
 */
@Repository
public interface SysDeptMapper extends BaseMapper<SysDept> {
    /**
     * 根据参数获取组织列表
     *
     * @param deptDTO
     * @param page
     * @return
     */
    List<DeptVO> selectListDeptByDeptDTO(DeptDTO deptDTO, Page page);

    /**
     * 根据参数获取组织列表
     *
     * @param deptDTO
     * @return
     */
    List<DeptVO> selectListDeptByDeptDTOAll(DeptDTO deptDTO);

    /**
     * 根据部门ID，获取当前部门所属辖区
     *
     * @param deptId
     * @return
     */
    List<SysDept> selectJurisdiction(@Param("deptId") Long deptId);

    /**
     * 根据部门ID查询所有父级节点
     *
     * @param deptId
     * @param deptType
     * @return
     */
    List<DeptVO> selectDeptParentList(@Param("deptId") Long deptId, @Param("deptType") Integer deptType);

    /**
     * 根据科室的编号查找他的子节点数据
     *
     * @param deptId 科室编号
     * @return
     */
    String selectDeptChildrenByRootId(@Param("deptId") String deptId);

    /**
     * 根据部门id查出它下面所有特定类型的子节点
     *
     * @param deptId   部门编号
     * @param deptType 部门类型
     * @return
     */
    List<DeptNodeVO> selectListDeptChildrenByType(@Param("deptId") Long deptId, @Param("deptType") Integer deptType);

    /**
     * 根据检测点编号获取被监测点
     *
     * @param detectionId 检测点编号
     * @return
     */
    List<SysDept> selectDeptListByDetection(@Param("deptId") String detectionId);

    /**
     * 根据参数获取经纬度
     *
     * @param deptIds
     * @return
     */
    List<DeptLocationVO> selectListMarketLocation(@Param("deptIds") String deptIds);

    /**
     * 根据检索条件获取数据
     *
     * @param deptIds   科室信息
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    List<DeptSampleCountVO> selectListMarketSampleCount(@Param("deptIds") String deptIds,
                                                        @Param("startDate") String startDate,
                                                        @Param("endDate") String endDate);


    /**
     * 查询所有部门的评分
     *
     * @param childDeptIds
     * @param deptType
     * @return
     */
    List<DeptRankVO> selectListMarketRank(@Param("childDeptIds") String[] childDeptIds, @Param("deptType") Integer deptType);

    /**
     * 获取指定ID的部门数量
     *
     * @param deptId
     * @return
     */
    Integer selectDeptCount(String deptId);

    /**
     * 获取所有市场ID
     *
     * @return
     */
    List<Long> selectMarketId();

}
