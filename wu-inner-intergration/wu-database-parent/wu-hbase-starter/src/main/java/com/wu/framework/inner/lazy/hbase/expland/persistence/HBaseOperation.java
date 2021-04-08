package com.wu.framework.inner.lazy.hbase.expland.persistence;



import java.util.List;

/**
 * description HBaseOperation
 * HBase 操作使用
 *
 * @author Jia wei Wu
 * @date 2021/3/26 下午5:10
 */
public interface HBaseOperation  {


    /**
     * @param <T>
     * @return t
     * @describe 新增数据
     * @author Jia wei Wu
     * @date 2021/3/27 9:21 下午
     **/
    <T> T insert(T t);

    /**
     * description  插入数据集合
     *
     * @param t
     * @param <T>
     * @return
     * @exception/throws
     * @author Jia wei Wu
     * @date 2021/4/8 上午9:37
     */
    <T> T insertList(List<T> t);


    /**
     * @param <T>
     * @return t
     * @describe 更新或者插入数据
     * @author Jia wei Wu
     * @date 2021/3/27 9:21 下午
     **/
    <T> T upsert(T t);


}
