package com.wu.framework.inner.lazy.database.expand.database.persistence.method.ddl;

import com.wu.framework.inner.lazy.config.enums.DDLAuto;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepositoryFactory;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.LazyOperationParameter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import com.wu.framework.inner.lazy.source.SqlSourceClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * describe : 更新表
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/2 7:56 下午
 */
@Slf4j
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class LazyOperationMethodUpdateTable extends AbstractLazyDDLOperationMethod {
    public LazyOperationMethodUpdateTable(LazyOperationParameter lazyOperationParameter) {
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
        PersistenceRepository persistenceRepository = PersistenceRepositoryFactory.create();
        persistenceRepository.setExecutionType(LambdaTableType.DDL);
        List<Class<?>> classes = new ArrayList<>();

        for (Object sourceParam : sourceParams) {
            if (sourceParam instanceof Object[]) {
                Object[] objects = (Object[]) sourceParam;
                for (Object o : objects) {
                    Class<?> entityClass = (Class<?>) o;
                    classes.add(entityClass);
                }
            } else {
                Class<?> entityClass = (Class<?>) sourceParam;
                classes.add(entityClass);
            }
        }

        String tables = classes.stream().map(clazz -> {
//            LazyTableUtil.analyzeLazyTable(aClass).getTableName()
            SqlSourceClass sqlSourceClass =  SqlSourceClass.getInstance(clazz);
            LazyTableEndpoint lazyTableEndpoint = sqlSourceClass.getLazyTableEndpoint();
            return lazyTableEndpoint.getTableName();
        }).collect(Collectors.joining("、"));
        persistenceRepository.setQueryString("执行表[" + tables + "]结构更新sql");
        return persistenceRepository;
    }

    /**
     * describe  执行SQL 语句
     *
     * @param connection
     * @param sourceParams
     * @return
     * @author Jia wei Wu
     * @date 2022/1/2 8:10 下午
     **/
    @Override
    public Object doExecute(Connection connection, Object[] sourceParams) throws Exception {
        // 创建表
        final Object param = sourceParams[0];

        if (param instanceof Object[]) {
            Object[] objects = (Object[]) param;
            for (Object o : objects) {
                Class<?> entityClass = (Class<?>) o;
                update(entityClass, connection);
            }
        } else {
            Class<?> entityClass = (Class<?>) param;
            update(entityClass, connection);
        }
        return true;
    }

    protected void update(Class entityClass, Connection connection) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        SqlSourceClass sqlSourceClass =  SqlSourceClass.getInstance(entityClass);
        LazyTableEndpoint lazyTableEndpoint = sqlSourceClass.getLazyTableEndpoint();

//        LazyTableEndpoint lazyTableEndpoint = LazyTableUtil.analyzeLazyTable(entityClass);
        perfect(connection, entityClass, DDLAuto.UPDATE);

        log.warn("update table {} success", lazyTableEndpoint.getTableName());
    }
}
