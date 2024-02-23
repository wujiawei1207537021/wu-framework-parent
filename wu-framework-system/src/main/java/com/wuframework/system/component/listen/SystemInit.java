package com.wuframework.system.component.listen;

import com.wu.framework.inner.swagger.annotation.CustomController;
import com.wuframework.shiro.util.ShiroContextUtil;
import com.wuframework.system.common.annotation.RequiredPermission;
import com.wuframework.system.common.entity.DefaultSysUser;
import com.wuframework.system.common.entity.SysPermission;
import com.wuframework.system.common.entity.SysRole;
import com.wuframework.system.common.entity.SysUserRole;
import com.wuframework.system.common.enums.PermissionTypeEnums;
import com.wuframework.system.persistence.jpa.SysPermissionJpaRepository;
import com.wuframework.system.persistence.jpa.SysRoleJpaRepository;
import com.wuframework.system.persistence.jpa.SysUserJpaRepository;
import com.wuframework.system.persistence.jpa.SysUserRoleJpaRepository;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * init 数据库
 */
@Slf4j
@Component
@Order()
public class SystemInit implements CommandLineRunner {

    @Resource
    private SysUserJpaRepository sysUserJpaRepository;

    @Resource
    private SysRoleJpaRepository sysRoleJpaRepository;

    @Resource
    private SysUserRoleJpaRepository sysUserRoleJpaRepository;

    @Resource
    private SysPermissionJpaRepository sysPermissionJpaRepository;


    @Override
    public void run(String... args) throws Exception {
        log.info("<---------数据库初始化数据开始---------->");
//        判断是否需要初始化数据库
        if (sysUserJpaRepository.count() == 0) {
//        添加用户
            DefaultSysUser defaultSysUser = new DefaultSysUser();
            defaultSysUser.setUsername("admin");
            defaultSysUser.setName("admin");
            defaultSysUser.setPassword(DigestUtils.md5Hex("admin"));
            defaultSysUser.setUserIdCreate(0);
            sysUserJpaRepository.save(defaultSysUser);
            log.info("<---------数据库初始化用户数据账号:admin 密码:admin---------->");
//        添加角色
            SysRole sysRole = new SysRole();
            sysRole.setRoleName("超级管理员");
            sysRole.setRoleSign("super_admin");
            sysRole.setUserIdCreate(0);
            sysRoleJpaRepository.save(sysRole);
//        给用户添加角色
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(sysRole.getRoleId());
            sysUserRole.setUserId(defaultSysUser.getUserId());
            sysUserRoleJpaRepository.save(sysUserRole);
        }
        //            初始化系统权限
        log.info("<---------初始化系统权限---------->");
        intiSysPermission();
        log.info("<---------数据库初始化数据结束---------->");

    }

    /***
     * 初始化控制器注解 并添加到数据库
     */
//    @PostConstruct
    public void intiSysPermission() {
        Map<String, Object> beansWithAnnotationMap = ShiroContextUtil.getApplicationContext().getBeansWithAnnotation(CustomController.class);
        List<SysPermission> sysPermissionList = new ArrayList<>();
        Class<? extends Object> clazz = null;
        for (Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()) {
            clazz = entry.getValue().getClass();//获取到实例对象的class信息
            // 获取类注解中Value
            Boolean classHasRequiredRoleAnnotation = clazz.getAnnotation(RequiredPermission.class) != null;
            CustomController classAnnotation = clazz.getAnnotation(CustomController.class);
            String[] classControllerValues = classAnnotation.value();
            Method[] methods = clazz.getMethods();
//            Class<? extends Object>  [] interfaces = clazz.getInterfaces();
//            方法上有权限注解
            for (Method method : methods) {
                //含有指定注解的方法
                if (!ObjectUtils.isEmpty(AnnotationUtils.findAnnotation(method, RequiredPermission.class)) | classHasRequiredRoleAnnotation) {
                    //当前支持的请求
                    for (PermissionTypeEnums permissionTypeEnmus : PermissionTypeEnums.values()) {
                        Annotation annotation = AnnotationUtils.findAnnotation(method, permissionTypeEnmus.getAClass());
                        if (!ObjectUtils.isEmpty(annotation)) {
                            ApiOperation apiOperation = AnnotationUtils.findAnnotation(method, ApiOperation.class);
                            String remark = "";
                            if (!ObjectUtils.isEmpty(apiOperation)) {
                                remark = apiOperation.value();
                            }
//                            System.out.println(annotation);
                            try {
                                Method method1 = permissionTypeEnmus.getAClass().getDeclaredMethod("value");
                                method1.setAccessible(true);
                                // 单个方法上的路径 （数组）
                                String[] methodControllerValues = (String[]) method1.invoke(annotation);
                                if (ObjectUtils.isEmpty(methodControllerValues)) {
                                    methodControllerValues = new String[]{""};
                                }
                                for (String classControllerValue : classControllerValues) {
                                    for (String methodControllerValue : methodControllerValues) {
                                        SysPermission sysPermission = new SysPermission();
                                        sysPermission.setPermissionRootPath(classControllerValue);
                                        sysPermission.setPermissionCompletePath(classControllerValue + methodControllerValue);
                                        sysPermission.setPermissionType(permissionTypeEnmus.name());
                                        sysPermission.setRemark(remark);
                                        sysPermissionList.add(sysPermission);
                                    }
                                }
//                                System.out.println(Arrays.toString(methodControllerValues));
                            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                                e.printStackTrace();
                            }


                        }
                    }

                }

            }
        }
        List<SysPermission> sysPermissionDBList = sysPermissionJpaRepository.findAll();
        List<String> permissionCompletePathWithTypes = sysPermissionDBList.stream().map(sysPermission -> {
            return sysPermission.getPermissionCompletePath() + sysPermission.getPermissionType();
        }).collect(Collectors.toList());
        List<SysPermission> saveSysPermissionList = sysPermissionList.stream().filter(sysPermission -> !permissionCompletePathWithTypes.contains(sysPermission.getPermissionCompletePath() + sysPermission.getPermissionType())).collect(Collectors.toList());
        if (ObjectUtils.isEmpty(saveSysPermissionList)) {
            return;
        }
        sysPermissionJpaRepository.saveAll(saveSysPermissionList);
        //        return ResultFactory.successOf(sysPermissionList);
    }
}
