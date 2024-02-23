package com.wu.framework.inner.layer.data.clazz;

import lombok.Data;

import java.util.List;

/**
 * description 解析class 获取的字段
 *
 * @author 吴佳伟
 * @date 2023/10/10 11:45
 */
@Data
public class LazyField {

    // 字段名称
    private String name;

    // 字段注解
    private List<String> annotations;

    // 字段类型
    private String fieldType;


}
