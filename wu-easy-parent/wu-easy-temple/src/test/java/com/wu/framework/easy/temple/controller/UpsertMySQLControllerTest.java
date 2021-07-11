package com.wu.framework.easy.temple.controller;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
class UpsertMySQLControllerTest {

    @Resource
    UpsertMySQLController upsertMySQLController;

    @Test
    void upsert() {
        upsertMySQLController.upsert(10);
    }

    @Test
    void quickUpsertSize() {
        upsertMySQLController.quickUpsertSize(10);
    }

    @Test
    void quickEasyHashMap() {
        upsertMySQLController.quickEasyHashMap(10);
    }

    @Test
    void quickComplexData() {
        upsertMySQLController.quickComplexData();
    }

    @Test
    void quickMoreExtractBo() {
        upsertMySQLController.quickMoreExtractBo();
    }

    @Test
    void quickBinary() {
        upsertMySQLController.quickBinary(10);
    }

    @Test
    void createUserLog() {
        upsertMySQLController.createUserLog(10);
    }
}