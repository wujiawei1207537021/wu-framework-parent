package com.wu.framework.inner.layer.data.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2023/01/03 8:20 下午
 */
public interface ILock {

    /**
     * description:  锁对象
     *
     * @param lockBean 锁对象
     * @return
     * @author Jia wei Wu
     * @date: 3.1.23 8:40 下午
     */
    Lock lock(Object lockBean);


    Lock lockInterruptibly(Object lockBean) throws InterruptedException;

    /**
     * 如果锁可用立即返回true，如果锁不可用立即返回false；
     *
     * @param lockBean 锁对象
     * @return
     */
    boolean tryLock(Object lockBean);

    /**
     * 如果锁可用，则此方法立即返回true。 如果该锁不可用，则当前线程将出于线程调度目的而被禁用并处于休眠状态，直到发生以下三种情况之一为止：①当前线程获取到该锁；②当前线程被其他线程中断，并且支持中断获取锁；③经过指定的等待时间如果获得了锁，则返回true，没获取到锁返回false。
     *
     * @param lockBean 锁对象
     * @param time     时间
     * @param unit     时间单位
     * @return
     * @throws InterruptedException
     */
    boolean tryLock(Object lockBean, long time, TimeUnit unit) throws InterruptedException;

    /**
     * 释放锁
     *
     * @param lockBean 锁对象
     */
    void unlock(Object lockBean);


    Condition newCondition();
}
