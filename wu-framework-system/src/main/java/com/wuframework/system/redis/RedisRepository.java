package com.wuframework.system.redis;


import com.wuframework.system.common.pro.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

import static com.alibaba.fastjson.util.IOUtils.close;

/**
 * Redis Repository
 *
 * @author
 */
@Slf4j
@Component
public class RedisRepository {

    /**
     * 默认编码
     */
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    @Autowired
    protected StringRedisTemplate redisTemplate;

    @Autowired
    private ApplicationProperties applicationProperties;

    public RedisRepository() {
    }

    public RedisRepository(final StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 列表序列化（用于Redis整存整取）
     *
     * @param value
     * @return
     */
    public static <T> byte[] serialize(final List<T> value) {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        byte[] rv = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            for (final T obj : value) {
                os.writeObject(obj);
            }
            os.writeObject(null);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        } catch (final IOException e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        } finally {
            close(os);
            close(bos);
        }
        return rv;
    }

    /**
     * 反序列化列表（用于Redis整存整取）
     *
     * @param in
     * @return
     */
    public static <T> List<T> unserializeForList(final byte[] in) {
        final List<T> list = new ArrayList<T>();
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                while (true) {
                    final T obj = (T) is.readObject();
                    if (obj == null) {
                        break;
                    } else {
                        list.add(obj);
                    }
                }
                is.close();
                bis.close();
            }
        } catch (final IOException e) {
            log.warn("Caught IOException decoding %d bytes of data",
                    in == null ? 0 : in.length, e);
        } catch (final ClassNotFoundException e) {
            log.warn("Caught CNFE decoding %d bytes of data",
                    in == null ? 0 : in.length, e);
        } finally {
            close(is);
            close(bis);
        }
        return list;
    }

    /**
     * 添加到带有过期时间的  缓存
     *
     * @param key   redis主键
     * @param value 值
     * @param time  过期时间
     */
    public void setExpire(final byte[] key, final byte[] value, final long time) {
        redisTemplate.execute((RedisCallback<Long>) connection -> {
            connection.set(key, value);
            connection.expire(key, time);
            log.info("[redisTemplate redis]放入 缓存  url:{} ========缓存时间为{}秒", key, time);
            return 1L;
        });
    }

    /**
     * 添加到带有 过期时间的  缓存
     *
     * @param key   redis主键
     * @param value 值
     * @param time  过期时间
     */
    public void setExpire(final String key, final String value, final long time) {
        final String keyName = this.applicationProperties.getName() + "_" + key;
        redisTemplate.execute((RedisCallback<Long>) connection -> {
            final RedisSerializer<String> serializer = getRedisSerializer();
            final byte[] keys = serializer.serialize(keyName);
            final byte[] values = serializer.serialize(value);
            connection.set(keys, values);
            connection.expire(keys, time);
            log.info("[redisTemplate redis]放入 缓存  url:{} ========缓存时间为{}秒", keyName, time);
            return 1L;
        });
    }

    /**
     * 一添加数组到过期时间的缓存
     *
     * @param keys   redis主键数组
     * @param values 值数组
     * @param time   过期时间
     */
    public void setExpire(final String[] keys, final String[] values, final long time) {
        redisTemplate.execute((RedisCallback<Long>) connection -> {
            final RedisSerializer<String> serializer = getRedisSerializer();
            for (int i = 0; i < keys.length; i++) {
                final byte[] bKeys = serializer.serialize(keys[i]);
                final byte[] bValues = serializer.serialize(values[i]);
                connection.set(bKeys, bValues);
                connection.expire(bKeys, time);
                log.info("[redisTemplate redis]放入 缓存  url:{} ========缓存时间为:{}秒", keys[i], time);
            }
            return 1L;
        });
    }

    /**
     * 一次性添加数组到 过期时间的缓存，不用多次连接,节省开销
     *
     * @param keys   the keys
     * @param values the values
     */
    public void set(final String[] keys, final String[] values) {
        redisTemplate.execute((RedisCallback<Long>) connection -> {
            final RedisSerializer<String> serializer = getRedisSerializer();
            for (int i = 0; i < keys.length; i++) {
                final byte[] bKeys = serializer.serialize(keys[i]);
                final byte[] bValues = serializer.serialize(values[i]);
                connection.set(bKeys, bValues);
                log.info("[redisTemplate redis]放入 缓存  url:{}", keys[i]);
            }
            return 1L;
        });
    }

    /**
     * 存放字符串数据到redis中无过期时间
     *
     * @param key   the key
     * @param value the value
     */
    public void set(final String key, final String value) {
        final String keyName = this.applicationProperties.getName() + "_" + key;
        redisTemplate.execute((RedisCallback<Long>) connection -> {
            final RedisSerializer<String> serializer = getRedisSerializer();
            final byte[] keys = serializer.serialize(keyName);
            final byte[] values = serializer.serialize(value);
            connection.set(keys, values);
            log.info("[redisTemplate redis]放入 缓存  url:{}", keyName);
            return 1L;
        });
    }

    /**
     * 查询在这个时间段内即将过期的key
     *
     * @param key  the key
     * @param time the time
     * @return the list
     */
    public List<String> willExpire(final String key, final long time) {
        final String keyName = this.applicationProperties.getName() + "_" + key;
        final List<String> keysList = new ArrayList<>();
        redisTemplate.execute((RedisCallback<List<String>>) connection -> {
            final Set<String> keys = redisTemplate.keys(keyName + "*");
            for (final String key1 : keys) {
                final Long ttl = connection.ttl(key1.getBytes(DEFAULT_CHARSET));
                if (0 <= ttl && ttl <= 2 * time) {
                    keysList.add(key1);
                }
            }
            return keysList;
        });
        return keysList;
    }

    /**
     * 查询在以keyPatten的所有  key
     *
     * @param keyPatten the key patten
     * @return the set
     */
    public Set<String> keys(final String keyPatten) {
        return redisTemplate.execute((RedisCallback<Set<String>>) connection -> redisTemplate.keys(keyPatten + "*"));
    }

    /**
     * 根据key获取对象
     *
     * @param key the key
     * @return the byte [ ]
     */
    public byte[] get(final byte[] key) {
        final byte[] result = redisTemplate.execute((RedisCallback<byte[]>) connection -> connection.get(key));
        log.info("[redisTemplate redis]取出 缓存  url:{} ", key);
        return result;
    }

    /**
     * 根据key获取对象
     *
     * @param key the key
     * @return the string
     */
    public String get(final String key) {
        final String keyName = this.applicationProperties.getName() + "_" + key;
        final String resultStr = redisTemplate.execute((RedisCallback<String>) connection -> {
            final RedisSerializer<String> serializer = getRedisSerializer();
            byte[] values = new byte[0];
            if (keyName != null) {
                final byte[] keys = serializer.serialize(keyName);
                values = connection.get(keys);
            }
            return serializer.deserialize(values);
        });
        log.info("[redisTemplate redis]取出 缓存  url:{} ", keyName);
        return resultStr;
    }

    /**
     * 根据key获取map对象
     *
     * @param keyPatten the key patten
     * @return the keys values
     */
    public Map<String, String> getKeysValues(final String keyPatten) {
        log.info("[redisTemplate redis]  getValues()  patten={} ", keyPatten);
        return redisTemplate.execute((RedisCallback<Map<String, String>>) connection -> {
            final RedisSerializer<String> serializer = getRedisSerializer();
            final Map<String, String> maps = new HashMap<>();
            final Set<String> keys = redisTemplate.keys(keyPatten + "*");
            for (final String key : keys) {
                final byte[] bKeys = serializer.serialize(key);
                final byte[] bValues = connection.get(bKeys);
                final String value = serializer.deserialize(bValues);
                maps.put(key, value);
            }
            return maps;
        });
    }

    /**
     * @return the hash operations
     */
    private HashOperations<String, String, String> opsForHash() {
        return redisTemplate.opsForHash();
    }

    /**
     * 对HashMap操作
     *
     * @param key       the key
     * @param hashKey   the hash key
     * @param hashValue the hash value
     */
    public void putHashValue(final String key, final String hashKey, final String hashValue) {
        log.info("[redisTemplate redis]  putHashValue()  key={},hashKey={},hashValue={} ", key, hashKey, hashValue);
        opsForHash().put(key, hashKey, hashValue);
    }

    /**
     * 获取单个field对应的值
     *
     * @param key     the key
     * @param hashKey the hash key
     * @return the hash values
     */
    public Object getHashValues(final String key, final String hashKey) {
        log.info("[redisTemplate redis]  getHashValues()  key={},hashKey={}", key, hashKey);
        return opsForHash().get(key, hashKey);
    }

    /**
     * 根据key值删除
     *
     * @param key      the key
     * @param hashKeys the hash keys
     */
    public void delHashValues(final String key, final Object... hashKeys) {
        log.info("[redisTemplate redis]  delHashValues()  key={}", key);
        opsForHash().delete(key, hashKeys);
    }

    /**
     * key只匹配map
     *
     * @param key the key
     * @return the hash value
     */
    public Map<String, String> getHashValue(final String key) {
        log.info("[redisTemplate redis]  getHashValue()  key={}", key);
        return opsForHash().entries(key);
    }

    /**
     * 批量添加
     *
     * @param key the key
     * @param map the map
     */
    public void putHashValues(final String key, final Map<String, String> map) {
        opsForHash().putAll(key, map);
    }

    /**
     * 集合数量
     *
     * @return the long
     */
    public long dbSize() {
        return redisTemplate.execute(RedisServerCommands::dbSize);
    }

    /**
     * 清空redis存储的数据
     *
     * @return the string
     */
    public String flushDB() {
        return redisTemplate.execute((RedisCallback<String>) connection -> {
            connection.flushDb();
            return "ok";
        });
    }

    /**
     * 判断某个主键是否存在
     *
     * @param key the key
     * @return the boolean
     */
    public boolean exists(final String key) {
        final String keyName = this.applicationProperties.getName() + "_" + key;
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.exists(keyName.getBytes(DEFAULT_CHARSET)));
    }

    /**
     * 删除key
     *
     * @param keys the keys
     * @return the long
     */
    public long del(final String... keys) {
        String[] keyArr = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            keyArr[i] = this.applicationProperties.getName() + "_" + keys[i];
        }
        return redisTemplate.execute((RedisCallback<Long>) connection -> {
            long result = 0;
            for (final String key : keyArr) {
                result = connection.del(key.getBytes(DEFAULT_CHARSET));
                log.info("删除key：{},{}", key, result > 0 ? "成功" : "key不存在");
            }
            return result;
        });
    }

    /**
     * 获取 RedisSerializer
     *
     * @return the redis serializer
     */
    protected RedisSerializer<String> getRedisSerializer() {
        return redisTemplate.getStringSerializer();
    }

    /**
     * 对某个主键对应的值加一,value值必须是全数字的字符串
     *
     * @param key the key
     * @return the long
     */
    public long incr(final String key) {
        return redisTemplate.execute((RedisCallback<Long>) connection -> {
            final RedisSerializer<String> redisSerializer = getRedisSerializer();
            return connection.incr(redisSerializer.serialize(key));
        });
    }

    /**
     * redis List 引擎
     *
     * @return the list operations
     */
    private ListOperations<String, String> opsForList() {
        return redisTemplate.opsForList();
    }

    /**
     * redis List数据结构 : 将一个或多个值 value 插入到列表 key 的表头
     *
     * @param key   the key
     * @param value the value
     * @return the long
     */
    public Long leftPush(final String key, final String value) {
        return opsForList().leftPush(key, value);
    }

    /**
     * redis List数据结构 : 移除并返回列表 key 的头元素
     *
     * @param key the key
     * @return the string
     */
    public String leftPop(final String key) {
        return opsForList().leftPop(key);
    }

    /**
     * redis List数据结构 :将一个或多个值 value 插入到列表 key 的表尾(最右边)。
     *
     * @param key   the key
     * @param value the value
     * @return the long
     */
    public Long in(final String key, final String value) {
        return opsForList().rightPush(key, value);
    }

    /**
     * redis List数据结构 : 移除并返回列表 key 的末尾元素
     *
     * @param key the key
     * @return the string
     */
    public String rightPop(final String key) {
        return opsForList().rightPop(key);
    }

    /**
     * redis List数据结构 : 返回列表 key 的长度 ; 如果 key 不存在，则 key 被解释为一个空列表，返回 0 ; 如果 key 不是列表类型，返回一个错误。
     *
     * @param key the key
     * @return the long
     */
    public Long length(final String key) {
        return opsForList().size(key);
    }

    /**
     * redis List数据结构 : 根据参数 i 的值，移除列表中与参数 value 相等的元素
     *
     * @param key   the key
     * @param i     the
     * @param value the value
     */
    public void remove(final String key, final long i, final String value) {
        opsForList().remove(key, i, value);
    }

    /**
     * redis List数据结构 : 将列表 key 下标为 index 的元素的值设置为 value
     *
     * @param key   the key
     * @param index the index
     * @param value the value
     */
    public void set(final String key, final long index, final String value) {
        opsForList().set(key, index, value);
    }

    /**
     * redis List数据结构 : 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 end 指定。
     *
     * @param key   the key
     * @param start the start
     * @param end   the end
     * @return the list
     */
    public List<String> getList(final String key, final int start, final int end) {
        return opsForList().range(key, start, end);
    }

    /**
     * redis List数据结构 : 批量存储
     *
     * @param key  the key
     * @param list the list
     * @return the long
     */
    public Long leftPushAll(final String key, final List<String> list) {
        return opsForList().leftPushAll(key, list);
    }

    /**
     * redis List数据结构 : 将值 value 插入到列表 key 当中，位于值 index 之前或之后,默认之后。
     *
     * @param key   the key
     * @param index the index
     * @param value the value
     */
    public void insert(final String key, final long index, final String value) {
        opsForList().set(key, index, value);
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return Object
     */
    public Object lGetIndex(final String key, final long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

