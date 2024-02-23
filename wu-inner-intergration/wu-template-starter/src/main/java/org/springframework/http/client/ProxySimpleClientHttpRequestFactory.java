package org.springframework.http.client;

import com.wu.framework.inner.template.proxy.http.config.ProxyHttpConfig;
import lombok.Data;
import lombok.Setter;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.net.*;
import java.util.logging.Logger;

/**
 * {@link ClientHttpRequestFactory} implementation that uses standard JDK facilities.
 *
 * @author Arjen Poutsma
 * @author Juergen Hoeller
 * @see java.net.HttpURLConnection
 * @see HttpComponentsClientHttpRequestFactory
 * @since 3.0
 */
@SuppressWarnings("deprecation")
public class ProxySimpleClientHttpRequestFactory implements ClientHttpRequestFactory {

    private static final int DEFAULT_CHUNK_SIZE = 4096;
    private final ProxyHttpConfig proxyHttpConfig;
    Logger log = Logger.getAnonymousLogger();
    @Nullable
    private Proxy proxy;
    /**
     * -- SETTER --
     * Indicate whether this request factory should buffer the
     * <p>
     * internally.
     * <p>Default is
     * . When sending large amounts of data via POST or PUT,
     * it is recommended to change this property to
     * , so as not to run
     * out of memory. This will result in a
     * that either
     * streams directly to the underlying
     * (if the
     * <p>
     * is known in advance), or that will use "Chunked transfer encoding"
     * (if the
     * is not known in advance).
     */
    @Setter
    private boolean bufferRequestBody = true;
    /**
     * -- SETTER --
     * Set the number of bytes to write in each chunk when not buffering request
     * bodies locally.
     * <p>Note that this parameter is only used when
     * <p>
     * is set to
     * ,
     * and the
     * is not known in advance.
     */
    @Setter
    private int chunkSize = DEFAULT_CHUNK_SIZE;
    private int connectTimeout = -1;
    private int readTimeout = -1;
    private boolean outputStreaming = true;
    @Nullable
    private AsyncListenableTaskExecutor taskExecutor;

    public ProxySimpleClientHttpRequestFactory(ProxyHttpConfig proxyHttpConfig) {
        this.proxyHttpConfig = proxyHttpConfig;
    }


    /**
     * Set the {@link Proxy} to use for this request factory.
     */
    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    /**
     * Set the underlying URLConnection's connect timeout (in milliseconds).
     * A timeout value of 0 specifies an infinite timeout.
     * <p>Default is the system's default timeout.
     *
     * @see URLConnection#setConnectTimeout(int)
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * Set the underlying URLConnection's read timeout (in milliseconds).
     * A timeout value of 0 specifies an infinite timeout.
     * <p>Default is the system's default timeout.
     *
     * @see URLConnection#setReadTimeout(int)
     */
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    /**
     * Set if the underlying URLConnection can be set to 'outand streaming' mode.
     * Default is {@code true}.
     * <p>When outand streaming is enabled, authentication and redirection cannot be handled automatically.
     * If outand streaming is disabled, the {@link HttpURLConnection#setFixedLengthStreamingMode} and
     * {@link HttpURLConnection#setChunkedStreamingMode} methods of the underlying connection will never
     * be called.
     *
     * @param outputStreaming if outand streaming is enabled
     */
    public void setOutputStreaming(boolean outputStreaming) {
        this.outputStreaming = outputStreaming;
    }

    /**
     * Set the task executor for this request factory. Setting this property is required
     *
     * @param taskExecutor the task executor
     */
    public void setTaskExecutor(AsyncListenableTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }


    @Override
    public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
        HttpURLConnection connection = openConnection(uri.toURL(), this.proxy);
        prepareConnection(connection, httpMethod.name());

        if (this.bufferRequestBody) {
            return new SimpleBufferingClientHttpRequest(connection, this.outputStreaming);
        } else {
            return new SimpleStreamingClientHttpRequest(connection, this.chunkSize, this.outputStreaming);
        }
    }


    /**
     * Opens and returns a connection to the given URL.
     * <p>The default implementation uses the given {@linkplain #setProxy(Proxy) proxy} -
     * if any - to open a connection.
     *
     * @param url   the URL to open a connection to
     * @param proxy the proxy to use, may be {@code null}
     * @return the opened connection
     * @throws IOException in case of I/O errors
     */
    protected HttpURLConnection openConnection(URL url, @Nullable Proxy proxy) throws IOException {
        URLConnection urlConnection = (proxy != null ? url.openConnection(proxy) : url.openConnection());
        String ip = proxyHttpConfig.getProxy();
        log.info("当前请求代理IP为:" + ip);
        urlConnection.setRequestProperty("X-Forwarded-For", ip);

        urlConnection.setRequestProperty("HTTP_X_FORWARDED_FOR", ip);

        urlConnection.setRequestProperty("HTTP_CLIENT_IP", ip);

        urlConnection.setRequestProperty("REMOTE_ADDR", ip);

        urlConnection.setRequestProperty("Host", "");

        urlConnection.setRequestProperty("Connection", "keep-alive");

        urlConnection.setRequestProperty("Content-Length", "17");

        urlConnection.setRequestProperty("Accept", "application/json");

        urlConnection.setRequestProperty("Origin", "ORIGIN");

        urlConnection.setRequestProperty("User-Agent",

                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");

        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        urlConnection.setRequestProperty("Referer", "REFERER");

//        urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");

        urlConnection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4,pt;q=0.2");

//        urlConnection.setDoOutput(true);
//
//        urlConnection.setDoInput(true);

        urlConnection.setRequestProperty(//设置终端类型
                "User-Agent",
                "Mozilla/4.0 (compatible; MSIE 7.0; NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)"
        );

        if (!(urlConnection instanceof HttpURLConnection)) {
            throw new IllegalStateException(
                    "HttpURLConnection required for [" + url + "] but got: " + urlConnection);
        }
        return (HttpURLConnection) urlConnection;
    }

    /**
     * Template method for preparing the given {@link HttpURLConnection}.
     * <p>The default implementation prepares the connection for inand and output, and sets the HTTP method.
     *
     * @param connection the connection to prepare
     * @param httpMethod the HTTP request method ({@code GET}, {@code POST}, etc.)
     * @throws IOException in case of I/O errors
     */
    protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
        if (this.connectTimeout >= 0) {
            connection.setConnectTimeout(this.connectTimeout);
        }
        if (this.readTimeout >= 0) {
            connection.setReadTimeout(this.readTimeout);
        }

        boolean mayWrite =
                ("POST".equals(httpMethod) || "PUT".equals(httpMethod) ||
                        "PATCH".equals(httpMethod) || "DELETE".equals(httpMethod));

        connection.setDoInput(true);
        connection.setInstanceFollowRedirects("GET".equals(httpMethod));
        connection.setDoOutput(mayWrite);
        connection.setRequestMethod(httpMethod);
    }

    @Data
    public static class ProxyInfo {
        private String proxy;
        private int port;
        private String socksProxy;
        private int socksPort;

        public ProxyInfo(String proxy, int port) {
            this.proxy = proxy;
            this.port = port;
        }

    }

}
