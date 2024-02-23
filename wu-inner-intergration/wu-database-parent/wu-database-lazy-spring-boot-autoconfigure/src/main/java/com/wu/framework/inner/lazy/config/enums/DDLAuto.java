package com.wu.framework.inner.lazy.config.enums;

import lombok.AllArgsConstructor;

/**
 * ddl 操作枚举
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/23 7:41 下午
 */
@AllArgsConstructor
public enum DDLAuto {
    /**
     * 空的不做任何操作
     */
    NONE,
    /**
     * 更新操作
     */
    UPDATE,
    /**
     * 创建操作
     */
    CREATE,
    /**
     * 完善表
     */
    PERFECT;
}