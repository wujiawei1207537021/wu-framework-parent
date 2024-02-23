package com.wu.framework.authorization.platform.provider;


import com.wu.framework.authorization.platform.model.role.Role;
import com.wu.framework.authorization.platform.model.role.menu.RoleMenu;
import com.wu.framework.database.lazy.web.plus.AbstractLazyCrudProvider;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe : 角色提供者
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/30 18:31
 */
@Tag(name = "角色提供者")
//@EasyController("/role")
public class RoleProvider extends AbstractLazyCrudProvider<Role, Role, Long> {
    private final LazyLambdaStream lazyLambdaStream;

    public RoleProvider(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;
    }

    /**
     * describe  新增
     *
     * @param model 实体对象
     * @return Result<S>
     * @author Jia wei Wu
     * @date 2022/1/30 19:00
     **/
    @Override
    public Result<Role> save(Role model) {
        return super.save(model);
    }


    @ApiOperation(value = "新增")
    @PostMapping("/saveRoleMenu")
    Result saveRoleMenu(@RequestBody Role role, @RequestParam List<Long> menuIds) {
        lazyLambdaStream.upsert(role);
        List<RoleMenu> roleMenuList = menuIds.stream().map(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(role.getId());
            return roleMenu;
        }).collect(Collectors.toList());
        lazyLambdaStream.insert(roleMenuList);
        return ResultFactory.successOf();
    }
}
