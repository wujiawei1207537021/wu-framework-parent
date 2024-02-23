package com.wu.framework.sql.audit.platform.config;

import com.wu.framework.inner.layer.data.NormalUsedString;
import com.wu.framework.inner.lazy.config.LazyApplicationConfig;
import com.wu.framework.inner.lazy.config.prop.LazyDataSourceProperties;
import com.wu.framework.inner.lazy.database.datasource.proxy.sql.LazySQLContext;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyAuditDSContextHolder;
import com.wu.framework.inner.lazy.database.dynamic.toolkit.DynamicLazyDataSourceContextHolder;
import com.wu.framework.inner.lazy.database.expand.database.persistence.audit.AbstractAudit;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.LambdaTableType;
import com.wu.framework.inner.lazy.database.util.SqlUtils;
import com.wu.framework.inner.lazy.persistence.util.DataSourceUrlParsingUtil;
import com.wu.framework.inner.lazy.database.util.SqlColumnUtils;
import com.wu.framework.inner.lazy.persistence.conf.LazyDynamicEndpoint;
import com.wu.framework.sql.audit.platform.domain.model.sql.audit.SqlAudit;
import com.wu.framework.sql.audit.platform.domain.model.sql.audit.SqlAuditRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.sql.DataSource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/05/13 16:03
 */
@Component
public class AuditPlatform extends AbstractAudit {

    private final LazyApplicationConfig lazyApplicationConfig;
    private final SqlAuditRepository sqlAuditRepository;
    private final LazyDataSourceProperties lazyDataSourceProperties;

    public AuditPlatform(LazyApplicationConfig lazyApplicationConfig,
                         SqlAuditRepository sqlAuditRepository,
                         LazyDataSourceProperties lazyDataSourceProperties) {
        this.lazyApplicationConfig = lazyApplicationConfig;
        this.sqlAuditRepository = sqlAuditRepository;
        this.lazyDataSourceProperties = lazyDataSourceProperties;
    }

    /**
     * 判断当前是否允许sql审计
     *
     * @param lazySQLContext 预执行SQL需要的属性
     * @return
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
        LazyDynamicEndpoint peek = DynamicLazyAuditDSContextHolder.peek();
        try {
            String sql = lazySQLContext.getSql();
            SqlAudit sqlAudit = new SqlAudit();
            sqlAudit.setExecuteSql(sql);
            LambdaTableType lambdaTableType = SqlColumnUtils.executeTypeInSql(sql);
            List<String> tablesInSql = SqlUtils.tablesInSql(sql);

            tablesInSql= tablesInSql.stream().map(table -> table.replaceAll(NormalUsedString.SINGLE_QUOTE,NormalUsedString.EMPTY)).collect(Collectors.toList());

            sqlAudit.setTableList(tablesInSql);
            sqlAudit.setSqlType(lambdaTableType.getValue());
            sqlAudit.setApplicationName(lazyApplicationConfig.getName());


            DataSource dataSource = DynamicLazyDataSourceContextHolder.peek();
            sqlAudit.setDatasource(dataSource);
            sqlAudit.setId(UUID.randomUUID().toString());
            sqlAudit.setCreateTime(LocalDateTime.now());
            sqlAudit.setUpdateTime(LocalDateTime.now());
//            sqlAuditUo.setSchema(LazyDataSourceUtils.getConnection(dataSource).getSchema());
//            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            sqlAudit.setRequestId(getRequestId());
            sqlAudit.setIp(getIpAddr());

            String url = lazyDataSourceProperties.getUrl();
            String schema = DataSourceUrlParsingUtil.schema(url);

            if (!ObjectUtils.isEmpty(peek)) {
                String name = peek.getName();
                schema = peek.getSchema();
                sqlAudit.setInstanceId(ObjectUtils.isEmpty(name) ? NormalUsedString.DEFAULT : name);
            } else {
                sqlAudit.setInstanceId(NormalUsedString.DEFAULT);
            }
            sqlAudit.setSchema(schema);


            sqlAuditRepository.story(sqlAudit);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }


    public static String getRequestId() {
        String requestId = null;

        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            requestId = request.getRequestId();
        } catch (Exception e) {

            requestId = "";
        }

        return requestId;
    }

    public static String getIpAddr() {
        String ipAddress = null;
        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            final HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if ("127.0.0.1".equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }
}
