package com.wu.framework.inner.lazy.database.expand.database.persistence.stream;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.stereotype.proxy.ProxyStrategicApproach;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.util.LazyTableUtil;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/8/8 2:38 下午
 */
@ProxyStrategicApproach
public class SelectReferencePipeline<T, R> extends ReferencePipelineCollector<T, R> {


    public SelectReferencePipeline(LazyOperation lazyOperation) {
        super(lazyOperation);

    }

    /**
     * @param primaryTable @return
     * @return
     * @describe 主表
     * @author Jia wei Wu
     * @date 2021/8/8 12:27 下午
     */
    @Override
    public LambdaStreamCollector<T, R> table(Class primaryTable) {
        this.lambdaTableType = LambdaTableType.SELECT;
        this.primaryTable = primaryTable;
        this.SQLExecuted = new StringBuilder(String.format("%s * from %s ", this.lambdaTableType.getValue(), LazyTableUtil.getTableName(this.primaryTable)));
        return this;
    }

    /**
     * 操作表类型
     *
     * @param lambdaTableType
     * @return
     */
    @Override
    public LambdaStreamCollector<T, R> lambdaTableType(LambdaTableType lambdaTableType) {
        this.lambdaTableType = LambdaTableType.SELECT;
        return this;
    }

    /**
     * @param condition
     * @param row
     * @param var
     * @return
     * @describe 等于条件
     * @author Jia wei Wu
     * @date 2021/7/16 9:44 下午
     **/
    public LambdaStreamCollector<T, R> eq(boolean condition, R row, Object var) {
        this.where();
        if (condition) {
            if (haveAnd) {
                this.SQLExecuted.
                        append(NormalUsedString.SPACE)
                        .append(NormalUsedString.AND)
                        .append(NormalUsedString.SPACE);
            } else {
                this.haveAnd = true;
            }
            this.SQLExecuted
                    .append(NormalUsedString.SPACE)
                    .append(row)
                    .append(NormalUsedString.SPACE)
                    .append(NormalUsedString.EQUALS)
                    .append(NormalUsedString.SPACE)
                    .append(NormalUsedString.SINGLE_QUOTE)
                    .append(var)
                    .append(NormalUsedString.SINGLE_QUOTE);
        }
        return this;
    }
}
