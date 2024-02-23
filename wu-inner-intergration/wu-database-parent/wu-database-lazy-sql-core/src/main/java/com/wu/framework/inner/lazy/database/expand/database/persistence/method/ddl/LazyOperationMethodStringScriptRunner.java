package com.wu.framework.inner.lazy.database.expand.database.persistence.method.ddl;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationParameter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.ObjectUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :  执行 字符串 sql 脚本
 * @date : 2020/7/3 下午10:28
 */
@Slf4j
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyOperationMethodStringScriptRunner extends AbstractLazyDDLOperationMethod {


    public LazyOperationMethodStringScriptRunner(LazyOperationParameter lazyOperationParameter) {
        super(lazyOperationParameter);
    }

    /**
     * @param sourceParams
     * @return description 通过参数获取持久性存储库对象
     * @author Jia wei Wu
     * @date 2021/4/17 3:38 下午
     **/
    @Override
    public PersistenceRepository doAnalyzePersistenceRepository(Object[] sourceParams) throws Exception {
        PersistenceRepository persistenceRepository = createPersistenceRepository();
        persistenceRepository.setExecutionType(LambdaTableType.STRING_SCRIPT_RUNNER);
        StringBuffer stringBuffer = new StringBuffer();
        for (Object sourceParam : sourceParams) {
            if (sourceParam instanceof String[]) {
                String[] stringScripts = (String[]) sourceParam;
                for (String script : stringScripts) {
                    stringBuffer.append(script);
                }
            } else {
                stringBuffer.append(sourceParam.toString());
            }
        }

        persistenceRepository.setQueryString("执行脚本sql:" + stringBuffer);
        return persistenceRepository;
    }

    /**
     * description 执行SQL 语句
     *
     * @param connection
     * @param sourceParams
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/11/22 上午11:02
     */
    @Override
    public Object doExecute(Connection connection, Object[] sourceParams) throws Exception {
        if (ObjectUtils.isEmpty(sourceParams)) {
            return false;
        }
        // sql 脚本
        log.info("数据初始化开始: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        Object sourceParam = sourceParams[0];
        // 创建 script 文件

        File scriptTempFile = File.createTempFile(UUID.randomUUID().toString(), ".script-sql");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(scriptTempFile));

        if (ObjectUtils.isEmpty(sourceParam)) {
            return false;
        }
        if (sourceParam instanceof String[]) {
            String[] stringScripts = (String[]) sourceParam;
            for (String script : stringScripts) {
                bufferedWriter.write(script);
            }
        } else {
            bufferedWriter.write(sourceParam.toString());
        }
        bufferedWriter.close();
        Resource resource = new FileSystemResource(scriptTempFile);
        resourceDatabasePopulator.addScripts(resource);
        try {
            resourceDatabasePopulator.populate(connection);
        } catch (Exception e) {
            List<String> allLines = Files.readAllLines(scriptTempFile.toPath());
            throw new SQLException("执行sql失败:" + String.join(NormalUsedString.NEWLINE, allLines));
        } finally {
            scriptTempFile.deleteOnExit();
        }
        log.info("数据初始化结束: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        return true;
    }

}
