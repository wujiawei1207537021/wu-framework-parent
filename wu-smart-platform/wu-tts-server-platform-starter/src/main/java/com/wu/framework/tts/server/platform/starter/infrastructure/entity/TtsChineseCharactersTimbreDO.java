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
import java.io.File;
import java.lang.Boolean;
import java.time.LocalDateTime;
/**
 * describe tts 中文对应制定音色数据 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity 
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "tts_chinese_characters_timbre",comment = "tts 中文对应制定音色数据")
@Schema(title = "tts_chinese_characters_timbre",description = "tts 中文对应制定音色数据")
public class TtsChineseCharactersTimbreDO {


    /**
     * 
     * 主键
     */
    @Schema(description ="主键",name ="id",example = "")
    @LazyTableFieldId(name="id",comment="主键")
    private Long id;

    /**
     * 
     * 汉字
     */
    @Schema(description ="汉字",name ="word",example = "")
    @LazyTableField(name="word",comment="汉字",notNull=true,columnType="varchar(255)")
    private String word;

    /**
     * 
     * 音色编码
     */
    @Schema(description ="音色编码",name ="timbreCode",example = "")
    @LazyTableField(name="timbre_code",comment="音色编码",notNull=true,columnType="varchar(255)")
    private String timbreCode;

    /**
     * 
     * 音色对应的声音数据
     */
    @Schema(description ="音色对应的声音数据",name ="voice",example = "")
    @LazyTableField(name="voice",comment="音色对应的声音数据",notNull=true,columnType="blob")
    private byte[] voice;

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
    @LazyTableField(name="create_time",comment="创建时间",notNull=true,defaultValue="CURRENT_TIMESTAMP",upsertStrategy = LazyFieldStrategy.NEVER,columnType="datetime",extra="")
    private LocalDateTime createTime;

    /**
     * 
     * 修改时间
     */
    @Schema(description ="修改时间",name ="updateTime",example = "")
    @LazyTableField(name="update_time",comment="修改时间",notNull=true,defaultValue="CURRENT_TIMESTAMP",upsertStrategy = LazyFieldStrategy.NEVER,columnType="datetime",extra="")
    private LocalDateTime updateTime;

}