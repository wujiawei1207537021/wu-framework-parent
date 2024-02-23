package com.wu.freamwork.test;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.freamwork.domain.LazyUserTest;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InsertTestRunner extends AbstractTestRunner implements TestRunner {


    @Resource
    private LazyLambdaStream lazyLambdaStream;

    private final TestLazyResult result = new TestLazyResult();


    @Override
    public LambdaTableType type() {
        return LambdaTableType.INSERT;
    }

    @Override
    public void doRun() {
        LazyUserTest lazyUserTest = new LazyUserTest();
        lazyUserTest.setSex(LazyUserTest.Sex.MAN);
        lazyUserTest.setBirthday(LocalDateTime.now());
        lazyUserTest.setAge(18);
        lazyUserTest.setUsername("admin");

        lazyLambdaStream.upsert(lazyUserTest);
    }

    @Override
    public TestLazyResult result() {
        result.setType(type());

        return result;
    }
}
