package com.wuframework.system.component.listen;

import com.wuframework.system.serivce.SysConfigService;
import com.wuframework.system.serivce.SysDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @description: 监听程序启动
 * @author: Xiongxz
 * @create: 2018-11-28 11:55
 **/
@Slf4j
@Configuration
public class ApplicationStartedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //服务系统配置  以注解的方式注入我们需要的bean，会报空指针异常，因此需要以代码中的方式获取我们要的bean
        SysConfigService sysConfigService = contextRefreshedEvent.getApplicationContext().getBean(SysConfigService.class);
        SysDeptService sysDeptService = contextRefreshedEvent.getApplicationContext().getBean(SysDeptService.class);

        log.info("<----------添加系统配置[sys_config]到redis---------->");
        sysConfigService.selectSysConfigListAll("");
        log.info("<----------系统配置添加成功---------->");
        log.info("<----------添加部门[sys_dept]到redis---------->");
//        sysDeptService.saveRedisByDept();
        log.info("<----------部门添加成功---------->");
    }


    @PostConstruct
    public void  ss(){
        System.out.println("start");
    }

    @PreDestroy
    public void  preDestory(){
        System.out.println("end");
    }
}
