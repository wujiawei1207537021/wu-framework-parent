package com.wu.smart.acw.client.nocode.adapter;

import com.wu.smart.acw.client.nocode.filter.PathFilter;
import jakarta.servlet.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * description 适配器
 *
 * @author 吴佳伟
 * @date 2023/08/11 15:04
 */
public class DynamicPathAdapter {
    private final List<PathFilter> pathFilterList;


    public DynamicPathAdapter(List<PathFilter> pathFilterList) {
        this.pathFilterList = pathFilterList;
    }


    /**
     * 判断当前请求是否是动态请求
     *
     * @param servletRequest
     * @return
     */
    public boolean support(ServletRequest servletRequest) {

        return false;
    }

    /**
     * 处理动态请求
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write("nihao".getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();

    }


}
