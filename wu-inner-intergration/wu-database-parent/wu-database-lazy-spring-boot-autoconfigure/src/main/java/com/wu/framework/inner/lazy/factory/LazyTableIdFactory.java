package com.wu.framework.inner.lazy.factory;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 创建主键的工厂
 */
public class LazyTableIdFactory {
    protected final static ConcurrentHashMap<Class<?>, CreateId> CONCURRENT_CREATE_ID_HASH_MAP = new ConcurrentHashMap<>() {
        {
            put(String.class, new StringIdCreator());
            put(Long.class, new LongIdCreator());
        }
    };

    public static Object createId(Class<?> idClassType) {
        if (CONCURRENT_CREATE_ID_HASH_MAP.containsKey(idClassType)) {
            return CONCURRENT_CREATE_ID_HASH_MAP.get(idClassType).createId();
        }
        System.err.println("idClassType = " + idClassType);
        return null;
    }


    public interface CreateId {
        Object createId();
    }


}

/**
 * 字符串id
 */
class StringIdCreator implements LazyTableIdFactory.CreateId {

    /**
     * @return
     */
    @Override
    public Object createId() {
        return UUID.randomUUID().toString();
    }
}

/**
 * Long 类型生成ID
 */
class LongIdCreator implements LazyTableIdFactory.CreateId {

    /**
     * @return
     */
    @Override
    public Object createId() {
        return System.currentTimeMillis();
    }
}

