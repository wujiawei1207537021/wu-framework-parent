package com.wu.framework.tts.server.platform.starter.config;

import com.wu.framework.inner.lazy.stereotype.LazyScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.wu.framework.tts.server.platform.starter")
@LazyScan(scanBasePackages = "com.wu.framework.tts.server.platform.starter.infrastructure.entity")
public class TTSPlatformConfig {
}
