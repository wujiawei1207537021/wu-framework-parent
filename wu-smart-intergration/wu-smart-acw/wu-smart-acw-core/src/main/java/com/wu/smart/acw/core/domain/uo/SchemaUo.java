package com.wu.smart.acw.core.domain.uo;

import com.wu.framework.inner.lazy.stereotype.LazyTable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * schema  创建时不存储、不更新对象 仅仅是生成执行脚本和查询时对象映射
 */
@Deprecated // TODO 恒久后可能会使用
@Data
@Accessors(chain = true)
@LazyTable(tableName = "database_schema")
public class SchemaUo {
    /**
     * 数据库名称
     */
    private String name;
    /**
     * 字符集
     */
    private String characterSet;
    /**
     * 排序规则
     */
    private String sortingRules;

    private String ext;

}
