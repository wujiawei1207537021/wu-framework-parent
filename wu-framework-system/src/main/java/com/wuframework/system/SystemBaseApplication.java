package com.wuframework.system;

import com.github.tobato.fastdfs.FdfsClientConfig;
import com.wuframework.system.component.enmus.EnableEnumReflection;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableTransactionManagement   //开启事务管理功能
@EnableAsync                    //异步执行,开启多线程
@EnableCaching                  //缓存管理功能
@Import({FdfsClientConfig.class})
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@MapperScan("com.wuframework.system.persistence.mapper")
@SpringBootApplication(scanBasePackages = {"com.wuframework.system"})
@EnableSwagger2
@EntityScan("com.wuframework")
@EnableEnumReflection
public class SystemBaseApplication {

//    public static void main(String[] agrs) {
//        SpringApplication.run(SystemBaseApplication.class, agrs);
//    }
}
