package com.wu.framework.inner.lazy.database.expand.database.persistence.method.ddl;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationParameter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * describe :  执行 sql 文件
 * @date : 2020/7/3 下午10:28
 */
@Slf4j
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyOperationMethodScriptRunner extends AbstractLazyDDLOperationMethod {


    public LazyOperationMethodScriptRunner(LazyOperationParameter lazyOperationParameter) {
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
        persistenceRepository.setExecutionType(LambdaTableType.SCRIPT_RUNNER);
        List<String> pathList = new ArrayList<>();
        for (Object sourceParam : sourceParams) {
            if (sourceParam instanceof Resource[]) {
                Resource[] resources = (Resource[]) sourceParam;
                for (Resource resource : resources) {
                    String path = resource.getFile().getPath();
                    pathList.add(path);
                }
            } else {
                String path = ((Resource) sourceParam).getFile().getPath();
                pathList.add(path);
            }

        }

        persistenceRepository.setQueryString("执行sql文件：" + String.join("、", pathList));
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
        // sql 脚本
        log.info("数据初始化开始: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        Object sourceParam = sourceParams[0];
        if (sourceParam instanceof Resource[]) {
            Resource[] resources = (Resource[]) sourceParam;
            for (Resource resource : resources) {
                resourceDatabasePopulator.addScripts(resource);
            }
        } else {
            resourceDatabasePopulator.addScripts((Resource) sourceParam);
        }

        // 通过直接读取sql文件执行
//        Resource resources = new ClassPathResource("sql/client_api_auth.sql");
        resourceDatabasePopulator.populate(connection);
        log.info("数据初始化结束: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        return true;
    }

}
