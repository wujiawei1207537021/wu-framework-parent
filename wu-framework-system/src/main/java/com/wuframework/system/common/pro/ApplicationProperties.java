package com.wuframework.system.common.pro;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ Description   :  配置spring.application 属性
 * @ Author        :  wujiawei
 * @ CreateDate    :  2020/1/16 0016 15:48
 * @ UpdateUser    :  wujiawei
 * @ UpdateDate    :  2020/1/16 0016 15:48
 * @ UpdateRemark  :  修改内容
 * @ Version       :  1.0
 * <p>
 * <p>
 * #应用名称
 * spring :
 * application:
 * name: system-base
 * version: '@project.version@'
 * accesspath: ${fdfs.file.accesspath}
 */


@Data
@Component
@ConfigurationProperties(prefix = "spring.application")
public class ApplicationProperties {

    private String name;

    private String version;

    private boolean initEnum = true;
}
