package com.wu.framework.inner.lazy.database.expand.database.persistence.cure;

/**
 * description 持久层治愈
 *
 * @author Jia wei Wu
 * @date 2023/01/11 17:12
 */
public interface Cure {


    /**
     * 判断当前能否治愈此异常
     *
     * @param throwable 异常信息
     * @return
     */
    boolean supports(Throwable throwable);

    /**
     * 治愈
     *
     * @param retryTime
     * @param throwable 异常信息
     */
    void cure(int retryTime, Throwable throwable) throws Throwable;

}
