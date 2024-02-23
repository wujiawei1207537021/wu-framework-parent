package com.wu.freamwork.test.select;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.BasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.LambdaBasicComparison;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.SqlPart;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.freamwork.domain.LazyUserTest;
import com.wu.freamwork.test.AbstractTestRunner;
import com.wu.freamwork.test.TestRunner;

import java.time.LocalDateTime;

public abstract class AbstractSelectTestRunner extends AbstractTestRunner implements TestRunner {

    public BasicComparison<LazyUserTest, ?, ?, ?>  getBasicComparison(){
        LambdaBasicComparison<LazyUserTest> eq = LazyWrappers.<LazyUserTest>lambdaWrapper()
                .eq(LazyUserTest::getIsDeleted, false)
                .eq(LazyUserTest::getSex, LazyUserTest.Sex.MAN)
                .gt(LazyUserTest::getAge, 10)
                .gt(LazyUserTest::getBirthday, LocalDateTime.now())
                ;
        return eq;
    }


}
