package com.wu.framework.database.lazy.table.structure.plus;

import com.wu.framework.database.lazy.table.structure.plus.dto.DataBaseTableStructure;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.datasource.proxy.LazyProxyDataSource;
import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.persistence.conf.LazyDynamicEndpoint;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Tag(name = "表结构插件")
@EasyController("/structure")
public class StructureProvider {

    private final LazyLambdaStream lazyLambdaStream;
    private final LazyProxyDataSource lazyProxyDataSource;

    public StructureProvider(LazyLambdaStream lazyLambdaStream, LazyProxyDataSource lazyProxyDataSource) {
        this.lazyLambdaStream = lazyLambdaStream;
        this.lazyProxyDataSource = lazyProxyDataSource;
    }

    /**
     * 查询当前数据库对应的 表结构
     *
     * @return
     */
    @GetMapping("/table/get")
    public Result<DataBaseTableStructure> tableStructure(String schema) throws SQLException {
        LazyDynamicEndpoint peek = DynamicLazyDSContextHolder.peek();
        if (peek != null) {
            schema = schema == null ? peek.getSchema() : schema;
        }
        if(schema==null){
            Connection connection = lazyProxyDataSource.getConnection();
            schema = ObjectUtils.isEmpty(connection.getCatalog()) ? connection.getSchema() : connection.getCatalog();
        }

        // 查询当前数据库所有字段
        List<LazyColumn> lazyColumnList = lazyLambdaStream.selectList(
                LazyWrappers.<LazyColumn>lambdaWrapper()
                        .eqIgnoreEmpty(LazyColumn::getTableSchema, schema)
        );
        DataBaseTableStructure structure = new DataBaseTableStructure();
        DataBaseTableStructure.Info info=new DataBaseTableStructure.Info();
        info.setTitle("表结构");
        structure.setInfo(info);
        structure.setLazyColumnList(lazyColumnList);
        return ResultFactory.successOf(structure);
    }


}
