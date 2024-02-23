package com.wu.framework.authorization.platform.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDTO
 **/
@Data
@Accessors(chain = true)
@Schema(title = "dictionary_data_command", description = "")
public class DictionaryDataDTO {


    /**
     * 数据编码
     */
    @Schema(description = "数据编码", name = "code")
    private String code;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    private LocalDateTime createTime;

    /**
     * 描述
     */
    @Schema(description = "描述", name = "description")
    private String description;

    /**
     * 字典编码
     */
    @Schema(description = "字典编码", name = "dictionaryCode")
    private String dictionaryCode;

    /**
     * id
     */
    @Schema(description = "id", name = "id")
    private Long id;

    /**
     * 数据名称
     */
    @Schema(description = "数据名称", name = "name")
    private String name;

    /**
     * 父节点编码
     */
    @Schema(description = "父节点编码", name = "pcode")
    private String pcode;

    /**
     * 排序值
     */
    @Schema(description = "排序值", name = "sortValue")
    private String sortValue;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    private LocalDateTime updateTime;

}