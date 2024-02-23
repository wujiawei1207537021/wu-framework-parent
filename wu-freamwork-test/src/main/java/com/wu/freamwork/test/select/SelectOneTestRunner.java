package com.wu.freamwork.test.select;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.SqlPart;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.freamwork.domain.LazyUserTest;
import com.wu.freamwork.test.TestLazyResult;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SelectOneTestRunner extends AbstractSelectTestRunner {


    @Resource
    private LazyLambdaStream lazyLambdaStream;

    private final TestLazyResult result = new TestLazyResult();


    @Override
    public LambdaTableType type() {
        return LambdaTableType.SELECT;
    }

    @Override
    public void doRun() {

        BasicComparison<LazyUserTest, ?, ?, ?> basicComparison = getBasicComparison();
        LazyUserTest lazyUserTest = lazyLambdaStream.selectOne(basicComparison
        );
        SqlPart sqlPart = basicComparison.getSqlPart();
        String sql = sqlPart.sql();
        result.setExecuteSql(sql);
        result.setResult(lazyUserTest);

    }

    @Override
    public TestLazyResult result() {
        result.setType(type());

        return result;
    }
}
