package com.wu.framework.log.platform.config;

import com.wu.framework.inner.lazy.stereotype.LazyScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/12/29 21:57
 */
@ComponentScan(basePackages = "com.wu.framework.log.platform.provider")
@LazyScan(scanBasePackages = "com.wu.framework.log.platform.domain")
public class LogConfig {
}
