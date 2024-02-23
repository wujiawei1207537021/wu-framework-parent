package com.wu.framework.inner.layer.data.schema;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :
 * @date : 2021/9/12 3:24 下午
 */
@Accessors(chain = true)
@Data
public class FieldSchema {

    /**
     * 类型
     */
    private Class type;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String describe;

}
