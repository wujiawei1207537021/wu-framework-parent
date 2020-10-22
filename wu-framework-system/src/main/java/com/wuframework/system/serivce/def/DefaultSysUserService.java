package com.wuframework.system.serivce.def;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.base.Splitter;
import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.system.common.dto.UserDTO;
import com.wuframework.system.common.entity.DefaultSysUser;
import com.wuframework.system.common.entity.DefaultSysUserDetails;
import com.wuframework.system.common.entity.SysDept;
import com.wuframework.system.common.vo.SimpleSelectVo;
import com.wuframework.system.common.vo.UserVO;
import com.wuframework.system.persistence.mapper.DefaultSysUserMapper;
import com.wuframework.system.persistence.mapper.SysDeptMapper;
import com.wuframework.system.redis.RedisRepository;
import com.wuframework.system.serivce.SysDeptService;
import com.wuframework.system.serivce.SysUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
@Service("defaultSysUserService")
public class DefaultSysUserService extends ServiceImpl<DefaultSysUserMapper, DefaultSysUser> implements SysUserService {

    @Resource
    private DefaultSysUserMapper defaultSysUserMapper;

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private RedisRepository redisRepository;

    @Resource
    private SysDeptService sysDeptService;


    /**
     * 根据手机号与密码进行登录
     *
     * @param sysUser 封装了登录用户名密码参数的user对象
     * @return
     */
    @Override
    public DefaultSysUser loginByPhone(DefaultSysUser sysUser) {
        sysUser.setPassword(DigestUtils.md5Hex(sysUser.getPassword()));
        return this.defaultSysUserMapper.loginByPhone(sysUser);
    }

    /**
     * 根据传入参数获取人员下拉框
     *
     * @return
     */
    @Override
    public List<SimpleSelectVo> getSimpleSelectVoList(UserDTO userDTO) {
        return this.defaultSysUserMapper.selectSimpleSelectVoList(userDTO);
    }

    /***
     * 注销
     * @param username 用户姓名
     * @return
     */
    @Override
    public DefaultSysUser logout(String username) {
        return null;
    }

    /***
     * 根据用户名查找用户
     * @param username 用户姓名
     * @return 关联了role的user对象
     */
    @Override
    public DefaultSysUser getUserByUsername(String username) {
        return defaultSysUserMapper.selectByUsername(username);
    }

    /**
     * 获取人员列表包含部门
     *
     * @param userDTO 用户DTO
     * @param page    分页
     * @return
     */
    @Override
    public Page selectUser(UserDTO userDTO, Page page) {
        return page.setRecords(this.defaultSysUserMapper.selectUser(userDTO, page));
    }

    /**
     * 获取人员列表包含部门
     *
     * @param userDTO 用户DTO
     * @return
     */
    @Override
    public List<UserVO> selectUser(UserDTO userDTO) {
        return this.defaultSysUserMapper.selectUser(userDTO);
    }

    /**
     * 根据权限获取用户列表
     *
     * @param roleId 角色编号
     * @return
     */
    @Override
    public List<DefaultSysUser> getUserListByRoleId(Long roleId) {
        return this.defaultSysUserMapper.getUserListByRoleId(roleId);
    }

    /**
     * 根据科室编号获取子部门的用户列表【还有一定问题，需要过滤非市场的部门】
     *
     * @param haveChildren 是否包含子部门人员
     * @param deptId       部门编号
     * @return
     */
    @Override
    public List<DefaultSysUser> getUserListByDept(Boolean haveChildren, String deptId) {
        EntityWrapper<DefaultSysUser> userEntityWrapper = new EntityWrapper<>();
        if (haveChildren) {
            String deptList = this.sysDeptMapper.selectDeptChildrenByRootId(deptId);
            userEntityWrapper.in("dept_id", deptList);
        } else {
            userEntityWrapper.eq("dept_id", deptId);
        }
        return (this.defaultSysUserMapper.selectList(userEntityWrapper));
    }

    /**
     * 根据用户名查询用户
     *
     * @param name 用户姓名
     * @return
     */
    @Override
    public DefaultSysUser selectUserByName(String name) {
        DefaultSysUser user = new DefaultSysUser();
        user.setName(name);
        return this.defaultSysUserMapper.selectOne(user);
    }

    /**
     * token进行延长时间
     *
     * @param loginUser 用户信息
     * @return
     */
//    @Override
//    public HashMap<String, Object> refreshToken(DefaultSysUser loginUser) {
//        Long deptId = loginUser.getDeptId().longValue();
//        String token = JwtUtil.sign(loginUser.getUsername(), loginUser.getUserId());
//        List<SysMenu> menus = this.sysMenuService.getMenuListByUserId(loginUser.getUserId());
//        List<SysRoleMenu> defaultMenuIds = this.sysRoleMenuMapper.getRoleDefaultPage(loginUser.getUserId());
//        loginUser.setMenus(menus);
//        HashMap<String, Object> dict = new HashMap<String, Object>();
//        // 用户名称
//        dict.put("userName", loginUser.getName());
//        //用户所属部门类型
////            dict.put("deptType", this.sysDeptService.getDeptById(deptId).getDeptType());
//        // token
//        dict.put("token", token);
//        // refresh_token
//        dict.put("refresh_token", JwtUtil.signRefreshToken(loginUser.getUserId(), loginUser.getUsername()));
//        // 菜单
////            dict.put("menus", loginUser.getMenus());
//        // 角色[目前没用]
//        dict.put("roules", loginUser.getRoles());
//        //用户角色默认菜单
//        dict.put("defaultMenus", defaultMenuIds);
//        //所属市
//        dict.put("city", this.sysDeptMapper.selectById(deptId));
//        //加入或者更新缓存
//        redisRepository.setExpire(loginUser.getUserId().toString(), JSON.toJSONString(loginUser), ConfigConsts.EXPIRE_TIME);
//        loginUser.setPassword("");
//        dict.put("userInfo", loginUser);
//        List<String> perms = menus.stream().filter(x -> x.getType() == 2).map(SysMenu::getPerms).collect(Collectors.toList());
//        //加入用户的按钮权限组
//        dict.put("perms", perms);
//        return dict;
//    }

