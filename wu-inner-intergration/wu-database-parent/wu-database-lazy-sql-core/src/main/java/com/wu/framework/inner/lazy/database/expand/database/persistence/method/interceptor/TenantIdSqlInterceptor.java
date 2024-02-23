package com.wu.framework.inner.lazy.database.expand.database.persistence.method.interceptor;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.interceptor.tenant.LazyTenantLineHandler;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.SqlPart;
import com.wu.framework.inner.lazy.persistence.conf.LazyTableEndpoint;
import com.wu.framework.inner.lazy.persistence.util.LazyTableUtil;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * 租户ID 列（ TenantId 行级 ）
 */
public class TenantIdSqlInterceptor extends AbstractSqlInterceptor {

    private final LazyTenantLineHandler lazyTenantLineHandler;

    public TenantIdSqlInterceptor(LazyTenantLineHandler lazyTenantLineHandler) {
        this.lazyTenantLineHandler = lazyTenantLineHandler;
    }

    /**
     * before insert sql解析拦截
     *
     * @param persistenceRepository 预执行SQL需要的属性
     */
    @Override
    protected void beforeInsertInterceptor(PersistenceRepository persistenceRepository) {

        // 解析表

        // 获取添加字段
        // 重新修改执行sql

        return;
    }

    /**
     * before delete sql解析拦截
     *
     * @param persistenceRepository 预执行SQL需要的属性
     */
    @Override
    protected void beforeDeleteInterceptor(PersistenceRepository persistenceRepository) {
        intercept(persistenceRepository);

    }

    /**
     * before update sql解析拦截
     *
     * @param persistenceRepository 预执行SQL需要的属性
     */
    @Override
    protected void beforeUpdateInterceptor(PersistenceRepository persistenceRepository) {
        intercept(persistenceRepository);
    }

    private void intercept(PersistenceRepository persistenceRepository) {
        List<String> tableList = tableInterceptor(persistenceRepository);
        for (String table : tableList) {
            boolean ignoreTable = lazyTenantLineHandler.ignoreTable(table);
            if (ignoreTable) {
                return;
            }
        }

        String tenantIdColumn = lazyTenantLineHandler.getTenantIdColumn();
        String tenantId = lazyTenantLineHandler.getTenantId();


        List<LazyTableEndpoint> currentLazyTableEndpointList = LazyTableUtil.getCurrentLazyTableEndpointList();
        SqlPart sqlPart = persistenceRepository.getSqlPart();
        // 含有租户ID的表
        List<LazyTableEndpoint> lazyTableEndpointList = currentLazyTableEndpointList
                .stream()
                .filter(lazyTableEndpoint -> tableList.contains(lazyTableEndpoint.getTableName()))
                .filter(lazyTableEndpoint -> lazyTableEndpoint.getFieldEndpoints().stream().anyMatch(lazyTableFieldEndpoint -> tenantIdColumn.equals(lazyTableFieldEndpoint.getColumnName())))
                .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(LazyTableEndpoint::getTableName))), ArrayList::new));

        for (LazyTableEndpoint lazyTableEndpoint : lazyTableEndpointList) {
            // 表名+字段名
            sqlPart.put(lazyTableEndpoint.getTableName() + "." + tenantIdColumn, "=", tenantId);
        }


        persistenceRepository = sqlPart.persistenceRepository();
        String newSql = persistenceRepository.getBeforeQueryString();
        persistenceRepository.setQueryString(newSql);
    }

    /**
     * before select sql解析拦截
     *
     * @param persistenceRepository 预执行SQL需要的属性
     */
    @Override
    protected void beforeSelectInterceptor(PersistenceRepository persistenceRepository) {
        intercept(persistenceRepository);
    }

    /**
     * 支持
     *
     * @param persistenceRepository 预执行SQL需要的属性
     * @return 布尔类型
     */
    @Override
    public boolean support(PersistenceRepository persistenceRepository) {
        // 支持增删改查
        if (ObjectUtils.isEmpty(persistenceRepository)) return false;
        LambdaTableType executionType = persistenceRepository.getExecutionType();

        return executionType.equals(LambdaTableType.INSERT)
                || executionType.equals(LambdaTableType.UPSERT)
                || executionType.equals(LambdaTableType.DELETE)
                || executionType.equals(LambdaTableType.UPDATE)
                || executionType.equals(LambdaTableType.SELECT)
                || executionType.equals(LambdaTableType.BATCH);
    }
}

class DefaultLazyTenantLineHandler implements LazyTenantLineHandler {

    /**
     * 获取租户 ID 值表达式，只支持单个 ID 值
     * <p>
     *
     * @return 租户 ID 值表达式
     */
    @Override
    public String getTenantId() {
        // 上下文获取租户ID

        return null;
    }
}
