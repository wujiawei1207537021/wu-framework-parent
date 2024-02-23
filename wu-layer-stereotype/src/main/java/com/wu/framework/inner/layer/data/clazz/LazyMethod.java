package com.wu.framework.inner.layer.data.clazz;

import lombok.Data;

import java.util.List;

/**
 * description 解析class中获取的方法
 *
 * @author 吴佳伟
 * @date 2023/10/10 11:44
 */
@Data
public class LazyMethod {

    // 方法上的注解
    private List<String> annotation;

    // 方法描述
    private String desc;

    // 是否是共有的
    private boolean isPublic;
    
    // 参数
    private List<LazyParameter> lazyParameterList;

    // 方法体
    private String body;

}
