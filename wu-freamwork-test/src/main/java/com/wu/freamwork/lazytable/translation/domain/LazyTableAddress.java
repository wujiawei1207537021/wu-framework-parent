package com.wu.freamwork.lazytable.translation.domain;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * lazy 框架跨表映射 地址
 */
@Data
@LazyTable(tableName = "lazy_table_address", comment = "lazy 框架跨表映射 地址")
public class LazyTableAddress {
    @LazyTableFieldId(value = "id", idType = LazyTableFieldId.IdType.AUTOMATIC_ID)
    private Integer id;

    @LazyTableFieldUnique(value = "address_name")
    private String addressName;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    @LazyTableField(name = "is_deleted", comment = "是否删除", columnType = "tinyint(1)")
    private Boolean isDeleted;

}
