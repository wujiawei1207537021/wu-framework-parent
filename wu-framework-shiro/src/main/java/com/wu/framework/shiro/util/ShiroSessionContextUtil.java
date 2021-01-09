package com.wu.framework.shiro.util;

import com.wu.framework.shiro.model.UserDetails;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author : 吴佳伟
 * @version 1.0
 * @describe :
 * @date : 2021/1/8 8:55 下午
 */
public class ShiroSessionContextUtil {
    public static final String SESSION_USER_ID = "SESSION_USER_ID";

    public static void setSessionAttribute(UserDetails userDetails) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        final HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        final HttpSession session = request.getSession();
        session.setAttribute(SESSION_USER_ID, userDetails.getUserId());
    }
}
