package com.wuframework.system.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Deprecated
public enum RoleSignEnums {
    SUPER_ADMIN("super_admin", "超级管理员");
    private String sign;
    private String name;
}
