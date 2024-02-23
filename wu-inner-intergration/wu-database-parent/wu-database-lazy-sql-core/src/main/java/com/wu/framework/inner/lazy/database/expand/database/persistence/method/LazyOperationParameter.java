package com.wu.framework.inner.lazy.database.expand.database.persistence.method;


import com.wu.framework.inner.lazy.config.LazyOperationConfig;
import com.wu.framework.inner.lazy.database.expand.database.persistence.method.interceptor.SqlInterceptorAdapter;
import com.wu.framework.inner.lazy.database.expand.database.persistence.translation.adapter.LazyTranslationAdapter;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * LazyOperation 需要的参数
 */
@Accessors(chain = true)
@Data
public class LazyOperationParameter {
    private LazyOperationConfig lazyOperationConfig; // 配置信息
    private SqlInterceptorAdapter sqlInterceptorAdapter; // sql 拦截器
    /**
     * 跨表转译适配器
     */
    private LazyTranslationAdapter lazyTranslationAdapter;

    public LazyOperationParameter() {
    }
    public LazyOperationParameter(LazyOperationConfig lazyOperationConfig, SqlInterceptorAdapter sqlInterceptorAdapter, LazyTranslationAdapter lazyTranslationAdapter) {
        this.lazyOperationConfig = lazyOperationConfig;
        this.sqlInterceptorAdapter = sqlInterceptorAdapter;
        this.lazyTranslationAdapter = lazyTranslationAdapter;
    }

}
