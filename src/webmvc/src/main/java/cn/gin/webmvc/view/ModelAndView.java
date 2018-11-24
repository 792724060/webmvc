package cn.gin.webmvc.view;

import com.google.common.collect.Maps;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.Map;

public class ModelAndView {

    private String view;
    private boolean redirect;
    private boolean ajax;

    private StringBuilder builder;

    private List<Cookie> cookies;
    private Map<String, Object> request;
    private Map<String, Object> session;

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }

    public boolean isAjax() {
        return ajax;
    }

    public void setAjax(boolean ajax) {
        this.ajax = ajax;
    }

    public StringBuilder getBuilder() {

        if (builder == null) {
            builder = new StringBuilder();
        }

        return builder;
    }

    public void setBuilder(StringBuilder builder) {
        this.builder = builder;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public Map<String, Object> getRequest() {

        if (request == null) {
            request = Maps.newHashMap();
        }

        return request;
    }

    public void setRequest(Map<String, Object> request) {
        this.request = request;
    }

    public Map<String, Object> getSession() {

        if (session == null) {
            session = Maps.newHashMap();
        }

        return session;
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public void setRequestAttribute(String key, Object value) {

        getRequest().put(key, value);
    }

    public void setSessionAttribute(String key, Object value) {

        getSession().put(key, value);
    }

    public void addCookie(Cookie cookie) {

        getCookies().add(cookie);
    }

    public void removeCookie(String name) {

        List<Cookie> cookies = getCookies();
        int index = -1;

        for (int i = 0; i < cookies.size(); i++) {

            if (cookies.get(i).getName().equals(name)) {
                index = i;
                break;
            }
        }

        if (index >= 0 && index < cookies.size()) {
            cookies.remove(index);
        }
    }

    public void write(String text) {

        if (this.isAjax()) {

            getBuilder().append(text);
        }
    }
}