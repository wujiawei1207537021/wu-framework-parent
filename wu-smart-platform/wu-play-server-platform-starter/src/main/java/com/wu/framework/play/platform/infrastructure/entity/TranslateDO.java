package com.wu.framework.play.platform.infrastructure.entity;

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
import java.time.LocalDateTime;
import java.lang.String;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import java.lang.Long;
/**
 * describe 翻译数据 
 *
 * @author Jia wei Wu
 * @date 2024/02/19 10:26 上午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity 
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "translate",comment = "翻译数据")
@Schema(title = "translate",description = "翻译数据")
public class TranslateDO {


    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    @LazyTableField(name="create_time",comment="创建时间",defaultValue="CURRENT_TIMESTAMP",upsertStrategy = LazyFieldStrategy.NEVER,columnType="datetime",extra="")
    private LocalDateTime createTime;

    /**
     * 
     * 源文本类型
     */
    @Schema(description ="源文本类型",name ="from",example = "")
    @LazyTableField(name="from",comment="源文本类型",columnType="varchar(10)")
    private String from;

    /**
     * 
     * 数据ID
     */
    @Schema(description ="数据ID",name ="id",example = "")
    @LazyTableFieldId(name = "id", comment = "数据ID")
    private Long id;

    /**
     * 
     * 源文本
     */
    @Schema(description ="源文本",name ="sourceWord",example = "")
    @LazyTableField(name="source_word",comment="源文本",columnType="varchar(255)")
    private String sourceWord;

    /**
     * 
     * 目标文本类型
     */
    @Schema(description ="目标文本类型",name ="to",example = "")
    @LazyTableField(name="to",comment="目标文本类型",columnType="varchar(10)")
    private String to;

    /**
     * 
     * 翻译后的文本
     */
    @Schema(description ="翻译后的文本",name ="translateWord",example = "")
    @LazyTableField(name="translate_word",comment="翻译后的文本",columnType="text")
    private String translateWord;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    @LazyTableField(name="update_time",comment="更新时间",defaultValue="CURRENT_TIMESTAMP",upsertStrategy = LazyFieldStrategy.NEVER,columnType="datetime",extra=" on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

}