package cn.gin.webmvc.support.util;

import cn.gin.webmvc.controller.model.Cookies;
import cn.gin.webmvc.support.Constants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class CookieUtils {

    /**
     * The seconds of one week, the default cookie expired time
     */
    public static final long ONE_WEEK = 604800000L;

    public static Cookie getCookie(Cookie[] cookies, String name) {

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }

        return null;
    }

    public static Cookie getCookie(Cookies cookies, String name) {

        if (cookies == null) {
            return null;
        }

        for (Map.Entry<String, Cookie> entry : cookies.entrySet()) {
            if (entry.getKey().equals(name)) {
                return entry.getValue();
            }
        }

        return null;
    }

    public static void addCookie(Cookies cookies, String cookieName, String cookieValue) {

        cookies.put(cookieName, new Cookie(cookieName, cookieValue));
    }

    public static void addCookie(HttpServletResponse resp, String name, String value) {

        if (!StringUtils.isEmpty(name)) {
            Cookie cookie = new Cookie(name, value);
            addCookie(resp, cookie, Constants.COOKIE_PATH, (int) ONE_WEEK);
        }
    }

    public static void addCookie(Cookies cookies, String name, String value, int age) {

        if (!StringUtils.isEmpty(name)) {
            Cookie cookie = new Cookie(name, value);
            cookie.setPath(Constants.COOKIE_PATH);
            cookie.setMaxAge(age);
            cookies.put(name, cookie);
        }
    }

    public static void addCookie(HttpServletResponse resp, String name, String value, int age) {

        if (!StringUtils.isEmpty(name)) {
            Cookie cookie = new Cookie(name, value);
            addCookie(resp, cookie, Constants.COOKIE_PATH, age);
        }
    }

    public static void addCookie(HttpServletResponse resp, Cookie cookie) {

        addCookie(resp, cookie, Constants.COOKIE_PATH,  (int) ONE_WEEK);
    }

    public static void addCookie(HttpServletResponse resp, Cookie cookie, int age) {

        addCookie(resp, cookie, Constants.COOKIE_PATH, age);
    }

    public static void addCookie(HttpServletResponse resp, Cookie cookie, String path, int age) {

        ValidateUtils.assertNotNull(resp, cookie, path);

        cookie.setPath(path);
        cookie.setMaxAge(age);
        resp.addCookie(cookie);
    }

    public static void removeCookie(HttpServletResponse resp, String cookieName) {

        Cookie newCookie = new Cookie(cookieName, null);
        newCookie.setMaxAge(0);
        resp.addCookie(newCookie);
    }
}