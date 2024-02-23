package com.wu.framework.inner.lazy.database.expand.database.persistence.audit;

import com.wu.framework.inner.layer.toolkit.DynamicLazyAttributeContextHolder;
import com.wu.framework.inner.lazy.database.datasource.proxy.audit.LazyAuditAdapter;
import com.wu.framework.inner.lazy.database.datasource.proxy.sql.LazySQLContext;
import com.wu.framework.inner.lazy.database.datasource.proxy.toolkit.DynamicLazySQLContextHolder;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyAuditDSContextHolder;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.PersistenceRepository;
import com.wu.framework.inner.lazy.persistence.conf.LazyDynamicEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description sql 审计适配器
 *
 * @author 吴佳伟
 * @date 2023/05/13 12:33
 */
public class AuditAdapter implements LazyAuditAdapter {

    private final Logger log = LoggerFactory.getLogger(AuditAdapter.class);

    private final List<Audit> auditList = new ArrayList<>();

    private final ApplicationContext applicationContext;

    public AuditAdapter(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void audit(PersistenceRepository persistenceRepository) {
//        if (ObjectUtils.isEmpty(auditList)) {
//            Map<String, Audit> auditMap = applicationContext.getBeansOfType(Audit.class);
//            if (!ObjectUtils.isEmpty(auditMap)) {
//                auditList.addAll(auditMap.values());
//            }
//        }
//        Boolean peek = DynamicLazyAttributeContextHolder.peek(AuditAdapter.class);
//        // 使用原始数据源
//        LazyDynamicEndpoint lazyDynamicEndpoint = DynamicLazyDSContextHolder.peek();
//        DynamicLazyDSContextHolder.clear();
//        if (ObjectUtils.isEmpty(peek) || peek) {
//            // 执行sql审计
//            auditList.forEach(audit -> {
//                // 删除 sql 审计 标识
//                DynamicLazyAttributeContextHolder.push(AuditAdapter.class, false);
//                if (audit.supports(persistenceRepository)) {
//                    try {
//                        audit.audit(persistenceRepository);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        throw new RuntimeException("SQL 审计异常" + persistenceRepository);
//                    }
//                }
//                DynamicLazyAttributeContextHolder.clear(AuditAdapter.class);
//            });
//        }
//        if (null != lazyDynamicEndpoint) {
//            DynamicLazyDSContextHolder.push(lazyDynamicEndpoint);
//        }


    }

    public void setAuditList(List<Audit> auditList) {
        this.auditList.addAll(auditList);
    }

    /**
     *
     */
    @Override
    public void audit() {
        List<LazySQLContext> stringList = DynamicLazySQLContextHolder.peek();

        if (ObjectUtils.isEmpty(stringList)) {
            return;
        }
        for (LazySQLContext lazySQLContext : stringList) {
            if (ObjectUtils.isEmpty(auditList)) {
                Map<String, Audit> auditMap = applicationContext.getBeansOfType(Audit.class);
                if (!ObjectUtils.isEmpty(auditMap)) {
                    auditList.addAll(auditMap.values());
                }
            }
            String sql = lazySQLContext.getSql();
            Boolean peek = DynamicLazyAttributeContextHolder.peek(AuditAdapter.class);
            // 使用原始数据源
            LazyDynamicEndpoint lazyDynamicEndpoint = DynamicLazyDSContextHolder.peek();
            DynamicLazyDSContextHolder.clear();
            if (ObjectUtils.isEmpty(peek) || peek) {
                DynamicLazyAuditDSContextHolder.push(lazyDynamicEndpoint);
                // 执行sql审计
                auditList.forEach(audit -> {
                    // 删除 sql 审计 标识
                    DynamicLazyAttributeContextHolder.push(AuditAdapter.class, false);
                    if (audit.supports(lazySQLContext)) {
                        try {
                            audit.audit(lazySQLContext);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RuntimeException("SQL 审计异常" + sql);
                        }
                    }
                    DynamicLazyAttributeContextHolder.clear(AuditAdapter.class);
                });
                DynamicLazyAuditDSContextHolder.clear();
            }
            if (null != lazyDynamicEndpoint) {
                DynamicLazyDSContextHolder.push(lazyDynamicEndpoint);
            }
        }


    }
}
