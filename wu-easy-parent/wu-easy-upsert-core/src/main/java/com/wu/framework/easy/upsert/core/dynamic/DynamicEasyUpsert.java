package com.wu.framework.easy.upsert.core.dynamic;

import com.wu.framework.easy.upsert.autoconfigure.config.SpringUpsertAutoConfigure;
import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsert;
import com.wu.framework.easy.upsert.autoconfigure.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.upsert.autoconfigure.enums.EasyUpsertType;
import com.wu.framework.easy.upsert.autoconfigure.exceptions.IllegalDataSourceException;
import com.wu.framework.easy.upsert.core.dynamic.toolkit.DynamicEasyUpsertContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * description EasyUpsert获取IEasyUpsert和注解 EasyUpsert实现类
 * 策略方式
 *
 * @author Jia wei Wu
 * @date 2020/9/14 下午2:55
 */
public class DynamicEasyUpsert extends AbstractDynamicEasyUpsert implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(DynamicEasyUpsert.class);

    private EasyUpsertType primary;
    private EasyUpsert primaryEasyUpsert;
    private ConcurrentMap<EasyUpsertType, IEasyUpsert> iEasyUpsertMap = new ConcurrentHashMap<>();


    private final List<IEasyUpsert> iEasyUpsertList;
    private final SpringUpsertAutoConfigure springUpsertAutoConfigure;

    private final ApplicationContext applicationContext;

    public DynamicEasyUpsert(List<IEasyUpsert> iEasyUpsertList, SpringUpsertAutoConfigure springUpsertAutoConfigure, ApplicationContext applicationContext) {
        this.iEasyUpsertList = iEasyUpsertList;
        this.springUpsertAutoConfigure = springUpsertAutoConfigure;
        this.applicationContext = applicationContext;
    }


    /**
     * 获取当前线程所需要的接口类型
     *
     * @return
     */
    @Override
    public IEasyUpsert determineIEasyUpsert() {
        return getIEasyUpsert(DynamicEasyUpsertContextHolder.peek());
    }


    /**
     * description 确定主数据源
     *
     * @param
     * @return
     * @exception/throws
     * @author 吴佳伟
     * @date 2021/4/29 11:01 上午
     */
    private IEasyUpsert determinePrimaryDataSource() {
        log.info("使用默认方式:【{}】", primary);
        return iEasyUpsertMap.get(primary);
    }

    private IEasyUpsert getIEasyUpsert(EasyUpsert peek) {
        if (ObjectUtils.isEmpty(peek) || peek.type().equals(EasyUpsertType.AUTO)) {
            return determinePrimaryDataSource();
        } else if (iEasyUpsertMap.containsKey(peek.type())) {
            log.info("使用方式:【{}】", peek.type());
            return iEasyUpsertMap.get(peek.type());
        } else {
            throw new IllegalDataSourceException("不能找到类型为" + peek + "的数据源");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //        数据源类型存放
        primaryEasyUpsert = defaultEasyUpsert();
        Map<String, IEasyUpsert> beansOfType = applicationContext.getBeansOfType(IEasyUpsert.class);
        if (!ObjectUtils.isEmpty(beansOfType)) {
            log.info("EasyUpsert 初始共加载 {} 种方式", beansOfType.size());
            beansOfType.forEach((s, iEasyUpsert) -> {
                EasyUpsertStrategy easyUpsertStrategy = AnnotationUtils.findAnnotation(iEasyUpsert.getClass(), EasyUpsertStrategy.class);
                if (null != easyUpsertStrategy) {
                    log.info("EasyUpsert 动态方式-加载 {} 成功", easyUpsertStrategy.easyUpsertType());
                    iEasyUpsertMap.put(easyUpsertStrategy.easyUpsertType(), iEasyUpsert);
                }
            });
            if (springUpsertAutoConfigure.getEasyUpsertType().equals(EasyUpsertType.AUTO)) {
                if (ObjectUtils.isEmpty(iEasyUpsertMap)) {
                    return;
                }
                primary = iEasyUpsertMap.keySet().iterator().next();
                log.info("当前的默认方式是 {} ", primary);
            } else {
                if (iEasyUpsertMap.containsKey(springUpsertAutoConfigure.getEasyUpsertType())) {
                    primary = springUpsertAutoConfigure.getEasyUpsertType();
                    log.info("当前的默认方式是 {} ", springUpsertAutoConfigure.getEasyUpsertType());
                } else {
                    primary = iEasyUpsertMap.keySet().iterator().next();
                    log.info("无法找到方式 【{}】 使用默认方式【{}】 ", springUpsertAutoConfigure.getEasyUpsertType(), primary);
                }
            }
        }

    }

    /**
     * 默认 注解
     *
     * @return
     */
    private EasyUpsert defaultEasyUpsert() {
        return new EasyUpsert() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return EasyUpsert.class;
            }

            /**
             * 数据源类型 默认MySQL
             */
            @Override
            public EasyUpsertType type() {
                return springUpsertAutoConfigure.getEasyUpsertType();
            }
        };
    }

}
