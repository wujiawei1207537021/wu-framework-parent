package com.wu.freamwork.service.impl;

import com.wu.framework.inner.layer.data.lock.EasyLock;
import com.wu.freamwork.domain.DataBaseUser;
import com.wu.freamwork.service.LockeService;
import org.springframework.stereotype.Service;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2023/01/04 11:12 上午
 */
@Service
public class LockeServiceImpl implements LockeService {
    /**
     * 锁ID
     *
     * @param id
     */
    @EasyLock(key = "#id")
    @Override
    public void lockId(String id) throws InterruptedException {
        System.out.println("当前方法并发30s内会被锁住");
        Thread.sleep(30000);
    }

    /**
     * 锁对象name
     *
     * @param dataBaseUser
     */
    @EasyLock(key = "#dataBaseUser.username")
    @Override
    public void lockName(DataBaseUser dataBaseUser) throws InterruptedException {
        System.out.println("当前方法并发30s内会被锁住");
        Thread.sleep(30000);
    }
}
