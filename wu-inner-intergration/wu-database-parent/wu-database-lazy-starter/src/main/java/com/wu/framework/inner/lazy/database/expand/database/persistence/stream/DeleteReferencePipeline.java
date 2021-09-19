package com.wu.framework.inner.lazy.database.expand.database.persistence.stream;

import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.function.Snippet;
import com.wu.framework.inner.lazy.database.expand.database.persistence.util.LazyTableUtil;

/**
 * describe :
 *
 * @author : 吴佳伟
 * @version 1.0
 * @date : 2021/9/19 6:00 下午
 */
public class DeleteReferencePipeline<T> extends ReferencePipelineSplicing<T, Snippet> {


    public DeleteReferencePipeline(LazyOperation lazyOperation) {
        super(lazyOperation);
    }

    /**
     * @param primaryTable@return
     * @describe 主表
     * @author Jia wei Wu
     * @date 2021/8/8 12:27 下午
     **/
    @Override
    public LambdaSplicing<T, Snippet> table(Class primaryTable) {
        this.lambdaTableType = LambdaTableType.DELETE;
        this.primaryTable = primaryTable;
        return this;
    }


    /**
     * @return
     * @describe 获取执行的sql语句
     * @author Jia wei Wu
     * @date 2021/8/8 2:28 下午
     **/
    @Override
    public String getSqlStatement() {
        final StringBuilder sqlStatement = conditionList.splice(String.format("%s  from %s where", this.lambdaTableType.getValue(), LazyTableUtil.getTableName(this.primaryTable)));
        return sqlStatement.toString();
    }
}
