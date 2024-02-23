package com.wu.smart.acw.server.translation.api;

import com.wu.framework.inner.layer.data.translation.api.SourceValues;
import com.wu.framework.inner.layer.data.translation.api.TranslationAPI;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.persistence.map.EasyHashMap;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * description 表信息解析内存api调用
 *
 * @author 吴佳伟
 * @date 2023/09/25 15:29
 */
@Component
public class TableTranslationRowsAPI implements TranslationAPI {
    private final LazyLambdaStream lazyLambdaStream;

    public TableTranslationRowsAPI(LazyLambdaStream lazyLambdaStream) {
        this.lazyLambdaStream = lazyLambdaStream;
    }

    /**
     * description  转换数据
     *
     * @param sourceValuesSet 原始数据
     * @return Map<String sourceValue, Object 目标数据>
     * @author Jia wei Wu
     * @date 2020/8/6 下午7:54
     */
    @Override
    public ConcurrentMap<String, Object> translation(Set<Object> sourceValuesSet) {
        // 获取当前数据库信息
        // select count(1) from table
//        String unionAllSql = sourceValuesSet.stream().map(o -> {
//            Object tableName = o.get("tableName");
//            Object schemaName = o.get("schemaName");
//            return "select count(1) as tableRows,'" + tableName + "' as tableName from " + schemaName + "." + tableName ;
//        }).collect(Collectors.joining(" UNION ALL "));
//        return lazyLambdaStream.executeSQL(unionAllSql, EasyHashMap.class)
//                .stream()
//                .collect(Collectors.toConcurrentMap(e -> {
//                    e.get("tableName").toString();
//                    SourceValues sourceValues = new SourceValues();
//                    return sourceValues;
//                }, e -> e.get("tableRows")));
        return null;

    }
}
