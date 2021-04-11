package com.wu.framework.easy.temple.run;

import com.wu.framework.easy.stereotype.upsert.component.IUpsert;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.swing.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpsertControllerTest  {

    @Resource
    private IUpsert iUpsert;

    @Test
    public void run(){
        iUpsert.upsert(null);
    }

}