package com.wu.smart.acw.client.nocode.config;

import com.wu.framework.inner.lazy.stereotype.LazyScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/08/11 17:04
 */
@LazyScan(scanBasePackages = "com.wu.smart.acw.client.nocode.provider.infrastructure.entity")
@ComponentScan(basePackages = "com.wu.smart.acw.client.nocode.provider")
public class NoCodeLazyConfig {
}
