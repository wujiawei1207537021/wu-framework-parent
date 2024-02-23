package com.wu.smart.acw.server.service.impl;

import com.wu.smart.acw.core.domain.uo.ClassUo;
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


    @Test
    void save() {
        classService.save(ClassUo.class);
    }
}