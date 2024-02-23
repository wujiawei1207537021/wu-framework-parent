package com.wu.framework.inner.layer.data.limit;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Role;

import java.util.*;

/**
 * description
 *
 * @author Jia wei Wu
 * @date 2022/10/09 6:14 下午
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class DefaultIAccessLimit implements IAccessLimit {

    private ExpiryMap<String, Integer> accessLimitMap = new ExpiryMap<String, Integer>();

    /**
     * 限流控制
     *
     * @param accessLimit
     * @param key
     */
    @Override
    public void andThen(EasyAccessLimit accessLimit, String key) {
        int seconds = accessLimit.seconds();
        int maxCount = accessLimit.maxCount();
        String msg = accessLimit.msg();
        // 没有访问记录
        if (!accessLimitMap.containsKey(key) || accessLimitMap.isInvalid(key)) {
            accessLimitMap.put(key, 0, 1000L * seconds);
        } else {
            accessLimitMap.put(key, accessLimitMap.get(key) + 1, accessLimitMap.getExpiryTime(key) - System.currentTimeMillis());
        }
        // 有访问记录 判断次数
        if ((accessLimitMap.get(key) + 1) > maxCount) {
            //访问频繁
            throw new RuntimeException(msg);
        }
    }

    /**
     * @param <K>
     * @param <V> description: 带有效期map 简单实现 实现了基本的方法
     */
    public class ExpiryMap<K, V> extends HashMap<K, V> {

        private static final long serialVersionUID = 1L;

        /**
         * default expiry time 2m
         */
        private long EXPIRY = 1000 * 60 * 2;

        private HashMap<K, Long> expiryMap = new HashMap<>();

        public ExpiryMap() {
            super();
        }

        public ExpiryMap(long defaultExpiryTime) {
            this(1 << 4, defaultExpiryTime);
        }

        public ExpiryMap(int initialCapacity, long defaultExpiryTime) {
            super(initialCapacity);
            this.EXPIRY = defaultExpiryTime;
        }

        @Override
        public V put(K key, V value) {
            expiryMap.put(key, System.currentTimeMillis() + EXPIRY);
            return super.put(key, value);
        }

        @Override
        public boolean containsKey(Object key) {
            return !checkExpiry(key, true) && super.containsKey(key);
        }

        /**
         * @param key
         * @param value
         * @param expiryTime 键值对有效期 毫秒
         * @return
         */
        public V put(K key, V value, long expiryTime) {
            expiryMap.put(key, System.currentTimeMillis() + expiryTime);
            return super.put(key, value);
        }

        @Override
        public int size() {
            return entrySet().size();
        }

        @Override
        public boolean isEmpty() {
            return entrySet().size() == 0;
        }

        @Override
        public boolean containsValue(Object value) {
            if (value == null) {
                return Boolean.FALSE;
            }
            Set<Entry<K, V>> set = super.entrySet();
            Iterator<Entry<K, V>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry<K, V> entry = iterator.next();
                if (value.equals(entry.getValue())) {
                    if (checkExpiry(entry.getKey(), false)) {
                        iterator.remove();
                        return Boolean.FALSE;
                    } else {
                        return Boolean.TRUE;
                    }
                }
            }
            return Boolean.FALSE;
        }

        @Override
        public Collection<V> values() {

            Collection<V> values = super.values();

            if (values == null || values.size() < 1) {
                return values;
            }

            Iterator<V> iterator = values.iterator();

            while (iterator.hasNext()) {
                V next = iterator.next();
                if (!containsValue(next)) {
                    iterator.remove();
                }
            }
            return values;
        }

        @Override
        public V get(Object key) {
            if (key == null) {
                return null;
            }
            if (checkExpiry(key, true)) {
                return null;
            }
            return super.get(key);
        }

        /**
         * @param key
         * @return 过期 true  存在且没过期 false
         * description: 是否过期
         */
        public Boolean isInvalid(Object key) {
            if (key == null) {
                return true;
            }
            if (!expiryMap.containsKey(key)) {
                return true;
            }
            long expiryTime = expiryMap.get(key);

            boolean flag = System.currentTimeMillis() > expiryTime;

            if (flag) {
                super.remove(key);
                expiryMap.remove(key);
                return true;
            }
            return false;
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> m) {
            for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
                expiryMap.put(e.getKey(), System.currentTimeMillis() + EXPIRY);
            }
            super.putAll(m);
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> set = super.entrySet();
            Iterator<Map.Entry<K, V>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry<K, V> entry = iterator.next();
                if (checkExpiry(entry.getKey(), false)) {
                    iterator.remove();
                }
            }

            return set;
        }

        /**
         * @param key
         * @param isRemoveSuper true super删除
         * @return description: 是否过期
         */
        private boolean checkExpiry(Object key, boolean isRemoveSuper) {

            if (!expiryMap.containsKey(key)) {
                return Boolean.FALSE;
            }
            long expiryTime = expiryMap.get(key);

            boolean flag = System.currentTimeMillis() > expiryTime;

            if (flag) {
                if (isRemoveSuper) {
                    super.remove(key);
                }
                expiryMap.remove(key);
            }
            return flag;
        }

        /**
         * 获取过期时间
         *
         * @param key
         * @return
         */
        public long getExpiryTime(String key) {
            return expiryMap.get(key);
        }
    }
}
