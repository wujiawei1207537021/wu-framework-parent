package com.wu.smart.acw.core.domain;

import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 生成表信息
 */
@Accessors(chain = true)
@Data
public class GenerationTableInfo {
    private String name;

    @LazyTableField("`desc`")
    private String desc;


}
