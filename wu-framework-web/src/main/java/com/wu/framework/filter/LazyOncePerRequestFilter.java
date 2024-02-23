//package com.wu.framework.filter;
//
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * describe :
// *
// * @author : Jia wei Wu
// * @version 1.0
// * @date : 2022/10/6 15:48
// */
//public class LazyOncePerRequestFilter extends OncePerRequestFilter {
//    /**
//     * Same contract as for {@code doFilter}, but guaranteed to be
//     * just invoked once per request within a single request thread.
//     * See {@link #shouldNotFilterAsyncDispatch()} for details.
//     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
//     * default ServletRequest and ServletResponse ones.
//     *
//     * @param request
//     * @param response
//     * @param filterChain
//     */
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        filterChain.doFilter(request,response);
//    }
//}
