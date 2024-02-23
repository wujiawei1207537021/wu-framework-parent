package com.wuframework.system.persistence.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wuframework.system.common.entity.SysRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 根据用户ID获取相关角色
     *
     * @param userId
     * @return
     */
    List<SysRole> selectRoleByUserId(Long userId);

    /**
     * 根据用户ID获取用户角色信息分页
     *
     * @param role
     * @param page
     * @return
     */
    List<SysRole> listRoleUnderRole(SysRole role, Page page);
}
