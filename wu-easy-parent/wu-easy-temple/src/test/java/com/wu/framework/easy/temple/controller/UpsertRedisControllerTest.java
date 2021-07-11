package com.wu.framework.easy.temple.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
class UpsertRedisControllerTest {
    @Resource
    UpsertRedisController upsertRedisController;


    @Test
    void normalUpsert() {
        upsertRedisController.normalUpsert();
    }

    @Test
    void testUpsert() {
        upsertRedisController.upsert();
    }

    @Test
    void quickUpsert() {
        upsertRedisController.quickUpsert();
    }
}