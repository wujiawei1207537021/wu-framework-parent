package com.wu.framework.automation.platform.server.starter.config;

import com.wu.framework.inner.lazy.stereotype.LazyScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.wu.framework.automation.platform.server.starter")
@LazyScan(scanBasePackages = "com.wu.framework.automation.platform.server.starter.infrastructure.entity")
public class AuditPlatformConfig {
}
