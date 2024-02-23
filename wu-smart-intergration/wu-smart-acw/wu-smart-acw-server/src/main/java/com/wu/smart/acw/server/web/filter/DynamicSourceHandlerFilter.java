package com.wu.smart.acw.server.web.filter;


import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDSContextHolder;
import com.wu.framework.inner.lazy.persistence.util.DataSourceUrlParsingUtil;
import com.wu.framework.inner.lazy.persistence.conf.ClassLazyDynamicEndpoint;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@Configuration
public class DynamicSourceHandlerFilter extends OncePerRequestFilter {

    private final LazyDataSourceProperties lazyDataSourceProperties;

    public DynamicSourceHandlerFilter(LazyDataSourceProperties lazyDataSourceProperties) {
        this.lazyDataSourceProperties = lazyDataSourceProperties;
    }

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String requestURI = request.getRequestURI();
        log.debug("请求地址：" + requestURI);
        ClassLazyDynamicEndpoint lazyDynamicEndpoint = new ClassLazyDynamicEndpoint();

        // 请求头中有实例ID、schema
        String datasourceInstanceId = request.getHeader("datasource_instance_id");
        String datasource_schema = request.getHeader("datasource_schema");

        if (!ObjectUtils.isEmpty(datasourceInstanceId)) {
            lazyDynamicEndpoint.setName(datasourceInstanceId);
            if (!ObjectUtils.isEmpty(datasource_schema)) {
                lazyDynamicEndpoint.setSchema(datasource_schema);
            }
            DynamicLazyDSContextHolder.push(lazyDynamicEndpoint);
        }

        // 根据用户ID切换schema
        String zoneId = request.getHeader("zone_id");

        if (!ObjectUtils.isEmpty(zoneId)) {
            String url = lazyDataSourceProperties.getUrl();
            String schema = DataSourceUrlParsingUtil.schema(url);
            lazyDynamicEndpoint.setSchema(schema + "_" + zoneId);
//            DynamicLazyDSContextHolder.push(lazyDynamicEndpoint);
        }
        // 拦截器处理
        filterChain.doFilter(request, response);

        // 清除 Lazy上下文的数据源
        DynamicLazyDSContextHolder.clear();
    }

}
