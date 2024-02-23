package com.wu.framework.tts.server.platform.starter.infrastructure.entity;

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
import java.lang.Long;
import java.lang.String;
import java.lang.Boolean;
import java.time.LocalDateTime;
/**
 * describe tts 音色 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity 
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "tts_timbre",comment = "tts 音色")
@Schema(title = "tts_timbre",description = "tts 音色")
public class TtsTimbreDO {


    /**
     * 
     * 主键ID
     */
    @Schema(description ="主键ID",name ="id",example = "")
    @LazyTableFieldId(name="id",comment="主键ID",idType = LazyTableFieldId.IdType.INPUT_ID)
    private Long id;

    /**
     * 
     * 音色名称
     */
    @Schema(description ="音色名称",name ="name",example = "")
    @LazyTableField(name="name",comment="音色名称",notNull=true,columnType="varchar(255)")
    private String name;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    @LazyTableField(name="is_deleted",comment="是否删除",notNull=true,defaultValue="'0'",upsertStrategy = LazyFieldStrategy.NEVER,columnType="tinyint")
    private Boolean isDeleted;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    @LazyTableFieldCreateTime(name="create_time",upsertStrategy = LazyFieldStrategy.NEVER)
    private LocalDateTime createTime;

    /**
     * 
     * 修改时间
     */
    @Schema(description ="修改时间",name ="updateTime",example = "")
    @LazyTableFieldUpdateTime(name="update_time",comment="修改时间",notNull=true,upsertStrategy = LazyFieldStrategy.NEVER)
    private LocalDateTime updateTime;

    /**
     * 
     * 音色code
     */
    @Schema(description ="音色code",name ="code",example = "")
    @LazyTableFieldUnique(name="code",comment="音色code",notNull=true,columnType="varchar(255)")
    private String code;

}