package com.wu.framework.easy.stereotype.upsert.component;

import com.wu.framework.easy.stereotype.upsert.IEasyUpsert;
import com.wu.framework.easy.stereotype.upsert.dynamic.EasyUpsertStrategy;
import com.wu.framework.easy.stereotype.upsert.enums.EasyUpsertType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.List;

/**
 * @author : Jia wei Wu
 * @version 1.0
 * @describe :
 * @date : 2020/12/20 8:27 下午
 */
@Slf4j
@ConditionalOnProperty(prefix = "spring.rsocket.server", value = "address")
@EasyUpsertStrategy(value = EasyUpsertType.ROCKETMQ)
public class RocketMQUpsert implements IEasyUpsert {
    @Override
    public <T> Object upsert(List<T> list) throws Exception {
        return null;
    }
}
