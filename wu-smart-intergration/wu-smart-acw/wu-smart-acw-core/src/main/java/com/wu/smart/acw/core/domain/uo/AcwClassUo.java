package com.wu.smart.acw.core.domain.uo;


import com.wu.framework.inner.layer.data.JavaClassType;
import com.wu.framework.inner.layer.stereotype.LayerClass;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Accessors(chain = true)
@Data
@LazyTable(engine = LazyTable.Engine.InnoDB,comment = "对应的class字节码")
public class AcwClassUo {

    /**
     * 主键
     */
    @LazyTableFieldId
    private Long id;
    /**
     * 项目id
     */
    @LazyTableFieldUnique
    private Long projectId;

    /**
     * 包名
     */
    @LazyTableFieldUnique
    private String packageName = "com.wu.smart.acw";
    /**
     * 类名
     */
    @LazyTableFieldUnique
    private String name;
    /**
     * 类 类型
     */
    private JavaClassType javaClassType;
    /**
     * 模块对应的 类型
     */
    private LayerClass.LayerType type;
    /**
     * 类注解
     */
    @LazyTableField
    private List<AcwClassUo> annotationList;
    /**
     * 类 父类
     */
    private AcwClassUo parentClass;
    /**
     * 类 接口
     */
    private AcwClassUo interfaceClass;
    /***
     * 包含的类
     */
    private List<AcwClassUo> includedClasses;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime")
    @LazyTableField(name = "create_time", comment = "创建时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = "on update CURRENT_TIMESTAMP")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime")
    @LazyTableField(name = "update_time", comment = "更新时间", defaultValue = "CURRENT_TIMESTAMP", columnType = "datetime", extra = "on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

}
