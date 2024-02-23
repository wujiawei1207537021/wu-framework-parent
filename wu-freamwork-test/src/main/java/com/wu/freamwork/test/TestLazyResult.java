package com.wu.freamwork.test;

import com.wu.framework.easy.excel.endpoint.EasyExcelPoint;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.stereotype.CustomRepositoryXmlScan;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import lombok.Data;

@Data
public class TestLazyResult {

    @LazyTableFieldId(idType = LazyTableFieldId.IdType.INPUT_ID)
    private String id;

    @LazyTableField(comment = "执行结构",columnType = "longtext")
    private Object result;

    @LazyTableField(comment = "执行sql",columnType = "longtext")
    private String executeSql;

    @LazyTableField(comment = "执行类型")
    private LambdaTableType type;

}
