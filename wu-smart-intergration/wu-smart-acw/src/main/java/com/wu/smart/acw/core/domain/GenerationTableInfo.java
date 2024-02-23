package com.wu.smart.acw.core.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 生成表信息
 */
@Accessors(chain = true)
@Data
public class GenerationTableInfo {
    private String name;

    private String desc;


}
