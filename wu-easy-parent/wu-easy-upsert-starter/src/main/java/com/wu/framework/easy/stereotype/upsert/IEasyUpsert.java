package com.wu.framework.easy.stereotype.upsert;


import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public interface IEasyUpsert {

    ThreadPoolExecutor easyUpsertExecutor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(20));

    <T> Object upsert(List<T> list) throws Exception;


    /**
     * 模糊插入
     * @param objects
     * @param <T>
     * @return
     * @throws Exception
     */
   default  <T> Object fuzzyUpsert(Object... objects) throws Exception{
       for (Object object : objects) {
           if(object instanceof List){
               upsert((List) object);
           }else {
               upsert(Collections.singletonList(object));
           }
       }
       return true;
   };
}
