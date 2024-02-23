package com.wu.framework.inner.layer.data.clazz;

import lombok.Data;

import java.util.List;

/**
 * description 参数
 *
 * @author 吴佳伟
 * @date 2023/10/10 15:25
 */
@Data
public class LazyParameter {

    // 参数名称
    private String name;

    // 参数类型
    private String type;

    // 参数注解
    private List<String> annotationList;
}
