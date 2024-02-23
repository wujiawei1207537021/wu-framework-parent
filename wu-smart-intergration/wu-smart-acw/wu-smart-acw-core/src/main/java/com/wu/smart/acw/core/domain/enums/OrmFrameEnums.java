package com.wu.smart.acw.core.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrmFrameEnums {

    UPSERT("UPSERT", "1.0.5"),
    MYBATIS("MYBATIS", "2.0"),
    JPA("JPA", "2.0");
    // 名称
    private String name;
    private String version;
}
