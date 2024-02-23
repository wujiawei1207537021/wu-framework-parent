package com.wu.freamwork;

import com.alibaba.fastjson.JSONObject;
import com.wu.framework.easy.upsert.core.dynamic.IUpsert;
import com.wu.framework.inner.layer.data.ProcessException;
import com.wu.framework.inner.layer.stereotype.MethodParamFunctionException;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazySqlOperation;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.database.smart.database.persistence.PerfectLazyOperation;
import com.wu.freamwork.domain.EnglishWord;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest
class DenoTestTest {

    @Autowired
    PerfectLazyOperation perfectLazyOperation;

    @Autowired
    IUpsert upsert;

    @Autowired
    LazySqlOperation lazySqlOperation;

    @Autowired
    LazyLambdaStream lazyLambdaStream;


    @Test
    void saveSqlFile() {
        try {
            perfectLazyOperation.saveSqlFile();
        } catch (ProcessException | SQLException | MethodParamFunctionException | ExecutionException |
                InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void scroll() {

        lazyLambdaStream.scroll(null, LazyWrappers.<EnglishWord>lambdaWrapper(), englishWordPage -> {
            final List<EnglishWord> collect = new ArrayList<>(englishWordPage.getRecord());
            if (ObjectUtils.isEmpty(collect)) {
                return;
            }
            upsert.upsert(collect);
        });

    }


    /**
     * 查询所有的表
     */
    @Test
    public void showTables() {
        String sqlSelectTable = "select concat('%s.',table_name) tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = '%s' ";

        final List<LazyTableInfo> tables = lazySqlOperation.executeSQL(sqlSelectTable, LazyTableInfo.class, "lazy", "lazy");

        System.out.println(JSONObject.toJSONString(tables));
    }
}