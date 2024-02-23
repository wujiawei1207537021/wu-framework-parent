package com.wu.framework.dynamic.iframe.platform.infrastructure.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import com.wu.framework.inner.lazy.stereotype.LazyTableIndex;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.layer.stereotype.LayerField.LayerFieldType;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Map;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import java.lang.Long;
import java.lang.Boolean;
import java.lang.String;
/**
 * describe 动态Iframe 
 *
 * @author Jia wei Wu
 * @date 2023/07/27 10:05 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity 
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "dynamic_iframe",comment = "动态Iframe")
@Schema(title = "dynamic_iframe",description = "动态Iframe")
public class DynamicIframeDO {


    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    @LazyTableField(name="create_time",comment="创建时间",defaultValue="CURRENT_TIMESTAMP",columnType="datetime",extra=" on update CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    /**
     * 
     * 动态参数
     */
    @Schema(description ="动态参数",name ="dynamicParameter",example = "")
    @LazyTableField(name="dynamic_parameter",comment="动态参数",columnType="json")
    private Map dynamicParameter;

    /**
     * 
     * 
     */
    @Schema(description ="",name ="id",example = "")
    @LazyTableFieldId(name = "id", comment = "")
    private Long id;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    @LazyTableField(name="is_deleted",comment="是否删除",defaultValue="'0'",columnType="tinyint(1)")
    private Boolean isDeleted;

    /**
     * 
     * 是否站内
     */
    @Schema(description ="是否站内",name ="isStation",example = "")
    @LazyTableField(name="is_station",comment="是否站内",defaultValue="'0'",columnType="tinyint(1)")
    private Boolean isStation;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    @LazyTableField(name="update_time",comment="更新时间",defaultValue="CURRENT_TIMESTAMP",columnType="datetime",extra=" on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

    /**
     * 
     * iframe地址
     */
    @Schema(description ="iframe地址",name ="url",example = "")
    @LazyTableField(name="url",comment="iframe地址",columnType="varchar(255)")
    private String url;

}