package com.wu.smart.acw.core.domain.uo;

import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 表和class的关联表
 */
@Accessors(chain = true)
@Data
public class TableClassUo {

    /**
     * 主键
     */
    @LazyTableFieldId
    private Long id;
    /**
     * class id
     */
    @LazyTableFieldUnique
    private Long classId;
    /**
     * project id
     */
    @LazyTableFieldUnique
    private Long projectId;
    /**
     * table name
     */
    @LazyTableFieldUnique
    private String tableName;

}
