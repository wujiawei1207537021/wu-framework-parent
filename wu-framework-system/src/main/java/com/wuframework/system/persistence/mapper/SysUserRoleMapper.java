package com.wuframework.system.persistence.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wuframework.system.common.entity.SysUserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 Mapper 接口
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
@Repository
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    /**
     * 批量保存用户关联角色信息
     *
     * @param list
     * @return
     */
    Integer insertBatchUserRole(List<SysUserRole> list);

    /**
     * 查询用户角色标示
     * @param userId
     * @return
     */
    List<String> listRoleSign(Integer userId);
}
