package com.wuframework.system.common.annotation;

import com.wuframework.system.component.listen.SystemInit;

/**
 * @ Description   :  权限控制注解
 * @ Author        :  wujiawei
 * @ CreateDate    :  2020/1/21 0021 14:11
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2020/1/21 0021 14:11
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 * 角色注解优先 {@link com.wuframework.shiro.annotation.RequiredRole}
 * 逻辑处理{@link com.wuframework.system.component.web.interceptors.AccessPermissionInterceptor}
 * 初始化 {@link SystemInit#intiSysPermission()}
 */

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
@Inherited
public @interface RequiredPermission {
}
