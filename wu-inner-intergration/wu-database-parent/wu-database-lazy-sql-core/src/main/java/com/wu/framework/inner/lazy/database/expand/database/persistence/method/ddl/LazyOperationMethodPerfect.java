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
 * describe: 完善表
 *
 * @author : Jia wei Wu
 * @version : 1.0
 * @date : 2022/1/2 5:03 下午
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Slf4j
public class LazyOperationMethodPerfect extends AbstractLazyDDLOperationMethod {

    public LazyOperationMethodPerfect(LazyOperationParameter lazyOperationParameter) {
        super(lazyOperationParameter);
    }


    /**
     * @param sourceParams 原始数据
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
        persistenceRepository.setQueryString("执行完善表[" + tables + "]结构sql");
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
        final Object param = sourceParams[0];
        if (param instanceof Object[]) {
            Object[] objects = (Object[]) param;
            for (Object o : objects) {
                if (null == o) {
                    continue;
                }
                Class<?> paramClass = o.getClass();
                if (Class.class.isAssignableFrom(paramClass)) {
                    final Class<?> entityClass = (Class<?>) o;
                    perfect(entityClass, connection);
                } else if (LazyTableEndpoint.class.isAssignableFrom(paramClass)) {
                    LazyTableEndpoint lazyTableEndpoint = (LazyTableEndpoint) o;
                    perfect(connection, lazyTableEndpoint, DDLAuto.PERFECT);
                }

            }
        } else {
            if (null == param) {
                return false;
            }
            Class<?> paramClass = param.getClass();
            if (Class.class.isAssignableFrom(paramClass)) {
                final Class<?> entityClass = (Class<?>) param;
                perfect(entityClass, connection);
            } else if (LazyTableEndpoint.class.isAssignableFrom(paramClass)) {
                LazyTableEndpoint lazyTableEndpoint = (LazyTableEndpoint) param;
                perfect(connection, lazyTableEndpoint, DDLAuto.PERFECT);
            }
        }
        return true;
    }

    protected void perfect(Class entityClass, Connection connection) throws SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
//        LazyTableEndpoint lazyTableEndpoint = LazyTableUtil.analyzeLazyTable(entityClass);
        SqlSourceClass sqlSourceClass =  SqlSourceClass.getInstance(entityClass);
        LazyTableEndpoint lazyTableEndpoint = sqlSourceClass.getLazyTableEndpoint();
        perfect(connection, entityClass, DDLAuto.UPDATE);

        log.warn("perfect table {} success", lazyTableEndpoint.getTableName());
    }
}
