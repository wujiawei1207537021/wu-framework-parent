package com.wu.framework.doc.platform.config;

import com.wu.framework.inner.lazy.stereotype.LazyScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/05/13 16:07
 */

@ComponentScan(basePackages = "com.wu.framework.doc.platform")
@LazyScan(scanBasePackages = "com.wu.framework.doc.platform.infrastructure.entity")
public class DocPlatformConfig {
}
