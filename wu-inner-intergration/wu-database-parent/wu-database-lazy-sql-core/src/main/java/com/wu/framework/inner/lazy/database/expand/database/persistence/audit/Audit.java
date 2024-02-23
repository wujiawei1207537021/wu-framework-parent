package com.wu.framework.inner.lazy.database.expand.database.persistence.audit;

import com.wu.framework.inner.lazy.database.datasource.proxy.audit.LazyAudit;
import com.wu.framework.inner.lazy.database.datasource.proxy.sql.LazySQLContext;

/**
 * description 审计抽象接口
 *
 * @author 吴佳伟
 * @date 2023/05/13 12:35
 */
public interface Audit extends LazyAudit<LazySQLContext> {

    /**
     * 判断当前是否允许sql审计
     *
     * @param lazySQLContext 预执行SQL需要的属性
     * @return
     */
    boolean supports(LazySQLContext lazySQLContext);

    /**
     * 审计 发送sql 的对象
     *
     * @param sql 预执行SQL需要的属性
     */
    void audit(LazySQLContext lazySQLContext);
}
