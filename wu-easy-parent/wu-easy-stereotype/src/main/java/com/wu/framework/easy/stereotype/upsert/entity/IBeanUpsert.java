package com.wu.framework.easy.stereotype.upsert.entity;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2021/3/2 6:23 下午
 */
public interface IBeanUpsert {

    /**
     * @param
     * @return
     * @describe 对象处理之前
     * @author Jia wei Wu
     * @date 2021/3/2 6:24 下午
     **/
    Object beforeObjectProcess() throws Exception;
}
