package com.wu.smart.acw.server.infrastructure.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import com.wu.framework.inner.lazy.stereotype.LazyTableIndex;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.layer.stereotype.LayerField.LayerFieldType;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.lang.String;
import java.time.LocalDateTime;
/**
 * describe 客户端使用创建Java代码常用路径 
 *
 * @author Jia wei Wu
 * @date 2023/12/08 06:04 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity 
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "acw_client_java_path",comment = "客户端使用创建Java代码常用路径")
@Schema(title = "acw_client_java_path",description = "客户端使用创建Java代码常用路径")
public class AcwClientJavaPathDO {


    /**
     * 
     * 使用的路径
     */
    @Schema(description ="使用的路径",name ="absolutePath",example = "")
    @LazyTableFieldUnique(name="absolute_path",comment="使用的路径",notNull=true,columnType="varchar(255)")
    private String absolutePath;

    /**
     * 
     * 客户端ID
     */
    @Schema(description ="客户端ID",name ="clientId",example = "")
    @LazyTableFieldUnique(name="client_id",comment="客户端ID",columnType="varchar(50)")
    private String clientId;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    @LazyTableField(name="create_time",comment="创建时间",defaultValue="CURRENT_TIMESTAMP",upsertStrategy = LazyFieldStrategy.NEVER,columnType="datetime",extra="")
    private LocalDateTime createTime;

    /**
     * 
     * 主键
     */
    @Schema(description ="主键",name ="id",example = "")
    @LazyTableFieldUUId(name="id",comment="主键")
    private String id;

    /**
     * 
     * 常用的包名称
     */
    @Schema(description ="常用的包名称",name ="packageName",example = "")
    @LazyTableFieldUnique(name="package_name",comment="常用的包名称",columnType="varchar(255)")
    private String packageName;

    /**
     * 
     * 常用的数据库
     */
    @Schema(description ="常用的数据库",name ="schemaName",example = "")
    @LazyTableFieldUnique(name="schema_name",comment="常用的数据库",columnType="varchar(50)")
    private String schemaName;

    /**
     * 
     * 实例ID
     */
    @Schema(description ="实例ID",name ="instanceId",example = "")
    @LazyTableFieldUnique(name="instance_id",comment="实例ID",columnType="varchar(50)")
    private String instanceId;

}