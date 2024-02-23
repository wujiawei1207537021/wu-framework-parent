package com.wu.freamwork.lazytable.translation.domain;

import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.LazyTableTranslationOneField;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.api.LazyTableTranslationOneAPI;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.endpoint.LazyTranslationTableEndpoint;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * lazy 框架跨表映射 用户
 */
@Data
@LazyTable(tableName = "lazy_table_user", comment = "lazy 框架跨表映射 用户")
public class LazyTableUser {
    @LazyTableFieldId(value = "id", idType = LazyTableFieldId.IdType.AUTOMATIC_ID)
    private Integer id;

    @LazyTableFieldUnique(value = "username")
    private String username;

    @LazyTableField(value = "birthday")
    private String birthday;

    @LazyTableField("sex")
    private String sex;

    @LazyTableTranslationOneField(translationSourceName = "addressId", translationTargetName = "id", columnList = "addressName",
            translationTargetTableName = "lazy_table_address", type = LazyTranslationTableEndpoint.Type.COLUMN,
            lazyTranslationAPIClass = LazyTableTranslationOneAPI.class)
    @LazyTableField(exist = false)
    private String address;

    @LazyTableFieldUnique(value = "age")
    private Integer age;

    @LazyTableFieldUnique()
    private Integer ageType;


    @LazyTableField(comment = "地址ID")
    private Integer addressId;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    @LazyTableField(name = "is_deleted", comment = "是否删除", columnType = "tinyint(1)")
    private Boolean isDeleted;
}
