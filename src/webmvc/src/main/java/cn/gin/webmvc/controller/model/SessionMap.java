package cn.gin.webmvc.controller.model;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;

public class SessionMap extends Model<String, Object> {

    private static final long serialVersionUID = 1L;

    private boolean invalid;

    public SessionMap() {}

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    /**
     * <p>Synchronizes the properties in the current set and Session.</p>
     */
    public void synchronizeData(HttpSession session) {

        List<Operation> operations = getOperations();

        for (Operation operation : operations) {
            final int id = operation.getId();

            if (id == 0) {
                session.removeAttribute(operation.getKey());
            }
            else if (id == 1) {
                session.setAttribute(operation.getKey(), this.get(operation.getKey()));
            }
        }
    }

    public static SessionMap asMap(HttpSession session) {

        SessionMap sessionMap = new SessionMap();

        if (session != null) {
            Enumeration<String> sessionAttrs = session.getAttributeNames();

            while (sessionAttrs.hasMoreElements()) {
                String name = sessionAttrs.nextElement();
                sessionMap.put(name, session.getAttribute(name));
            }
        }

        return sessionMap;
    }
}