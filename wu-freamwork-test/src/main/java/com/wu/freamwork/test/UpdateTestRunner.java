package com.wu.freamwork.test;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.LambdaBasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.SqlPart;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.freamwork.domain.LazyUserTest;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UpdateTestRunner extends AbstractTestRunner implements TestRunner {


    @Resource
    private LazyLambdaStream lazyLambdaStream;

    private final TestLazyResult result = new TestLazyResult();


    @Override
    public LambdaTableType type() {
        return LambdaTableType.UPDATE;
    }

    @Override
    public void doRun() {
        LazyUserTest lazyUserTest = new LazyUserTest();
        lazyUserTest.setSex(LazyUserTest.Sex.MAN);
        lazyUserTest.setBirthday(LocalDateTime.now());
        lazyUserTest.setAge(18);
        lazyUserTest.setUsername("admin");
        lazyUserTest.setIsDeleted(false);
        LambdaBasicComparison<LazyUserTest> basicComparison = LazyWrappers.<LazyUserTest>lambdaWrapper().eq(LazyUserTest::getIsDeleted, false);
        Integer update = lazyLambdaStream.update(lazyUserTest, basicComparison);
        SqlPart sqlPart = basicComparison.getSqlPart();
        String sql = sqlPart.sql();
        result.setExecuteSql(sql);
        result.setResult(update);


    }

    @Override
    public TestLazyResult result() {
        result.setType(type());

        return result;
    }
}
