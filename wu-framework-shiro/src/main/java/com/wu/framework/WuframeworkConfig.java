package com.wu.framework;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @ Description : 用于自动注入 @ Author : wujiawei @ CreateDate : 2019/12/17 0017 11:36 @
 * UpdateUser : wujiawei @ UpdateDate : 2019/12/17 0017 11:36 @ UpdateRemark : 修改内容 @ Version : 1.0
 * wuframework
 * <p>
 */

@Configuration
@ComponentScan
@ConfigurationPropertiesScan
@EnableAsync
@EntityScan
public class WuframeworkConfig {
    // auto import
}
