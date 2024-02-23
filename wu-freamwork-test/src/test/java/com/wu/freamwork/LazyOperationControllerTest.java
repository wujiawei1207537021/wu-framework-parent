package com.wu.freamwork;

import com.alibaba.fastjson.JSONObject;
import com.wu.framework.easy.upsert.core.dynamic.IUpsert;
import com.wu.framework.inner.layer.data.ProcessException;
import com.wu.framework.inner.layer.stereotype.MethodParamFunctionException;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.expand.database.persistence.LazyOperation;
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
class LazyOperationControllerTest {

    @Autowired
    PerfectLazyOperation perfectLazyOperation;

    @Autowired
    IUpsert upsert;

    @Autowired
    LazyOperation lazyOperation;


    @Test
    void saveSqlFile() {
        try {
            perfectLazyOperation.saveSqlFile();
        } catch (ProcessException | SQLException | MethodParamFunctionException | ExecutionException | InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void scroll() throws ProcessException, SQLException, MethodParamFunctionException, IOException, ExecutionException, InterruptedException {

        perfectLazyOperation.scroll(null, EnglishWord.class, null, englishWordPage -> {
            final List<EnglishWord> collect = new ArrayList<>(englishWordPage.getRecord());
            if (ObjectUtils.isEmpty(collect)) {
                return englishWordPage;
            }
            upsert.upsert(collect);
            return englishWordPage;
        });

    }


    /**
     * 查询所有的表
     */
    @Test
    public void showTables() {
        String sqlSelectTable = "select concat('%s.',table_name) tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = '%s' ";

        final List<LazyTableInfo> tables = lazyOperation.executeSQL(sqlSelectTable, LazyTableInfo.class, "lazy", "lazy");

        System.out.println(JSONObject.toJSONString(tables));
    }
}