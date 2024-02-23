package com.wu.smart.acw.server.service.impl;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.smart.acw.core.domain.uo.ClassUo;
import com.wu.smart.acw.server.MysqlDateTypeColumnDataUo;
import com.wu.smart.acw.server.service.ClassService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
class ClassServiceImplTest {

    @Autowired
    ClassService classService;

    @Autowired
    LazyLambdaStream lazyLambdaStream;


    @Test
    void save() {
        classService.save(ClassUo.class);
    }

    @Test
    void smartSaveMysqlDateTypeColumnDataUo() {
        lazyLambdaStream.smartUpsert(new MysqlDateTypeColumnDataUo());
    }

    @Test
    void saveMysqlDateTypeColumnDataUo() {
        lazyLambdaStream.upsert(new MysqlDateTypeColumnDataUo());
    }
}