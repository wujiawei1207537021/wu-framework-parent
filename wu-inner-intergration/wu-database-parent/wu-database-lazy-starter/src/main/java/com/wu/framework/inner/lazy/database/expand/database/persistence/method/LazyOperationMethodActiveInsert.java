package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.layer.stereotype.proxy.ProxyStrategicApproach;
import com.wu.framework.inner.lazy.database.converter.PersistenceConverter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.constant.LayerOperationMethodCounts;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.Persistence;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe : 灵活更新 去除null的字段
 * @date : 2020/7/3 下午10:28
 */
@Component
public class LazyOperationMethodActiveInsert extends AbstractLazyOperationMethod {

    @Override
    public PersistenceRepository getPersistenceRepository(Method method, Object[] args) throws Exception {
        if (ObjectUtils.isEmpty(args)) {
            throw new IllegalArgumentException("fail invoke this method in method" + method.getName());
        }
        Object object = args[0];

        // TODO EASYHASHMAP
        Persistence persistence = PersistenceConverter.activeInsertPrepared(object);
        StringBuffer stringBuffer = new StringBuffer(persistence.getExecutionEnum().getExecution());
        stringBuffer.append(persistence.getTableName());
        stringBuffer.append(NormalUsedString.LEFT_BRACKET);
        stringBuffer.append(String.join(",", persistence.getColumnList()));
        stringBuffer.append(") values ( ");
        stringBuffer.append(persistence.getCondition());
        stringBuffer.append(" ) ON DUPLICATE KEY UPDATE ");
        stringBuffer.append(persistence.getColumnList().stream().map(s -> s + " =VALUES (" + s + ")").collect(Collectors.joining(",")));
        String sql = stringBuffer.toString();
        PersistenceRepository persistenceRepository = new PersistenceRepository();
        persistenceRepository.setQueryString(sql);
        persistenceRepository.setBinaryList(persistence.getBinaryList());
        return persistenceRepository;
    }

    /**
     * description 执行SQL 语句
     *
     * @param preparedStatement
     * @param persistenceRepository
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/11/22 上午11:02
     */
    @Override
    public Object execute(PreparedStatement preparedStatement, PersistenceRepository persistenceRepository) throws SQLException {
        return super.execute(preparedStatement, persistenceRepository);
    }
}
