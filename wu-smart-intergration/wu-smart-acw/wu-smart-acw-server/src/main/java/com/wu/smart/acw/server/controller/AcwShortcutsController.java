package com.wu.smart.acw.server.controller;


import com.wu.framework.authorization.platform.model.menu.Menu;
import com.wu.framework.authorization.platform.model.role.Role;
import com.wu.framework.authorization.platform.model.sys.user.SysUser;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.core.domain.uo.AcwInstanceUo;
import com.wu.smart.acw.core.domain.uo.AcwSchemaUo;
import com.wu.smart.acw.core.domain.uo.AcwTableColumnUo;
import com.wu.smart.acw.core.domain.uo.AcwTableUo;
import com.wu.smart.acw.server.application.dto.ShortcutsDataDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "快捷方式")
@EasyController("/shortcuts")
public class AcwShortcutsController {

    private final LazyLambdaStream lazyLambdaStream;

    public AcwShortcutsController(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;
    }

    /**
     * 查询快捷方式数据
     *
     * @return
     */
    @ApiOperation(value = "查询快捷方式数据")
    @GetMapping("/data")
    public Result<ShortcutsDataDTO> data() {
        // 查询菜单数量、用户数量、角色数量
        ShortcutsDataDTO shortcutsDataDTO = new ShortcutsDataDTO();
        Long menuNum = lazyLambdaStream.count(LazyWrappers.
                <Menu>lambdaWrapper()
                .eq(Menu::getIsDeleted, false)
        );
        Long sysUserNum = lazyLambdaStream.count(LazyWrappers.
                <SysUser>lambdaWrapper()
                .eq(SysUser::getIsDeleted, false)
        );
        Long roleNum = lazyLambdaStream.count(LazyWrappers.
                <Role>lambdaWrapper()
                .eq(Role::getIsDeleted, false)
        );
        Long instanceNum = lazyLambdaStream.count(LazyWrappers.
                <AcwInstanceUo>lambdaWrapper()
                .eq(AcwInstanceUo::getIsDeleted, false)
        );
        Long schemaNum = lazyLambdaStream.count(LazyWrappers.
                <AcwSchemaUo>lambdaWrapper()
                .eq(AcwSchemaUo::getIsDeleted, false)
        );
        Long tableNum = lazyLambdaStream.count(LazyWrappers.
                <AcwTableUo>lambdaWrapper()
                .eq(AcwTableUo::getIsDeleted, false)
        );
        Long tableColumnNum = lazyLambdaStream.count(LazyWrappers.
                <AcwTableColumnUo>lambdaWrapper()
                .eq(AcwTableColumnUo::getIsDeleted, false)
        );
        shortcutsDataDTO.setMenuNum(menuNum);
        shortcutsDataDTO.setUserNum(sysUserNum);
        shortcutsDataDTO.setRoleNum(roleNum);
        shortcutsDataDTO.setInstanceNum(instanceNum);
        shortcutsDataDTO.setSchemaNum(schemaNum);
        shortcutsDataDTO.setTableNum(tableNum);
        shortcutsDataDTO.setTableColumnNum(tableColumnNum);

        return ResultFactory.successOf(shortcutsDataDTO);
    }

}
