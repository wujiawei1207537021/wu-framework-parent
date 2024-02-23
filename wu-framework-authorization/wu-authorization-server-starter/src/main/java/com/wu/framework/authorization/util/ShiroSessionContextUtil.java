package com.wu.framework.authorization.util;

import com.wu.framework.authorization.model.UserDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;


/**
 * @author : Jiawei Wu
 * @version 1.0
 * describe :
 * @date : 2021/1/8 8:55 下午
 */
public class ShiroSessionContextUtil {
    public static final String SESSION_USER_ID = "SESSION_USER_ID";

    public static void setSessionAttribute(UserDetails userDetails) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        final HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        final HttpSession session = request.getSession();
        session.setAttribute(SESSION_USER_ID, userDetails.getId());
    }
}
