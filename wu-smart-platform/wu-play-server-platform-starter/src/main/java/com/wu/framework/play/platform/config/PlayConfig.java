package com.wu.framework.play.platform.config;

import com.wu.framework.inner.lazy.stereotype.LazyScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * describe : 平台配置
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/1/30 17:54
 */
@ComponentScan(basePackages = "com.wu.framework.play.platform")
@LazyScan(scanBasePackages = "com.wu.framework.play.platform.infrastructure.entity")
public class PlayConfig {

}
