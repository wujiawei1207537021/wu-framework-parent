package com.wu.framework.easy.stereotype.upsert.ienum;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import java.util.HashMap;
import java.util.Map;

/**
 * description 默认字段转换
 *
 * @author Jia wei Wu
 * @date 2020/9/11 下午12:21
 */
@ConditionalOnMissingBean(UserConvertService.class)
public class DefaultUserConvertService implements UserConvertService, InitializingBean {


    @Override
    public Map<String, Map<String, String>> userConvert(Class<?> aClass) {
        return new HashMap<>();
    }

    @Override
    public void afterPropertiesSet() {
    }
}
