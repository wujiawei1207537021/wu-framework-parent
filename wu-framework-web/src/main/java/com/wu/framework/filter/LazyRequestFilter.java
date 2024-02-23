package com.wu.framework.filter;

import com.wu.framework.inner.layer.toolkit.DynamicLazyAttributeContextHolder;
import com.wu.framework.inner.layer.web.EasyRequestBodyParam;
import com.wu.framework.toolkit.DynamicLazyHttpBodyContextHolder;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * describe :
 *
 * @author : Jia wei Wu
 * @version 1.0
 * @date : 2022/10/5 20:37
 */
public class LazyRequestFilter implements Filter {


    /**
     * The <code>doFilter</code> method of the Filter is called by the container
     * each time a request/response pair is passed through the chain due to a
     * client request for a resource at the end of the chain. The FilterChain
     * passed in to this method allows the Filter to pass on the request put
     * response to the next entity in the chain.
     * <p>
     * A typical implementation of this method would follow the following
     * pattern:- <br>
     * 1. Examine the request<br>
     * 2. Optionally wrap the request object with a custom implementation to
     * filter content or headers for inand filtering <br>
     * 3. Optionally wrap the response object with a custom implementation to
     * filter content or headers for outand filtering <br>
     * 4. a) <strong>Either</strong> invoke the next entity in the chain using
     * the FilterChain object (<code>chain.doFilter()</code>), <br>
     * 4. b) <strong>or</strong> not pass on the request/response pair to the
     * next entity in the filter chain to block the request processing<br>
     * 5. Directly set headers on the response after invocation of the next
     * entity in the filter chain.
     *
     * @param request  The request to process
     * @param response The response associated with the request
     * @param chain    Provides access to the next filter in the chain for this
     *                 filter to pass the request and response to for further
     *                 processing
     * @throws IOException      if an I/O error occurs during this filter's
     *                          processing of the request
     * @throws ServletException if the processing fails for any other reason
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {

            if (StringUtils.startsWithIgnoreCase(request.getContentType(), "multipart/")) {
                chain.doFilter(request, response);
                return;
            }

            if (request instanceof HttpServletRequest) {
                RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) request);
                byte[] requestBody = requestWrapper.getRequestBody();
                DynamicLazyHttpBodyContextHolder.push(requestBody);
                chain.doFilter(requestWrapper, response);// 文件上传有问题
            } else {
                chain.doFilter(request, response);
            }
        } catch (Throwable  e) {
            e.printStackTrace();
        } finally {
            DynamicLazyHttpBodyContextHolder.clear();
        }
    }

    public static class RequestWrapper extends HttpServletRequestWrapper {
        //参数字节数组
        private byte[] requestBody;
        //Http请求对象
        private final HttpServletRequest request;

        public RequestWrapper(HttpServletRequest request) throws IOException {
            super(request);
            this.request = request;
        }


        /**
         * @return 返回流
         * @throws IOException 异常信息
         */
        @Override
        public ServletInputStream getInputStream() throws IOException {
            /*
             * 每次调用此方法时将数据流中的数据读取出来，然后再回填到InputStream之中
             * 解决通过@RequestBody和@RequestParam（POST方式）读取一次后控制器拿不到参数问题
             */
            if (null == this.requestBody) {
                this.requestBody = StreamUtils.copyToByteArray(request.getInputStream());
            }

            final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
            return new ServletInputStream() {

                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener listener) {

                }

                @Override
                public int read() {
                    return bais.read();
                }
            };
        }

        public byte[] getRequestBody() throws IOException {
            if (null == this.requestBody) {
                this.requestBody = StreamUtils.copyToByteArray(request.getInputStream());
            }
            return requestBody;
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        }
    }
    // TODO Unexpected EOF read on the socket
//    org.apache.catalina.connector.ClientAbortException: java.io.EOFException: Unexpected EOF read on the socket
//	at org.apache.catalina.connector.InputBuffer.realReadBytes(InputBuffer.java:340)
//	at org.apache.catalina.connector.InputBuffer.checkByteBufferEof(InputBuffer.java:632)
//	at org.apache.catalina.connector.InputBuffer.read(InputBuffer.java:362)
//	at org.apache.catalina.connector.CoyoteInputStream.read(CoyoteInputStream.java:132)
//	at org.apache.catalina.connector.CoyoteInputStream.read(CoyoteInputStream.java:110)
//	at org.springframework.util.StreamUtils.copy(StreamUtils.java:165)
//	at org.springframework.util.StreamUtils.copyToByteArray(StreamUtils.java:70)
//	at com.wu.framework.filter.LazyRequestFilter$RequestWrapper.getRequestBody(LazyRequestFilter.java:135)
//	at com.wu.framework.filter.LazyRequestFilter.doFilter(LazyRequestFilter.java:67)
//	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
//	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
//	at com.laihui.middleground.dynamic.saas.web.filter.TenantDynamicSourceHandlerFilter.doFilterInternal(TenantDynamicSourceHandlerFilter.java:79)
//	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)
//	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
//	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
//	at com.laihui.base.web.http.ResponseHeaderFilter.doFilterInternal(ResponseHeaderFilter.java:38)
//	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)
//	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
//	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
//	at org.springframework.boot.actuate.web.trace.servlet.HttpTraceFilter.doFilterInternal(HttpTraceFilter.java:88)
//	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)
//	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
//	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
//	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)
//	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)
//	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
//	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
//	at laihui.seata.filter.TenantFilter.doFilterInternal(TenantFilter.java:36)
//	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)
//	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
//	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
//	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)
//	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)
//	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
//	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
//	at org.springframework.boot.actuate.metrics.web.servlet.WebMvcMetricsFilter.doFilterInternal(WebMvcMetricsFilter.java:93)
//	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)
//	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
//	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
//	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)
//	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)
//	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
//	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
//	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:202)
//	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)
//	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:541)
//	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:139)
//	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)
//	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)
//	at org.apache.catalina.valves.RemoteIpValve.invoke(RemoteIpValve.java:747)
//	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343)
//	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:373)
//	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65)
//	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:868)
//	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1589)
//	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)
//	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
//	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
//	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)
//	at java.lang.Thread.run(Thread.java:748)
//Caused by: java.io.EOFException: Unexpected EOF read on the socket
//	at org.apache.coyote.http11.Http11InputBuffer.fill(Http11InputBuffer.java:788)
//	at org.apache.coyote.http11.Http11InputBuffer.access$300(Http11InputBuffer.java:42)
//	at org.apache.coyote.http11.Http11InputBuffer$SocketInputBuffer.doRead(Http11InputBuffer.java:1133)
//	at org.apache.coyote.http11.filters.IdentityInputFilter.doRead(IdentityInputFilter.java:102)
//	at org.apache.coyote.http11.Http11InputBuffer.doRead(Http11InputBuffer.java:249)
//	at org.apache.coyote.Request.doRead(Request.java:551)
//	at org.apache.catalina.connector.InputBuffer.realReadBytes(InputBuffer.java:336)
//	... 59 more
}
