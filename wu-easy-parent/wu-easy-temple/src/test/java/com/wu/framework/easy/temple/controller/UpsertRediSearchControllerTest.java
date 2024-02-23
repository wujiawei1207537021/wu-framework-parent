package com.wu.framework.easy.temple.controller;

import com.wu.framework.easy.temple.domain.UserLog;
import com.wu.framework.easy.upsert.RedisSearchUpsertSink;
import com.wu.framework.response.Result;
import io.redisearch.Query;
import io.redisearch.SearchResult;
import io.redisearch.client.Client;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
class UpsertRediSearchControllerTest {


    @Autowired
    UpsertRediSearchController upsertRediSearchController;

    @Autowired
    RedisSearchUpsertSink redisSearchUpsertSink;

    @Autowired
    Client client;

    @Test
    void normalUpsert() {
        final Result result = upsertRediSearchController.normalUpsert();
        System.out.println(result);
    }

    /**
     * describe 测试 redis search 数据插入
     *
     * @param
     * @return
     * @author Jia wei Wu
     * @date 2021/12/19 5:55 下午
     **/
    @Test
    void xx() {
        final Object o = redisSearchUpsertSink.fuzzyUpsert(UserLog.createUserLogList(10), UserLog.createUserLogList(100));
        System.out.println(o);
    }

    public SearchResult search(String queryString, String price) {
        Query query = new Query(queryString)
                .addFilter(new Query.NumericFilter(price, 0, 1000))
                .limit(0, Integer.MAX_VALUE);
        return client.search(query);
    }


}