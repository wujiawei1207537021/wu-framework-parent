package com.wu.framework.inner.layer.data.lock;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2023/01/03 8:20 下午
 */
public class DefaultLock implements ILock {

    ConcurrentHashMap<Object, Lock> LOCK_MAP = new ConcurrentHashMap<Object, Lock>(64);

    /**
     * description:  锁对象
     *
     * @param lockBean 锁对象
     * @return
     * @author Jia wei Wu
     * @date: 3.1.23 8:40 下午
     */
    @Override
    public Lock lock(Object lockBean) {
        // 锁对象
        Lock lock = new ReentrantLock();
        lock.lock();
        LOCK_MAP.putIfAbsent(lockBean, lock);
        return lock;
    }

    @Override
    public Lock lockInterruptibly(Object lockBean) throws InterruptedException {
        Lock lock = new ReentrantLock();
        lock.lockInterruptibly();
        LOCK_MAP.putIfAbsent(lockBean, lock);
        return lock;
    }

    /**
     * 如果锁可用立即返回true，如果锁不可用立即返回false；
     *
     * @param lockBean 锁对象
     * @return
     */
    @Override
    public boolean tryLock(Object lockBean) {
        Lock lock = LOCK_MAP.get(lockBean);
        if (null != lock) {
            return lock.tryLock();
        }
        return false;
    }

    /**
     * 如果锁可用，则此方法立即返回true。 如果该锁不可用，则当前线程将出于线程调度目的而被禁用并处于休眠状态，直到发生以下三种情况之一为止：①当前线程获取到该锁；②当前线程被其他线程中断，并且支持中断获取锁；③经过指定的等待时间如果获得了锁，则返回true，没获取到锁返回false。
     *
     * @param lockBean 锁对象
     * @param time     时间
     * @param unit     时间单位
     * @return
     * @throws InterruptedException
     */
    @Override
    public boolean tryLock(Object lockBean, long time, TimeUnit unit) throws InterruptedException {
        Lock lock = LOCK_MAP.get(lockBean);
        if (null != lock) {
            return lock.tryLock(time, unit);
        }
        return false;
    }

    /**
     * 释放锁
     *
     * @param lockBean 锁对象
     */
    @Override
    public void unlock(Object lockBean) {
        Lock lock = LOCK_MAP.get(lockBean);
        if (null != lock) {
            lock.unlock();
            LOCK_MAP.remove(lockBean);
        }

    }

    public Condition newCondition() {
        return null;
    }
}
