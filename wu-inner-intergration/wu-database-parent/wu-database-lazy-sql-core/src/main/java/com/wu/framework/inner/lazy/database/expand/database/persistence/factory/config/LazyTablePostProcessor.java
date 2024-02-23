package com.wu.framework.inner.lazy.database.expand.database.persistence.factory.config;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.util.FileUtil;
import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.persistence.conf.LazyDatabaseJsonMessage;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.inner.lazy.source.SqlSourceClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;

import java.io.BufferedWriter;
import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * LazyTablePostProcessor
 * DDL 操作
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/2 6:23 下午
 */
@Slf4j
public class LazyTablePostProcessor implements Ordered, InitializingBean {

    private final LazyOperationConfig lazyOperationConfig;

    public LazyTablePostProcessor(LazyOperationConfig lazyOperationConfig) {
        this.lazyOperationConfig = lazyOperationConfig;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        BufferedWriter bufferedWriter = null;
        LazyOperationConfig.DdlConfigure ddlConfigure = lazyOperationConfig.getDdlConfigure();


        if (ddlConfigure.isOutPutTableStructure()) {
            URL resource = LazyTablePostProcessor.class.getResource(NormalUsedString.SLASH);
            // 写入class文件
            String resourceFilePrefix = resource.getFile();

            String target = resourceFilePrefix.split("target")[0] + "src/main/resources/";
            resourceFilePrefix = target + "sql" + File.separator;
            // 创建对应的建表语句
            bufferedWriter = FileUtil.createFileBufferedWriter(null, resourceFilePrefix, ".sql", "schema");
        }
        List<Class<?>> tableClass = LazyDatabaseJsonMessage.localCacheEntityClass;
        for (Class clazz : tableClass) {
            try {
                if (bufferedWriter != null) {
//                    LazyTableEndpoint lazyTableEndpoint = LazyTableUtil.analyzeLazyTable(clazz);
                    SqlSourceClass sqlSourceClass =  SqlSourceClass.getInstance(clazz);
                    LazyTableEndpoint lazyTableEndpoint = sqlSourceClass.getLazyTableEndpoint();

                    String creatTableSQL = lazyTableEndpoint.creatTableSQL();
                    bufferedWriter.write(creatTableSQL);
                    bufferedWriter.newLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (bufferedWriter != null) {
            try {
                bufferedWriter.close();
            } catch (Exception e) {

            }
        }
    }
}
