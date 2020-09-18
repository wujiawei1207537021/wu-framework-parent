package com.wu.framework.easy.stereotype.upsert;


import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public interface IEasyUpsert {

    ThreadPoolExecutor easyUpsertExecutor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(20));

    <T> Object upsert(List<T> list) throws Exception;

}
