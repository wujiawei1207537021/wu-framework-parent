package com.wu.smart.acw.server.service.impl;

import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import com.wu.smart.acw.server.service.TableService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * describe : 表操作
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/31 22:43
 */
@Service
public class TableServiceImpl implements TableService {

    private final LazyLambdaStream lazyLambdaStream;

    public TableServiceImpl(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;
    }

    /**
     * describe 数据存储
     *
     * @param tableName 表名
     * @param data      数据
     * @return
     * @author Jia wei Wu
     * @date 2022/1/31 22:46
     **/
    @Override
    public Result dataStorage(String tableName, List<EasyHashMap> data) {
        for (EasyHashMap datum : data) {
            datum.setUniqueLabel(tableName);
            lazyLambdaStream.smartUpsert(datum);
        }
        return ResultFactory.successOf();
    }
}
