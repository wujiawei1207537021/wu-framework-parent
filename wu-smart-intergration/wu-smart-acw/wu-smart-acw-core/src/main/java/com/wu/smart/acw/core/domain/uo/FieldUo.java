package com.wu.smart.acw.core.domain.uo;

import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldUnique;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * describe : 字段对象
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/15 5:14 下午
 */
@Data
@Accessors(chain = true)
public class FieldUo {

    /**
     * 主键
     */
    @LazyTableFieldId
    private Long id;
    /**
     * class id
     */
    @LazyTableFieldUnique
    private Long classId;

    /**
     * 类型
     */
    @LazyTableField(columnType = "text")
    private ClassUo type;
    /**
     * 字段名称
     */
    @LazyTableFieldUnique
    private String name;

}
