package com.wu.framework.inner.lazy.database.expand.database.persistence.stream;

import com.wu.framework.inner.lazy.database.expand.database.persistence.util.LazyTableUtil;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/8/8 2:38 下午
 */
public class SelectReferencePipelineSplicing<T, R> extends ReferencePipelineSplicing<T, R> {

    public SelectReferencePipelineSplicing(LambdaTable.LambdaTableType lambdaTableType, Class primaryTable) {
        super(lambdaTableType, primaryTable);
        this.SQLExecuted = new StringBuilder(String.format("%s * form %s ", this.lambdaTableType.getValue(), LazyTableUtil.getTableName(this.primaryTable)));
    }


}
