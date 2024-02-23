package com.wu.smart.acw.server.application.dto;

import lombok.Data;

/**
 * description 数据库相同的字段
 *
 * @author 吴佳伟
 * @date 2023/08/10 15:02
 */
@Data
public class SchemaSameColumnDTO {

    /**
     * 字段名
     */
    private String  columnName;
    /**
     * 数量
     */
    private Long num;
    /**
     * 是否全表都有
     */
    private Boolean allTableHas;
}
