package com.wu.smart.acw.core.domain.uo;

import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * API 下联 Method 表
 */
@Data
@Accessors(chain = true)
public class ApiDownLinkMethodUo {

    /**
     * 主键
     */
    @LazyTableFieldId
    private Long id;
    /**
     * api Id
     */
    private Long apiId;

    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 方法ID
     */
    private Long methodId;


}
