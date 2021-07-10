package com.wu.framework.easy.temple.controller;


import com.wu.framework.easy.upsert.core.dynamic.aop.EasyUpsertAnnotationAdvisor;
import org.aopalliance.aop.Advice;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
class UpsertMySQLControllerTest {

    @Resource
    EasyUpsertAnnotationAdvisor easyUpsertAnnotationAdvisor;

    @Test
    public void getEasyUpsertDSAnnotationAdvisor() {
        final Advice advice = easyUpsertAnnotationAdvisor.getAdvice();
        System.out.println(advice);
    }

    @Test
    void upsert() {
    }

    @Test
    void upsertSize() {
    }

    @Test
    void easyHashMap() {
    }

    @Test
    void complexData() {
    }

    @Test
    void moreExtractBo() {
    }

    @Test
    void binary() {
    }

    @Test
    void createUserLog() {
    }
}