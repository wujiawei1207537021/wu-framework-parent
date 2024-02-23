package com.wu.smart.acw.client.nocode.provider.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.domain.LazyColumn;
import com.wu.framework.inner.lazy.database.domain.LazyTableInfo;
import com.wu.framework.inner.lazy.database.domain.Schemata;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.inner.lazy.persistence.util.DataSourceUrlParsingUtil;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * 获取当前服务的数据源
 */
@ConditionalOnBean(LazyLambdaStream.class)
@Tag(name = "获取当前服务的数据源")
@EasyController("/current/project/dataSource")
public class CurrentProjectDataSourceProvider {

    private final LazyLambdaStream lazyLambdaStream;
    private final LazyDataSourceProperties lazyDataSourceProperties;

    public CurrentProjectDataSourceProvider(LazyLambdaStream lazyLambdaStream, LazyDataSourceProperties lazyDataSourceProperties) {
        this.lazyLambdaStream = lazyLambdaStream;
        this.lazyDataSourceProperties = lazyDataSourceProperties;
    }


    @Operation(summary = "获取默认schema")
    @GetMapping("/findDefaultSchema")
    public Result<String> findDefaultSchema() {
        String url = lazyDataSourceProperties.getUrl();
        return ResultFactory.successOf(DataSourceUrlParsingUtil.schema(url));
    }

    // 获取当前实例对应的schema
    @Operation(summary = "获取当前实例对应的schema")
    @GetMapping("/findSchemaList")
    public Result<List<Schemata>> findSchemaList() {
        Collection<Schemata> schematas = lazyLambdaStream.of(Schemata.class).selectList(null);
        return ResultFactory.successOf(schematas.stream().toList());
    }

    // 获取表集合
    @Operation(summary = "获取表集合")
    @GetMapping("/findTableList")
    public Result<List<LazyTableInfo>> findTableList(@RequestParam("schema") String schema) {
        List<LazyTableInfo> lazyTableInfos = lazyLambdaStream.selectList(LazyWrappers.<LazyTableInfo>lambdaWrapper()
                .eq(LazyTableInfo::getTableSchema, schema)
        );
        return ResultFactory.successOf(lazyTableInfos);
    }

    // 获取字段集合
    @Operation(summary = "获取字段集合")
    @GetMapping("/findColumnList")
    public Result<List<LazyColumn>> findColumnList(@RequestParam("schema") String schema,
                                                   @RequestParam("tableNameList") List<String> tableNameList) {

        List<LazyColumn> lazyColumnList = lazyLambdaStream
                .selectList(LazyWrappers.<LazyColumn>lambdaWrapper()
                        .eq(LazyColumn::getTableSchema, schema)
                        .in(LazyColumn::getTableName, tableNameList)
                );
        return ResultFactory.successOf(lazyColumnList);
    }
}
