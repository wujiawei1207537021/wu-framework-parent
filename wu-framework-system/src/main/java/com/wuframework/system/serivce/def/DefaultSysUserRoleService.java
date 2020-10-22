package com.wuframework.system.serivce.def;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wuframework.response.exceptions.CustomException;
import com.wuframework.system.common.entity.SysRole;
import com.wuframework.system.common.entity.SysUserRole;
import com.wuframework.system.persistence.jpa.SysRoleJpaRepository;
import com.wuframework.system.persistence.mapper.SysUserRoleMapper;
import com.wuframework.system.serivce.SysUserRoleService;
import com.wuframework.system.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户与角色对应关系 服务实现类
 * </p>
 *
 * @author Xiongxz
 * @since 2018-11-08
 */
@Slf4j
@Service
public class DefaultSysUserRoleService extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Resource
    private SysRoleJpaRepository sysRoleJpaRepository;

    /**
     * 根据用户存储其拥有的角色
     *
     * @param userId     用户编号
     * @param roleIdList 角色列表
     * @return
     */
    @Override
    public Integer saveByUserId(Integer userId, List<String> roleIdList) {
        //获取用户拥有的所有角色
        List<SysUserRole> userRoleList = listByUserId(userId);
        List<String> oldRoleIdList = userRoleList.stream().map(u -> u.getRoleId().toString()).collect(Collectors.toList());
        Map<String, List<String>> map = CommonUtils.different(oldRoleIdList, roleIdList);
        List<String> commonList = map.get("common");
        int row = commonList.size();

        List<String> addList = map.get("add");
        for (String roleId : addList) {
            row += assignRoleToUser(userId, Integer.valueOf(roleId));
        }

        List<String> delList = map.get("del");
        delList.forEach(delRoleId -> deleteRoleFromUser(userId, Integer.valueOf(delRoleId)));
        return row;
    }

    /**
     * 根据角色存储角色所有用的用户
     *
     * @param roleId     角色编号
     * @param userIdList 用户列表
     * @return
     */
    @Override
    public Integer saveByRoleId(Integer roleId, List<String> userIdList) {
        List<SysUserRole> userRoleList = listByRoleId(roleId);
        List<String> oldUserIdList = userRoleList.stream().map(u -> u.getUserId().toString()).collect(Collectors.toList());
        Map<String, List<String>> map = CommonUtils.different(oldUserIdList, userIdList);
        List<String> commonList = map.get("common");
        int row = commonList.size();

        List<String> addList = map.get("add");
        for (String userId : addList) {
            row += assignRoleToUser(Integer.valueOf(userId), roleId);
        }

        List<String> delList = map.get("del");
        delList.forEach(delUserId -> deleteRoleFromUser(Integer.valueOf(delUserId), roleId));
        return row;
    }

    @Override
    public List<SysUserRole> listByUserId(Integer userId) {
        EntityWrapper<SysUserRole> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_id", userId);
        return this.sysUserRoleMapper.selectList(entityWrapper);
    }

    @Override
    public List<SysUserRole> listByRoleId(Integer roleId) {
        EntityWrapper<SysUserRole> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("role_id", roleId);
        return this.sysUserRoleMapper.selectList(entityWrapper);
    }

    @Override
    public List<String> listRoleSign(Integer userId) {
        return baseMapper.listRoleSign(userId);
    }

    /**
     * 统计该角色关联用户数
     *
     * @param roleId
     * @return
     */
    @Override
    public Integer countByRoleId(Long roleId) {
        EntityWrapper<SysUserRole> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("role_id", roleId);
        return this.sysUserRoleMapper.selectCount(entityWrapper);
    }

    @Override
    public Integer assignRoleToUser(Integer userId, Integer roleId) {
        SysUserRole userRoleAdd = new SysUserRole();
        userRoleAdd.setUserId(userId);
        userRoleAdd.setRoleId(roleId);
        return this.sysUserRoleMapper.insert(userRoleAdd);
    }

    /**
     * 分配指定角色标识给指定用户
     *
     * @param userId
     * @param roleSigns
     * @return
     */
    @Override
    public Integer assignRoleToUser(Integer userId, List<String> roleSigns) {
        List<SysRole> sysRoleList = sysRoleJpaRepository.findAllByRoleSignInAndStatus(roleSigns, 1);
        if (ObjectUtils.isEmpty(sysRoleList)) {
            throw new CustomException("当前角色标识符不存在");
        }
        if (sysRoleList.size() < roleSigns.size()) {
            //不存在的角色标识符
            List<String> tempSigns = sysRoleList.stream().map(sysRole -> {
                return sysRole.getRoleSign();
            }).collect(Collectors.toList());
            List<String> nonexistent = roleSigns.stream().filter(sigin -> tempSigns.contains(sigin)).collect(Collectors.toList());
            log.info("不存在的角色标识符=>{}", nonexistent);
        }
        List<SysUserRole> sysUserRoleList = new ArrayList<>();
        for (SysRole role : sysRoleList) {
            SysUserRole userRoleAdd = new SysUserRole();
            userRoleAdd.setRoleId(role.getRoleId());
            userRoleAdd.setUserId(userId);
            sysUserRoleList.add(userRoleAdd);
        }
        return baseMapper.insertBatchUserRole(sysUserRoleList);
    }

    @Override
    public void deleteRoleFromUser(Integer userId, Integer roleId) {
        EntityWrapper<SysUserRole> entityWrapperDel = new EntityWrapper<>();
        entityWrapperDel.eq("user_id", userId);
        entityWrapperDel.eq("role_id", roleId);
        this.sysUserRoleMapper.delete(entityWrapperDel);
    }
}
