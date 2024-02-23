package com.wu.framework.inner.lazy.database.datasource.proxy.audit;

import com.wu.framework.inner.lazy.database.datasource.proxy.toolkit.DynamicLazySQLContextHolder;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 懒人sql审计适配器
 */
public class LazyAuditHolderAdapter {


    private static final ConcurrentHashMap<String, LazyAuditAdapter> AUDIT_ADAPTER_CONCURRENT_HASHMAP = new ConcurrentHashMap<>();

    /**
     * 审计
     */
    public static void audit() {
        AUDIT_ADAPTER_CONCURRENT_HASHMAP.forEachValue(1L, lazyAuditAdapter -> {
            try {
                lazyAuditAdapter.audit();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        DynamicLazySQLContextHolder.clear();
    }

    /**
     * 添加 自定义审计适配器
     *
     * @param key              审计适配器ID 默认当前bean
     * @param lazyAuditAdapter 审计适配器
     */
    public static void addLazyAuditAdapter(String key, LazyAuditAdapter lazyAuditAdapter) {
        AUDIT_ADAPTER_CONCURRENT_HASHMAP.put(key, lazyAuditAdapter);
    }

}
