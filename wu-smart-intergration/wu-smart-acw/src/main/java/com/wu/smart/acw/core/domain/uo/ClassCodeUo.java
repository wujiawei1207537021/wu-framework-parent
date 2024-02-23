package com.wu.smart.acw.core.domain.uo;


import com.wu.framework.inner.layer.data.JavaClassType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stereotype.LazyTableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class ClassCodeUo {

    /**
     * 主键
     */
    private Long id;
    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 包名
     */
    private String packageName;
    /**
     * 类名
     */
    private String name;
    /**
     * 类 类型
     */
    private JavaClassType type;

    /**
     * 类注解
     */
    @LazyTableField
    private List<ClassCodeUo> annotationList;
    /**
     * 类 父类
     */
    private ClassCodeUo parentClass;
    /**
     * 类 接口
     */
    private ClassCodeUo interfaceClass;

}
