package com.wu.freamwork.controller;

import com.wu.framework.inner.layer.data.ProcessException;
import com.wu.framework.inner.layer.stereotype.MethodParamFunctionException;
import com.wu.framework.inner.lazy.database.expand.database.persistence.PerfectLazyOperation;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest
class LazyOperationControllerTest {

    @Autowired
    PerfectLazyOperation perfectLazyOperation;


    @Test
    void saveSqlFile()  {
        try {
            perfectLazyOperation.saveSqlFile();
        } catch (ProcessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (MethodParamFunctionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}