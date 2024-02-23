package com.wu.smart.acw.core.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UIFrameEnums {
    VUE("VUE", "1.0.5"),
    ;
    // 名称
    private String name;
    private String version;
}
