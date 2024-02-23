package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.view;

import com.wu.framework.inner.lazy.database.domain.DataBaseRole;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyLambdaStreamFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyDynamicEndpoint;

/**
 * description 创建大量的视图
 *
 * @author 吴佳伟
 * @date 2023/01/20 14:47
 */
public class MoreViewTest {

    public static void main(String[] args) {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1", 3306, "mysql", "root", "wujiawei");
        int viewNum = 1000000;
        // 原始数据库
        String sourceSchema = "source_test";
        // 创建原始数据库
        String createSourceSchema = "create database if not exists " + sourceSchema + ";";
        lazyLambdaStream.stringScriptRunner(createSourceSchema);
        // 切换数据库
        ClassLazyDynamicEndpoint lazyDynamicEndpoint = new ClassLazyDynamicEndpoint();
        lazyDynamicEndpoint.setSchema(sourceSchema);
        DynamicLazyDSContextHolder.push(lazyDynamicEndpoint);
        // 创建 表
        lazyLambdaStream.createTable(DataBaseRole.class);

        String sourceViewSchema = sourceSchema + "_view";
        lazyDynamicEndpoint.setSchema(sourceViewSchema);
        DynamicLazyDSContextHolder.push(lazyDynamicEndpoint);
        // 创建视图数据库

        String createSourceViewSchema = "create database if not exists " + sourceViewSchema + ";";
        lazyLambdaStream.stringScriptRunner(createSourceViewSchema);
        // 创建视图
        String createUserView = "create or replace   view source_test_view.user_view as select * from source_test.user;";

        String[] scripts = new String[viewNum];
        for (int i = 0; i < viewNum; i++) {
            String createUserViewScript = "create or replace   view source_test_view.data_base_role_view_" + i + " as select * from source_test.data_base_role;";
            scripts[i] = createUserViewScript;
        }
        lazyLambdaStream.stringScriptRunner(scripts);


    }

}
