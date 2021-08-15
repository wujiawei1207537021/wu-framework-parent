package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTable;

/**
 * description 数据库操作lambda 表达式写法
 *
 * @author 吴佳伟
 * @date 2021/4/27 3:41 下午
 */
public interface LambdaStream extends AutoCloseable {

    @Override
    default void close() throws Exception {

    }

    LambdaTable select();


}
