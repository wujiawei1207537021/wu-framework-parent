package com.wu.framework.authorization.annotation;

import com.wu.framework.authorization.web.interceptors.AccessPermissionInterceptorAbstract;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author :  wujiawei
 * @ Description   :  角色控制注解
 * @ CreateDate    :  2020/1/21 0021 14:11
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2020/1/21 0021 14:11
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 * {@link AccessPermissionInterceptorAbstract}
 * orRoles 优先级大于 roles
 */

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME) // 运行时有效
@Documented
public @interface RequiredRole {

    @AliasFor(attribute = "roles")
    String[] value() default {};

    /**
     * 访问需要的角色
     *
     * @return
     */
    @AliasFor(attribute = "value")
    String[] roles() default {};

    /**
     * 访问需要的角色(只满足一个即可 优先级大于 roles)
     *
     * @return
     */
    String[] orRoles() default {};

}
