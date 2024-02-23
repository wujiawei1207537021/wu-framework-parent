package com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.script;

import com.wu.framework.inner.lazy.database.expand.database.persistence.factory.LazyLambdaStreamFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/7/18 19:06
 */
public class ScriptRunner {
    public static void main(String[] args) throws MalformedURLException {
        LazyLambdaStream lazyLambdaStream = LazyLambdaStreamFactory.createLazyLambdaStream(
                "127.0.0.1",
                3306,
                "acw",
                "root",
                "wujiawei"
        );
        Resource fileResource = new FileSystemResource("/Users/wujiawei/IdeaProjects/wu-framework-parent/wu-inner-intergration/wu-database-parent/wu-database-lazy-sql-core/src/test/resources/schema.sql");
        ClassPathResource classPathResource = new ClassPathResource("schema.sql");
        UrlResource urlResource = new UrlResource("http://ip:port/tenant-sql-173.sql");
        lazyLambdaStream.scriptRunner(fileResource, classPathResource, urlResource);
    }
}