    /**
     * 验证手机号码
     *
     * @param userId
     * @param mobile
     * @return
     */
    @Override
    public Boolean selectVerifyMobileNumber(Integer userId, String mobile) {
        EntityWrapper<DefaultSysUser> entityWrapper = new EntityWrapper<>();
        Optional.ofNullable(mobile).ifPresent(mb -> entityWrapper.eq("mobile", mobile));
        Optional.ofNullable(userId).ifPresent(id -> entityWrapper.ne("user_id", userId));
        return this.defaultSysUserMapper.selectCount(entityWrapper) > 0;
    }

    /**
     * 获取经营者id
     *
     * @param dealerName 经营商姓名
     * @return
     */
    @Override
    public Long getDealerId(String dealerName) {
        return this.defaultSysUserMapper.selectDealerId(dealerName);
    }

    /**
     * 获取用户id
     *
     * @param
     * @return
     */
    @Override
    public List<Long> getUserId() {
        return this.defaultSysUserMapper.selectUserId();
    }

    @Override
    public DefaultSysUser selectUserById(Integer userId) {
        return this.defaultSysUserMapper.selectById(userId);
    }

    /**
     * 更新用户信息
     *
     * @param sysUser
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public String updateUser(DefaultSysUser sysUser) {
        DefaultSysUser userLoad = this.selectById(sysUser.getUserId());
        boolean updateSuccess = this.updateById(sysUser);
        if (!updateSuccess) {
            throw new IllegalArgumentException("修改用户信息失败");
        }
        this.redisRepository.del(sysUser.getUserId().toString());
        return sysUser.getUserId().toString();
    }

    /**
     * 获取指定部门下的所有用户
     *
     * @param deptIdList
     * @return
     */
    @Override
    public List<DefaultSysUser> listUsersByDeptIdList(List<Integer> deptIdList) {
        EntityWrapper<DefaultSysUser> entityWrapper = new EntityWrapper<>();
        entityWrapper.in("dept_id", deptIdList);
        return defaultSysUserMapper.selectList(entityWrapper);
    }

    /**
     * 判断用户是否存在指定角色
     *
     * @param userId
     * @param roleSign
     * @return
     */
    @Override
    public boolean isExistAppointRole(Long userId, String roleSign) {
        Integer count = defaultSysUserMapper.isExistAppointRole(userId, roleSign);
        return count > 0;
    }

    /**
     * app保存用户
     *
     * @param sysUser
     * @param currentUser
     * @return
     */
    @Override
    public void appSaveUser(DefaultSysUser sysUser, DefaultSysUser currentUser) {
        sysUser.setUserIdCreate(currentUser.getUserId());
        sysUser.setUsername(sysUser.getMobile());
        sysUser.setPassword(DigestUtils.md5Hex(sysUser.getMobile()));
        if (Objects.isNull(sysUser.getDeptId())) {
            sysUser.setDeptId(currentUser.getDeptId());
        }
        this.insert(sysUser);
    }


    /**
     * @param userDTO
     * @param sysUser
     * @param page
     * @return
     */
    @Override
    public Result getUserAllByUserDTO(UserDTO userDTO, DefaultSysUserDetails sysUser, Page page) {
        final boolean isNotAll = Optional.ofNullable(userDTO.getIsAll()).orElse(true);
        Integer deptId = Optional.ofNullable(userDTO.getDeptId()).orElse(sysUser.getDeptId());
        if (ObjectUtils.isEmpty(deptId)) {
            return ResultFactory.errorOf("部门ID为空");
        }
        List<SysDept> list = this.sysDeptService.getDeptChildrenByParentId(deptId, null);
        String deptIds = "'" + StringUtils.join(list.stream().map(SysDept::getDeptId).collect(Collectors.toList()), "','") + "'";
        if (StringUtils.isBlank(deptIds)) {
            deptIds = sysUser.getDeptId().toString();
        }
        userDTO.setDeptIds(deptIds);
        if (!isNotAll) {
            userDTO.setUserId(sysUser.getUserId());
        }
        if (StringUtils.isNotBlank(userDTO.getRoleIds())) {
            userDTO.setRoleIdList(Splitter.on(",").omitEmptyStrings().splitToList(userDTO.getRoleIds()));
        }
        return ResultFactory.successOf(this.selectUser(userDTO, page));
    }
}
