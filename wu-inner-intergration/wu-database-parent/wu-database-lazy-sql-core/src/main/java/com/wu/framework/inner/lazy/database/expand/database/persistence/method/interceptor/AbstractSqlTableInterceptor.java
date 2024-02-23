package com.wu.framework.inner.lazy.database.expand.database.persistence.method.interceptor;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.Condition;
import com.wu.framework.inner.lazy.database.util.SqlColumnUtils;
import com.wu.framework.inner.lazy.database.util.SqlUtils;

import java.util.List;

/**
 * 表信息拦截
 * 获取执行sql中的表信息、查询字段、条件字段
 */
public abstract class AbstractSqlTableInterceptor implements SqlInterceptor {

    /**
     * 解析表名称
     *
     * @param persistenceRepository 预执行SQL需要的属性
     * @return 执行的表
     */
    public List<String> tableInterceptor(PersistenceRepository persistenceRepository) {
        String sql = persistenceRepository.getBeforeQueryString();
        return SqlUtils.tablesInSql(sql);
    }

    /**
     * 解析 条件字段
     *
     * @param persistenceRepository 预执行SQL需要的属性
     * @return 条件集合
     */
    public List<Condition> tableConditionInterceptor(PersistenceRepository persistenceRepository) {
        String sql = persistenceRepository.getBeforeQueryString();
        return SqlColumnUtils.columnConditionInSql(sql);
    }


}
