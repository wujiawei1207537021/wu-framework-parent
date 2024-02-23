package com.wu.freamwork.service;

import com.wu.freamwork.domain.DataBaseUser;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2023/01/04 11:12 上午
 */
public interface LockeService {

    /**
     * 锁ID
     *
     * @param id
     */
    void lockId(String id) throws InterruptedException;

    /**
     * 锁对象name
     *
     * @param dataBaseUser
     */
    void lockName(DataBaseUser dataBaseUser) throws InterruptedException;
}
