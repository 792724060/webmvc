package cn.gin.webmvc.controller.model;

import cn.gin.webmvc.support.Constants;
import cn.gin.webmvc.support.util.CookieUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class Cookies extends Model<String, Cookie> {

    private static final long serialVersionUID = 1L;

    public Cookies() {}

    /**
     * <p>Synchronizes the properties in the current set and request.</p>
     */
    public void synchronizeData(HttpServletResponse response) {

        List<Operation> operations = getOperations();

        for (Operation operation : operations) {
            final int id = operation.getId();

            if (id == 0) {
                CookieUtils.removeCookie(response, operation.getKey());
            }
            else if (id == 1) {
                CookieUtils.addCookie(response, this.get(operation.getKey()));
            }
        }
    }

    public static Cookies asList(Cookie[] cookieArray) {

        Cookies cookies = new Cookies();

        if (cookieArray != null && cookieArray.length > 0) {

            for (int i = 0; i < cookieArray.length; i++) {
                if (!Constants.COOKIE_JESSIONID.equals(cookieArray[i].getName())) {
                    cookies.put(cookieArray[i].getName(), cookieArray[i]);
                }
            }
        }

        return cookies;
    }
}