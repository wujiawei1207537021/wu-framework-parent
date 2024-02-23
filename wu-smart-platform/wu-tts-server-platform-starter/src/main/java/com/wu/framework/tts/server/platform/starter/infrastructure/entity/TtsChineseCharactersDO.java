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
import java.lang.String;
import java.io.File;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import java.lang.Long;
import java.lang.Boolean;
import java.time.LocalDateTime;
import java.lang.Integer;
/**
 * describe tts 中文 
 *
 * @author Jia wei Wu
 * @date 2023/11/18 07:22 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity 
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "tts_chinese_characters",comment = "tts 中文")
@Schema(title = "tts_chinese_characters",description = "tts 中文")
public class TtsChineseCharactersDO {


    /**
     * 
     * 拼音
     */
    @Schema(description ="拼音",name ="pinYin",example = "")
    @LazyTableField(name="pin_yin",comment="拼音",columnType="varchar(255)")
    private String pinYin;

    /**
     * 
     * 笔划
     */
    @Schema(description ="笔划",name ="strokes",example = "")
    @LazyTableField(name="strokes",comment="笔划",columnType="text")
    private String strokes;

    /**
     * 
     * 部首
     */
    @Schema(description ="部首",name ="radicals",example = "")
    @LazyTableField(name="radicals",comment="部首",columnType="text")
    private String radicals;

    /**
     * 
     * 更多
     */
    @Schema(description ="更多",name ="more",example = "")
    @LazyTableField(name="more",comment="更多",columnType="text")
    private String more;

    /**
     * 
     * 繁体字
     */
    @Schema(description ="繁体字",name ="oldWord",example = "")
    @LazyTableField(name="old_word",comment="繁体字",columnType="text")
    private String oldWord;

    /**
     * 
     * 例子
     */
    @Schema(description ="例子",name ="explanation",example = "")
    @LazyTableField(name="explanation",comment="例子",columnType="text")
    private String explanation;

    /**
     * 
     * 汉字
     */
    @Schema(description ="汉字",name ="word",example = "")
    @LazyTableFieldUnique(name="word",comment="汉字",columnType="varchar(255)")
    private String word;

    /**
     * 
     * 主键
     */
    @Schema(description ="主键",name ="id",example = "")
    @LazyTableFieldId(name = "id", comment = "主键")
    private Long id;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    @LazyTableField(name="is_deleted",comment="是否删除",defaultValue="'0'",upsertStrategy = LazyFieldStrategy.NEVER,columnType="tinyint(1)")
    private Boolean isDeleted;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    @LazyTableField(name="create_time",comment="创建时间",defaultValue="CURRENT_TIMESTAMP",upsertStrategy = LazyFieldStrategy.NEVER,columnType="datetime",extra="")
    private LocalDateTime createTime;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    @LazyTableField(name="update_time",comment="更新时间",defaultValue="CURRENT_TIMESTAMP",upsertStrategy = LazyFieldStrategy.NEVER,columnType="datetime",extra=" on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;


}