package com.baojie.jeesite.util.http;

import com.baojie.jeesite.util.constants.GlobalConfig;
import com.baojie.jeesite.util.redis.RedisUtil;
import com.baojie.jeesite.util.spring.SpringContextHolder;
import com.baojie.jeesite.util.util.StringUtils;
import com.baojie.jeesite.util.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：冀保杰
 * @date：2018-08-13
 * @desc：
 */
public class CookieUtils {

    private static RedisUtil redisUtil = SpringContextHolder.getBean(RedisUtil.class);

    /**
     * 设置Cookie值，用传入的Cookie Value
     *
     * @param request
     * @param response
     * @param cookieValue
     * @throws IOException
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieValue) throws IOException {
        Cookie cookie = getCookie(request, GlobalConfig.COOKIE_NAME);
        if (cookie == null) {
            cookie = new Cookie(GlobalConfig.COOKIE_NAME, cookieValue);
        }
        cookie.setValue(cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    /**
     * 设置Cookie值，随机生成一个Cookie
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public static String setCookie(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cookieValue = UUID.randomUUID();
        setCookie(request, response, cookieValue);
        return cookieValue;
    }

    /**
     * 获取Cookie，如果为空则设置Cookie，并返回Cookie值
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public static String getCookieValueIfNullThenSetCookie(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cookieValue = getCookieValue(request);
        if (StringUtils.isEmpty(cookieValue)) {
            cookieValue = setCookie(request, response);
        }
        return cookieValue;
    }

    /**
     * 获取Cookie值
     * @param request
     * @return
     */
    public static String getCookieValue(HttpServletRequest request) {
        Cookie cookie = getCookie(request, GlobalConfig.COOKIE_NAME);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    /**
     * 移除Cookie值
     * @param request
     * @param response
     */
    public static void removeCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = getCookie(request, GlobalConfig.COOKIE_NAME);
        if (cookie != null){
            //如果redis中保存有session，删除
            redisUtil.remove(GlobalConfig.DEFAULT_SESSION_KEY_PREFIX + cookie.getValue());
            cookie.setValue(null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);

        }
    }

    /**
     * 获取Cookie对象
     * @param request
     * @param cookieName
     * @return
     */
    private static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }

        Cookie ticket = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                ticket = cookie;
                break;
            }
        }
        return ticket;
    }

}
