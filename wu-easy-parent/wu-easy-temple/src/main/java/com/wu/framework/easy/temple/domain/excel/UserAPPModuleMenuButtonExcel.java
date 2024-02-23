package com.wu.framework.easy.temple.domain.excel;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


@Accessors(chain = true)
@Data
public class UserAPPModuleMenuButtonExcel {


    @Schema(description = "用户名", name = "userName", example = "")
    private String userName;

    @Schema(description = "手机", name = "mobile", example = "")
    private String mobile;

    @Schema(description = "Email", name = "email", example = "")
    private String email;

    @Schema(description = "职位", name = "position", example = "")
    private String position;



    @Schema(description = "用户所有权限数据", name = "authByApplicationList", example = "")
    private List<AuthByApplication> authByApplicationList;

    /**
     * 应用权限
     */
    @Data
    public static class AuthByApplication {

        @Schema(description = "应用编码", name = "applicationCode", example = "")
        private String applicationCode;

        @Schema(description = "应用名称", name = "applicationName", example = "")
        private String applicationName;

        @Schema(description = "应用所有模块权限数据", name = "authByModuleList", example = "")
        private List<AuthByModule> authByModuleList;
    }

    /**
     * 模块权限
     */
    @Data
    public static class AuthByModule {

        @Schema(description = "模块id", name = "moduleId", example = "")
        private Long moduleId;

        @Schema(description = "模块名称", name = "moduleName", example = "")
        private String moduleName;

        @Schema(description = "应用所有菜单权限数据", name = "authByMenuList", example = "")
        private List<AuthByMenu> authByMenuList;
    }

    /**
     * 菜单权限
     */
    @Data
    public static class AuthByMenu {

        @Schema(description = "菜单id", name = "menuId", example = "")
        private Long menuId;

        @Schema(description = "菜单名称", name = "menuName", example = "")
        private String menuName;

        @Schema(description = "应用所有按钮权限数据", name = "authByButtonList", example = "")
        private List<AuthByButton> authByButtonList;
    }

    /**
     * 按钮权限
     */
    @Data
    public static class AuthByButton {

        @Schema(description = "按钮id", name = "buttonId", example = "")
        private Long buttonId;

        @Schema(description = "按钮名称", name = "buttonName", example = "")
        private String buttonName;
    }
}
