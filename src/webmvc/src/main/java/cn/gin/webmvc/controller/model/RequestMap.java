package cn.gin.webmvc.controller.model;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

public class RequestMap extends Model<String, Object> {

    private static final long serialVersionUID = 1L;

    /**
     * <p>Synchronizes the properties in the current set and request.</p>
     */
    public void synchronizeData(HttpServletRequest request) {

        List<Operation> operations = getOperations();

        for (Operation operation : operations) {
            final int id = operation.getId();

            if (id == 0) {
                request.removeAttribute(operation.getKey());
            }
            else if (id == 1) {
                request.setAttribute(operation.getKey(), this.get(operation.getKey()));
            }
        }
    }

    public static RequestMap asMap(HttpServletRequest request) {

        RequestMap requestMap = new RequestMap();

        if (request != null) {
            Enumeration<String> requestAttrs = request.getAttributeNames();

            while (requestAttrs.hasMoreElements()) {
                String name = requestAttrs.nextElement();
                requestMap.put(name, request.getAttribute(name));
            }
        }

        return requestMap;
    }
}