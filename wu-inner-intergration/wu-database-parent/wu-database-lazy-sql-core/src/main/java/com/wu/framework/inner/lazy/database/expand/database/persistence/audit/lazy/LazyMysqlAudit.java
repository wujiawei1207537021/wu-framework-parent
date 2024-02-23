package com.wu.framework.inner.lazy.database.expand.database.persistence.audit.lazy;

import com.wu.framework.inner.lazy.database.datasource.proxy.audit.LazyAudit;
import com.wu.framework.inner.lazy.database.datasource.proxy.sql.LazySQLContext;
import com.wu.framework.inner.lazy.database.expand.database.persistence.audit.AbstractAudit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/05/13 12:36
 */
public class LazyMysqlAudit extends AbstractAudit implements LazyAudit<LazySQLContext> {
    private final Logger log = LoggerFactory.getLogger(LazyMysqlAudit.class);

    /**
     * 判断当前是否允许sql审计
     *
     * @return boolean
     */
    @Override
    public boolean supports(LazySQLContext lazySQLContext) {
        return true;
    }

    /**
     * 审计 发送sql 的对象
     *
     * @param lazySQLContext 预执行SQL需要的属性
     */
    @Override
    public void audit(LazySQLContext lazySQLContext) {
        log.debug("执行SQL: {}", lazySQLContext);
    }
}
