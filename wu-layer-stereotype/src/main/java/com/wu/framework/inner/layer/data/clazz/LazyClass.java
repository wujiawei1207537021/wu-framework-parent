package com.wu.framework.inner.layer.data.clazz;


import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class LazyClass {
    // 包名称
    private String packageName;

    // class 完整名称
    private String name;

    // 是否是私有的
    private boolean isPublic;

    //  类类型
    private ClassType classType;

    // class 绝对路径
    private String readLocalSrcMainClassPath;

    // 总行数
    private Integer totalLineNum;

    //   文件行内容
    private Map<Integer/*行*/, String/*行内容*/> lineContent;

    // 导出的class
    private List<String> importClasses;


    // 获取注解
    private List<String> annotations;

    // class 头
    private String classHeader;

    // 私有字段
    private List<LazyField> declaredFields;

    // 共有字段
    private List<LazyField> fields;


    // 私有方法
    private List<LazyMethod> declaredMethods;

    // 共有方法
    private List<LazyMethod> methods;


}
