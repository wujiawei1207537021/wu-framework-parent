package com.wu.smart.acw.server.service.impl;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.smart.acw.core.domain.uo.AcwClassUo;
import com.wu.smart.acw.server.MysqlDateTypeColumnDataUo;
import com.wu.smart.acw.server.application.ClassApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
class ClassServiceImplTest {

    @Autowired
    ClassApplication classService;

    @Autowired
    LazyLambdaStream lazyLambdaStream;


    @Test
    void save() {
        classService.save(AcwClassUo.class);
    }

    @Test
    void smartSaveMysqlDateTypeColumnDataUo() {
        lazyLambdaStream.upsert(new MysqlDateTypeColumnDataUo());
    }

    @Test
    void saveMysqlDateTypeColumnDataUo() {
        List<MysqlDateTypeColumnDataUo> mysqlDateTypeColumnDataUoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final MysqlDateTypeColumnDataUo mysqlDateTypeColumnDataUo = new MysqlDateTypeColumnDataUo();
            mysqlDateTypeColumnDataUoList.add(mysqlDateTypeColumnDataUo);
        }
        lazyLambdaStream.upsert(mysqlDateTypeColumnDataUoList);
        for (MysqlDateTypeColumnDataUo mysqlDateTypeColumnDataUo : mysqlDateTypeColumnDataUoList) {
            System.out.println(mysqlDateTypeColumnDataUo);
        }
    }

    @Test
    void upsertRemoveNull() {
        List<MysqlDateTypeColumnDataUo> mysqlDateTypeColumnDataUoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final MysqlDateTypeColumnDataUo mysqlDateTypeColumnDataUo = new MysqlDateTypeColumnDataUo();
            mysqlDateTypeColumnDataUo.setDate(null);
            mysqlDateTypeColumnDataUoList.add(mysqlDateTypeColumnDataUo);
        }
        lazyLambdaStream.upsertRemoveNull(mysqlDateTypeColumnDataUoList);

    }


}