package com.wu.framework.database.lazy.web.plus;


import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;

class AbstractLazyCrudProviderTest {

    @Autowired
    AbstractLazyCrudProvider abstractLazyCrudProvider;

    void findById() {
        abstractLazyCrudProvider.findById(null);
        Result one = abstractLazyCrudProvider.findOne(LazyWrappers.<User>lambdaWrapper()
                .eq(User::getId, null)
        );

    }
}