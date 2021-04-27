package com.wu.framework.inner.lazy.database.expand.database.persistence;

import com.wu.framework.inner.lazy.database.expand.database.persistence.lambda.LambdaStream;

/**
 * @author : Jia wei Wu
 * @version : 1.0
 * @describe: 懒人数据库持久层操作合集
 * @date : 2020/7/3 下午8:48
 */
public interface LazyOperation extends LazyBaseOperation {

    /**
     * description lambda
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/27 3:18 下午
     */
    <T> LambdaStream<T> lambda(String tableName);

}
