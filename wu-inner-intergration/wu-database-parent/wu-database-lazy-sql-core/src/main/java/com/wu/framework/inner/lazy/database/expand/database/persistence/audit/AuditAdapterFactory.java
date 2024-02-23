package com.wu.framework.inner.lazy.database.expand.database.persistence.audit;

import com.wu.framework.inner.lazy.database.expand.database.persistence.audit.lazy.LazyMysqlAudit;

import java.util.Arrays;
import java.util.List;

/**
 * description sql 审计工厂
 *
 * @author 吴佳伟
 * @date 2023/05/13 12:55
 */
public class AuditAdapterFactory {

    public static AuditAdapter createAuditAdapter() {
        AuditAdapter auditAdapter = new AuditAdapter(null);
        auditAdapter.setAuditList(defaultAuditList());
        return auditAdapter;
    }

    /**
     * 默认sql 审计
     *
     * @return
     */
    private static List<Audit> defaultAuditList() {
        return Arrays.asList(new LazyMysqlAudit());
    }
}
