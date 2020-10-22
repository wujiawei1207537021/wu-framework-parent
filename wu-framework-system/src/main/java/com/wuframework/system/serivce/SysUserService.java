package com.wuframework.system.serivce;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.system.common.dto.UserDTO;
import com.wuframework.system.common.entity.DefaultSysUser;
import com.wuframework.system.common.entity.DefaultSysUserDetails;
import com.wuframework.system.common.vo.SimpleSelectVo;
import com.wuframework.system.common.vo.UserVO;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
public interface SysUserService extends IService<DefaultSysUser> {


    /**
     * 新增用户
     *
     * @param sysUser
     * @param defaultSysUser
     * @return
     */
    default Result save(DefaultSysUser sysUser, DefaultSysUserDetails defaultSysUser) {
        sysUser.setUserIdCreate(defaultSysUser.getUserId());
        sysUser.setPassword(DigestUtils.md5Hex(sysUser.getPassword()));
        if (null == sysUser.getDeptId()) {
            sysUser.setDeptId(defaultSysUser.getDeptId());
        }
        sysUser.insert();
        return ResultFactory.successOf(sysUser);

    }

    ;

    /**
     * 根据手机号与密码进行登录
     *
     * @param user 封装了登录用户名密码参数的user对象
     * @return
     */
    DefaultSysUser loginByPhone(DefaultSysUser user);

    /**
     * 根据传入参数获取人员下拉框
     *
     * @param userDTO
     * @return
     */
    List<SimpleSelectVo> getSimpleSelectVoList(UserDTO userDTO);

    /**
     * 注销
     *
     * @param username 用户姓名
     * @return
     */
    DefaultSysUser logout(String username);

    /**
     * 根据账号查找用户
     *
     * @param username 用户姓名
     * @return
     */
    DefaultSysUser getUserByUsername(String username);


    /**
     * 获取人员列表包含部门
     *
     * @param userDTO 用户DTO
     * @param page    分页
     * @return
     */
    Page selectUser(UserDTO userDTO, Page page);

    /**
     * 获取人员列表包含部门
     *
     * @param userDTO 用户DTO
     * @return
     */
    List<UserVO> selectUser(UserDTO userDTO);

    /**
     * 根据权限获取用户列表
     *
     * @param roleId
     * @return
     */
    List<DefaultSysUser> getUserListByRoleId(Long roleId);

    /**
     * 根据科室编号获取子部门的用户列表
     *
     * @param haveChildren 是否包含子节点
     * @param deptId       部门编号
     * @return
     */
    List<DefaultSysUser> getUserListByDept(Boolean haveChildren, String deptId);

    /**
     * 根据用户名查询用户
     *
     * @param name 用户姓名
     * @return
     */
    DefaultSysUser selectUserByName(String name);

//    /**
//     * token进行延长时间
//     *
//     * @param user 用户信息
//     * @return
//     */
//    HashMap<String, Object> refreshToken(DefaultSysUser user);

    /**
     * 验证手机号码
     *
     * @param userId
     * @param mobile
     * @return
     */
    Boolean selectVerifyMobileNumber(Integer userId, String mobile);

    /**
     * 获取经营者id
     *
     * @param dealerName 经营商姓名
     * @return
     */
    Long getDealerId(String dealerName);

    /**
     * 获取所有用户
     *
     * @return
     */
    List<Long> getUserId();

    /**
     * 根据ID查询用户
     *
     * @param userId
     * @return
     */
    DefaultSysUser selectUserById(Integer userId);

    /**
     * 更新用户信息
     *
     * @param sysUser
     * @return
     */
    String updateUser(DefaultSysUser sysUser);

    /**
     * 获取指定部门下的所有用户
     *
     * @param deptIdList
     * @return
     */
    List<DefaultSysUser> listUsersByDeptIdList(List<Integer> deptIdList);

    /**
     * 判断用户是否存在指定角色
     *
     * @param userId
     * @param roleSign
     * @return
     */
    boolean isExistAppointRole(Long userId, String roleSign);


    /**
     * app保存用户
     *
     * @param sysUser
     * @param currentUser
     * @return
     */
    void appSaveUser(DefaultSysUser sysUser, DefaultSysUser currentUser);

    default Result getAllByPage(Page page, DefaultSysUser defaultSysUserQO, boolean pagination, DefaultSysUserDetails current) {
        EntityWrapper<DefaultSysUser> userEntityWrapper = new EntityWrapper<>();
        Optional.ofNullable(defaultSysUserQO.getUserId()).ifPresent(userId -> userEntityWrapper.eq("user_id", userId));
        Optional.ofNullable(defaultSysUserQO.getUsername()).ifPresent(userName -> userEntityWrapper.like("username", defaultSysUserQO.getUsername()));
        Optional.ofNullable(defaultSysUserQO.getName()).ifPresent(name -> userEntityWrapper.like("name", defaultSysUserQO.getName()));
        Optional.ofNullable(defaultSysUserQO.getStatus()).ifPresent(status -> userEntityWrapper.eq("status", status.toString()));
        return ResultFactory.successOf(defaultSysUserQO.selectPage(page, userEntityWrapper));
    }

    ;

    /**
     * @param userDTO
     * @param sysUser
     * @param page
     * @return
     */
    Result getUserAllByUserDTO(UserDTO userDTO, DefaultSysUserDetails sysUser, Page page);

    /**
     * 获取用户信息
     *
     * @param defaultSysUserDetails
     * @return
     */
    default Result info(DefaultSysUserDetails defaultSysUserDetails) {
        return ResultFactory.successOf(defaultSysUserDetails);
    }

    ;
}
