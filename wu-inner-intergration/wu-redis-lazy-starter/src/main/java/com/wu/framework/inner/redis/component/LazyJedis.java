package com.wu.framework.inner.redis.component;


import redis.clients.jedis.*;
import redis.clients.jedis.util.JedisURIHelper;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;
import java.net.URI;

/**
 * describe : Jedis 二次封装
 *
 * @author : 吴佳伟
 * @version 1.0
 * @date : 2021/12/14 9:40 下午
 */
public class LazyJedis extends Jedis {

    public LazyJedis() {
    }

    /**
     * @param uri
     * @deprecated This constructor will not support a host string in future. It will accept only a
     * uri string. {@link JedisURIHelper#isValid(URI)} can used before this. If this
     * constructor was being used with a host, it can be replaced with
     * {@link #Jedis(String, int)} with the host and {@link Protocol#DEFAULT_PORT}.
     */
    public LazyJedis(String uri) {
        super(uri);
    }

    public LazyJedis(HostAndPort hp) {
        super(hp);
    }

    public LazyJedis(HostAndPort hp, JedisClientConfig config) {
        super(hp, config);
    }

    public LazyJedis(String host, int port) {
        super(host, port);
    }

    public LazyJedis(String host, int port, boolean ssl) {
        super(host, port, ssl);
    }

    public LazyJedis(String host, int port, boolean ssl, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(host, port, ssl, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public LazyJedis(String host, int port, int timeout) {
        super(host, port, timeout);
    }

    public LazyJedis(String host, int port, int timeout, boolean ssl) {
        super(host, port, timeout, ssl);
    }

    public LazyJedis(String host, int port, int timeout, boolean ssl, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(host, port, timeout, ssl, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public LazyJedis(String host, int port, int connectionTimeout, int soTimeout) {
        super(host, port, connectionTimeout, soTimeout);
    }

    public LazyJedis(String host, int port, int connectionTimeout, int soTimeout, int infiniteSoTimeout) {
        super(host, port, connectionTimeout, soTimeout, infiniteSoTimeout);
    }

    public LazyJedis(String host, int port, int connectionTimeout, int soTimeout, boolean ssl) {
        super(host, port, connectionTimeout, soTimeout, ssl);
    }

    public LazyJedis(String host, int port, int connectionTimeout, int soTimeout, boolean ssl, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(host, port, connectionTimeout, soTimeout, ssl, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public LazyJedis(String host, int port, int connectionTimeout, int soTimeout, int infiniteSoTimeout, boolean ssl, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(host, port, connectionTimeout, soTimeout, infiniteSoTimeout, ssl, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public LazyJedis(JedisShardInfo shardInfo) {
        super(shardInfo);
    }

    public LazyJedis(URI uri) {
        super(uri);
    }

    public LazyJedis(URI uri, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(uri, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public LazyJedis(URI uri, int timeout) {
        super(uri, timeout);
    }

    public LazyJedis(URI uri, int timeout, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(uri, timeout, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public LazyJedis(URI uri, int connectionTimeout, int soTimeout) {
        super(uri, connectionTimeout, soTimeout);
    }

    public LazyJedis(URI uri, int connectionTimeout, int soTimeout, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(uri, connectionTimeout, soTimeout, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public LazyJedis(URI uri, int connectionTimeout, int soTimeout, int infiniteSoTimeout, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(uri, connectionTimeout, soTimeout, infiniteSoTimeout, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public LazyJedis(URI uri, JedisClientConfig config) {
        super(uri, config);
    }

    /**
     * @param jedisSocketFactory
     * @deprecated This constructor will be removed in future major release.
     * <p>
     * Use {@link Jedis#Jedis(JedisSocketFactory, JedisClientConfig)}.
     */
    public LazyJedis(JedisSocketFactory jedisSocketFactory) {
        super(jedisSocketFactory);
    }

    public LazyJedis(JedisSocketFactory jedisSocketFactory, JedisClientConfig clientConfig) {
        super(jedisSocketFactory, clientConfig);
    }
}
