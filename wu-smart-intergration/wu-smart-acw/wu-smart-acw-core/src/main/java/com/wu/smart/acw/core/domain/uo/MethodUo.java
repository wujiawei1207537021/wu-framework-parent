package com.wu.smart.acw.core.domain.uo;

import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 方法编码
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2021/9/19 3:40 下午
 */
@Accessors(chain = true)
@Data
public class MethodUo {
    /**
     * 主键
     */
    @LazyTableFieldId
    private Long id;
    /**
     * class id
     */
    private Long classId;
    /**
     * 方法名
     */
    private String name;
    /**
     * 出参数
     */
    private String outParams;
    /**
     * 入参
     */
    private String inParams;
    /**
     * 方法体
     */
    private String body;

}
