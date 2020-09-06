package com.wuframework.system.serivce.def;

import com.wuframework.pojo.qo.UniversalSearchQO;
import com.wuframework.response.Result;
import com.wuframework.response.ResultFactory;
import com.wuframework.system.common.entity.SysPermission;
import com.wuframework.system.persistence.jpa.SysPermissionJpaRepository;
import com.wuframework.system.serivce.SysPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("defaultSysPermissionService")
public class DefaultSysPermissionService implements SysPermissionService {
    @Resource
    private SysPermissionJpaRepository sysPermissionJpaRepository;
    /**
     * 添加权限
     * @param sysPermission
     * @return
     */
    @Override
    public Result save(SysPermission sysPermission) {
        return ResultFactory.successOf(sysPermission.insert());
    }

    /**
     * 更新权限
     * @param sysPermission
     * @return
     */
    @Override
    public Result update(SysPermission sysPermission) {
        return ResultFactory.successOf(sysPermission.updateById());
    }

    /**
     * 查询权限
     * @param universalSearchQO
     * @return
     */
    @Override
    public Result queryList(UniversalSearchQO universalSearchQO) {
       List<SysPermission> sysPermissionList= sysPermissionJpaRepository.queryList(universalSearchQO);
        return ResultFactory.successOf(sysPermissionList);
    }
}
