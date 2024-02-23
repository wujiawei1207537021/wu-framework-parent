package com.wu.framework.doc.platform.application.command;

import com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyRemoveCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * describe  
 *
 * @author Jia wei Wu
 * @date 2023/06/03 05:08 下午
 * @see DefaultDDDLazyRemoveCommand
 **/
@Data
@Accessors(chain = true)
@Schema(title = "doc_pdf_merge_command",description = "")
public class DocPdfMergeCommand {


    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    private Boolean isDeleted;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="mergeUrl",example = "")
    private String mergeUrl;

    /**
     * 
     * null
     */
    @Schema(description ="null",name ="pdfUrlList",example = "")
    private List<String> pdfUrlList;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

}