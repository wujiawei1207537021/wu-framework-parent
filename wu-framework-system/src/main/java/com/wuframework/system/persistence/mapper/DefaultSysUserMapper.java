package com.wuframework.system.persistence.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wuframework.system.common.dto.UserDTO;
import com.wuframework.system.common.entity.DefaultSysUser;
import com.wuframework.system.common.vo.SimpleSelectVo;
import com.wuframework.system.common.vo.UserVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
@Repository
public interface DefaultSysUserMapper extends BaseMapper<DefaultSysUser> {
    /**
     * 查询登录用户
     *
     * @param user
     * @return
     */
    DefaultSysUser selectByLogin(DefaultSysUser user);

    /**
     * 根据手机号查询用户
     *
     * @param user
     * @return
     */
    DefaultSysUser loginByPhone(DefaultSysUser user);

    /**
     * 根据用户名称查询用户
     *
     * @param username
     * @return
     */
    DefaultSysUser selectByUsername(String username);

    /**
     * 根据角色编号获取用户信息
     *
     * @param roleId 角色编号
     * @return
     */
    List<DefaultSysUser> getUserListByRoleId(Long roleId);

    /**
     * 根据参数查询简单用户下拉对象
     *
     * @param userDTO
     * @return
     */
    List<SimpleSelectVo> selectSimpleSelectVoList(UserDTO userDTO);

    /**
     * 获取人员列表包含部门
     *
     * @param userDTO
     * @param page
     * @return
     */
    List<UserVO> selectUser(UserDTO userDTO, Page page);

    /**
     * 获取人员列表包含部门
     *
     * @param userDTO
     * @return
     */
    List<UserVO> selectUser(UserDTO userDTO);

    /**
     * 批量添加用户
     *
     * @param list 用户列表
     * @return
     */
    Integer insertBatchUser(List<DefaultSysUser> list);

    /**
     * 获取经营者id
     *
     * @param dealerName
     * @return
     */
    Long selectDealerId(String dealerName);

    /**
     * 获取用户id
     *
     * @return
     */
    List<Long> selectUserId();

    /**
     * 判断指定用户是否拥有指定角色
     *
     * @param userId
     * @param roleSign
     * @return
     */
    Integer isExistAppointRole(Long userId, String roleSign);


    DefaultSysUser getUserByUsername(String userName);
}
