package com.wu.framework.inner.lazy.database.expand.database.persistence.stream;

import com.wu.framework.inner.layer.stereotype.proxy.ProxyStrategicApproach;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.function.Snippet;
import com.wu.framework.inner.lazy.database.expand.database.persistence.util.LazyTableUtil;

/**
 * @author : 吴佳伟
 * @version 1.0
 * describe :
 * @date : 2021/8/8 2:38 下午
 */
@ProxyStrategicApproach
public class SelectReferencePipeline<T> extends ReferencePipelineSplicing<T, Snippet> {


    public SelectReferencePipeline(LazyOperation lazyOperation) {
        super(lazyOperation);

    }

    /**
     * @param primaryTable @return
     * @return
     * describe 主表
     * @author Jia wei Wu
     * @date 2021/8/8 12:27 下午
     */
    @Override
    public LambdaSplicing<T, Snippet> table(Class primaryTable) {
        this.lambdaTableType = LambdaTableType.SELECT;
        this.primaryTable = primaryTable;
        return this;
    }

    /**
     * 操作表类型
     *
     * @param lambdaTableType
     * @return
     */
    @Override
    public LambdaStreamCollector<T, Snippet> lambdaTableType(LambdaTableType lambdaTableType) {
        this.lambdaTableType = LambdaTableType.SELECT;
        return this;
    }


    /**
     * @return
     * describe 获取执行的sql语句
     * @author Jia wei Wu
     * @date 2021/8/8 2:28 下午
     **/
    @Override
    public String getSqlStatement() {
        final StringBuilder sqlStatement = conditionList.splice(String.format("%s * from %s where ", this.lambdaTableType.getValue(), LazyTableUtil.getTableName(this.primaryTable)));
        return sqlStatement.toString();
    }


}
