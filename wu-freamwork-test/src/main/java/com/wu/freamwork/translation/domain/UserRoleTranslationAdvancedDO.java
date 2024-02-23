package com.wu.freamwork.translation.domain;

import com.wu.framework.inner.lazy.stereotype.LazyFieldStrategy;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe : 用户角色信息转换测试表
 * @date : 2021/5/15 2:45 下午
 */
@LazyTable(tableName = "user_role_translation_advanced", comment = "用户角色信息转换测试表")
@Data
public class UserRoleTranslationAdvancedDO implements Serializable {


    private Long id;

    private String name;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted")
    @LazyTableField(name = "is_deleted", comment = "是否删除", columnType = "tinyint(1)")
    private Boolean isDeleted;
    /**
     * 操作时间
     */
    @LazyTableField(comment = "操作时间", notNull = true, defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", upsertStrategy = LazyFieldStrategy.NEVER)
    private Date updateTime;

    /**
     * 操作时间
     */
    @LazyTableField(comment = "操作时间", notNull = true, defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", upsertStrategy = LazyFieldStrategy.NEVER)
    private Date createTime;
}
