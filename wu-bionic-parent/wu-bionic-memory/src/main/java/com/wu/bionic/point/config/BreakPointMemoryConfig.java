package com.wu.bionic.point.config;

import com.wu.bionic.point.BreakPointMemory;
import com.wu.bionic.point.BreakPointMemoryAbstractPointcutAdvisor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2021/2/2 下午7:27
 */
@Configuration
public class BreakPointMemoryConfig implements InitializingBean {

    private final BreakPointMemory breakPointMemory;

    public BreakPointMemoryConfig(BreakPointMemory breakPointMemory) {
        this.breakPointMemory = breakPointMemory;
    }

    /**
     * description 添加切面
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/2/11 下午12:06
     */
    @Bean
    public BreakPointMemoryAbstractPointcutAdvisor breakPointMemoryAbstractPointcutAdvisor(BreakPointMemory breakPointMemory) {
        return new BreakPointMemoryAbstractPointcutAdvisor(new BreakPointMemoryAbstractPointcutAdvisor.BreakPointAnnotationInterceptor(breakPointMemory));
    }


    @Override
    public void afterPropertiesSet() throws Exception {
    }

}
