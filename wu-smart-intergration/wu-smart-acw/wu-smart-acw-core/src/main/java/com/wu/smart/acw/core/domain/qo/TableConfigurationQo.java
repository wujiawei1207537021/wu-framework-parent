package com.wu.smart.acw.core.domain.qo;

import com.wu.framework.inner.layer.data.JavaBasicTypeDefaultValue;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 表配置
 */
@Data
@Accessors(chain = true)
public class TableConfigurationQo {

    @Schema(description = "项目ID")
    private Long projectId = 1L;

    @Schema(description = "表中文名称")
    private String tableZhName;
    @Schema
    private List<FieldConfig> fields;


    @Data
    public static class FieldConfig {
        private String name;
        private JavaBasicTypeDefaultValue type;
    }


}


