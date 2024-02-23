package com.wu.freamwork.test.select;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.LambdaBasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.SqlPart;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.freamwork.domain.LazyUserTest;
import com.wu.freamwork.test.TestLazyResult;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SelectPageTestRunner extends AbstractSelectTestRunner {


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
        LazyPage<LazyUserTest> objectLazyPage = LazyPage.of(1, 10);
        LazyPage<LazyUserTest> lazyUserTestLazyPage = lazyLambdaStream.selectPage(basicComparison, objectLazyPage);
        SqlPart sqlPart = basicComparison.getSqlPart();
        String sql = sqlPart.sql();
        result.setExecuteSql(sql);
        result.setResult(lazyUserTestLazyPage);


    }

    @Override
    public TestLazyResult result() {
        result.setType(type());

        return result;
    }
}
