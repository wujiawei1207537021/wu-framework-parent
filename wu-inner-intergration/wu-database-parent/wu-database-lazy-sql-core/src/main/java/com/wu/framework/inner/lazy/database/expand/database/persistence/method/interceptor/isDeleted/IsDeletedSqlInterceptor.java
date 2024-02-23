package com.wu.framework.inner.lazy.database.expand.database.persistence.method.interceptor.isDeleted;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.interceptor.AbstractSqlInterceptor;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.Condition;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.condition.part.SqlPart;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 逻辑删除控制
 */
public class IsDeletedSqlInterceptor extends AbstractSqlInterceptor {

    private final LazyIsDeletedLineHandler lazyIsDeletedLineHandler;

    public IsDeletedSqlInterceptor(LazyIsDeletedLineHandler lazyIsDeletedLineHandler) {
        this.lazyIsDeletedLineHandler = lazyIsDeletedLineHandler;
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
            boolean ignoreTable = lazyIsDeletedLineHandler.ignoreTable(table);
            if (ignoreTable) {
                return;
            }
        }
        SqlPart sqlPart = persistenceRepository.getSqlPart();

        List<Condition> conditions = tableConditionInterceptor(persistenceRepository); // 条件字段
        String isDeletedColumn = lazyIsDeletedLineHandler.getIsDeletedColumn();
        Boolean isDeleted = lazyIsDeletedLineHandler.getIsDeleted();
        // 抓取到 别名
        for (Condition condition : conditions) {
            Object rowName = condition.getRowName();
            if (rowName.toString().contains(isDeletedColumn)) {
                isDeletedColumn = rowName.toString();
                break;
            }
        }
        sqlPart.put(isDeletedColumn, "=", isDeleted);
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

    public static final class DefaultLazyIsDeletedLineHandler implements LazyIsDeletedLineHandler {

        /**
         * 获取租户 ID 值表达式，只支持单个 ID 值
         * <p>
         *
         * @return 租户 ID 值表达式
         */
        @Override
        public Boolean getIsDeleted() {
            // 上下文获取租户ID

            return false;
        }
    }

}

