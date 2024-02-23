package com.wuframework.system.common.enums;

import com.wuframework.response.enmus.WuEnums;
import com.wuframework.system.component.enmus.WuEnumsProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.lang.annotation.Annotation;

/**
 * CRUD  权限
 */
@AllArgsConstructor
@Getter
@WuEnumsProperty(value = "PermissionType", name = "权限")
public enum PermissionTypeEnums implements WuEnums {
    C(PostMapping.class, "保存"),
    R(GetMapping.class, "查询"),
    U(PutMapping.class, "更改"),
    D(DeleteMapping.class, "删除");

    private Class<? extends Annotation> aClass;

    private String desc;

    @Override
    public Object getCode() {
        return this.aClass;
    }
}
