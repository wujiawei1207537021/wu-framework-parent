package com.wu.framework.inner.layer.data.lock;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * description 锁
 *
 * <p>
 * * 锁ID
 * *
 *
 * @author Jia wei Wu
 * @EasyLock(key = "#id")
 * @Override public void lockId(String id)throws InterruptedException{
 * System.out.println("当前方法并发30s内会被锁住");
 * Thread.sleep(30000);
 * }
 * * 锁对象name
 * *
 * * @param dataBaseUser
 * @EasyLock(key = "#dataBaseUser.username")
 * @Override public void lockName(DataBaseUser dataBaseUser)throws InterruptedException{
 * System.out.println("当前方法并发30s内会被锁住");
 * Thread.sleep(30000);
 * }
 * </p>
 * @date 2023/01/03 8:19 下午
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EasyLock {

    /**
     * 锁标识 可从方法中参数获取
     * <p>
     * #root.args[0] 请求方法的第一个参数
     * #user.id   请求方法中含有参数对象User中含有属性id
     * #id 请求方法中含有参数id
     * </p>
     *
     * @return
     */
    String key() default "";

    /**
     * 锁分组
     *
     * @return
     */
    String lockGroup() default "default";

    /**
     * 锁等待时间
     *
     * @return
     */
    long time() default 100;

    /**
     * 锁等待时间单位
     *
     * @return
     */
    TimeUnit unit() default TimeUnit.MILLISECONDS;

}
