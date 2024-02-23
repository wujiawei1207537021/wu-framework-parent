package com.wu.smart.acw.server;

import com.wu.framework.database.lazy.table.structure.plus.StructureProvider;
import com.wu.framework.inner.lazy.hint.LazyDataSourcePropertiesHints;
import com.wu.framework.inner.lazy.stereotype.LazyScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@LazyScan(scanBasePackages = {
        "com.wu.smart.acw.core.domain.uo",
//        "com.wu.framework.inner.lazy.example.**.entity",
        "com.wu.smart.acw.server.**.entity"
})
@SpringBootApplication
@Import(StructureProvider.class)
@ImportRuntimeHints(LazyDataSourcePropertiesHints.class)
public class AcwServerApplication {
    public static void main(String[] args) {
//        DataSourceProxy
        SpringApplication.run(AcwServerApplication.class, args);
    }
}
