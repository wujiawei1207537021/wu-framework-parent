package com.wu.smart.acw.server.infrastructure.persistence;


import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.smart.acw.server.domain.database.instance.type.AcwInstanceTypeRepository;
import com.wu.smart.acw.server.domain.database.instance.type.DatabaseInstanceType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AcwInstanceTypeRepositoryImpl implements AcwInstanceTypeRepository {
    private final LazyLambdaStream lazyLambdaStream;

    public AcwInstanceTypeRepositoryImpl(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;
    }

    @Override
    public List<DatabaseInstanceType> findAll() {
        return null;
    }
}
