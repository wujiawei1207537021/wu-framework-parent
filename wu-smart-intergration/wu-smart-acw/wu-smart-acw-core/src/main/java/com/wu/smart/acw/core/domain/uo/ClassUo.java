package com.wu.smart.acw.core.domain.uo;


import com.wu.framework.inner.layer.data.JavaClassType;
import com.wu.framework.inner.layer.stereotype.LayerClass;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class ClassUo {

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
    private List<ClassUo> annotationList;
    /**
     * 类 父类
     */
    private ClassUo parentClass;
    /**
     * 类 接口
     */
    private ClassUo interfaceClass;
    /***
     * 包含的类
     */
    private List<ClassUo> includedClasses;

}
