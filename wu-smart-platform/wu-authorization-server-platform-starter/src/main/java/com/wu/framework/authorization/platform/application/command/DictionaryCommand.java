package com.wu.framework.authorization.platform.application.command;

import com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyRemoveCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe
 *
 * @author Jia wei Wu
 * @date 2023/05/17 11:19 晚上
 * @see DefaultDDDLazyRemoveCommand
 **/
@Data
@Accessors(chain = true)
@Schema(title = "dictionary_command", description = "")
public class DictionaryCommand {


    /**
     * 字典编码
     */
    @Schema(description = "字典编码", name = "code")
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
     * id
     */
    @Schema(description = "id", name = "id")
    private Long id;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称", name = "name")
    private String name;

    /**
     * 类型
     */
    @Schema(description = "类型", name = "type")
    private String type;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    private LocalDateTime updateTime;

}