package com.wu.framework.inner.lazy.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ORM 架构
 */

@Getter
@AllArgsConstructor
public enum OrmArchitecture {
    JPA,
    MYBATIS,
    LAZY
}