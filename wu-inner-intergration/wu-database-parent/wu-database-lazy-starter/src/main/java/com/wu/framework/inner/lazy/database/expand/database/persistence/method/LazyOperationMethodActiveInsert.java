package com.wu.framework.inner.lazy.database.expand.database.persistence.method;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.database.converter.PersistenceConverter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.Persistence;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
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
    public PersistenceRepository analyzePersistenceRepository(Object... params) throws IllegalArgumentException {
        Object object = params[0];

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
     * @param dataSource
     * @param params
     * @return
     * @params
     * @author Jia wei Wu
     * @date 2020/11/22 上午11:02
     */
    @Override
    public Object execute(DataSource dataSource, Object... params) throws Exception {
        return super.execute(dataSource, params);
    }
}
