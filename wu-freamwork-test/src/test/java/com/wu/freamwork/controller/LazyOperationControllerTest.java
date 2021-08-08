package com.wu.freamwork.controller;

import com.wu.framework.authorization.model.User;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.ReferencePipeline;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
class LazyOperationControllerTest {

    @Resource
    LazyOperation lazyOperation;

    @Test
    void select() {
        ReferencePipeline<User> userReferencePipeline = new ReferencePipeline<>(lazyOperation);

    }
}