package cn.gin.webmvc.controller;

import cn.gin.webmvc.anno.RequestMapping;
import cn.gin.webmvc.anno.RequestMethod;
import cn.gin.webmvc.exception.ContextLoadException;
import cn.gin.webmvc.support.util.StringUtils;
import com.google.common.collect.Maps;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/**
 * Map different requests to different controllers.
 */
public class ControllerMapping {

    private Map<RequestDefinition, ControllerAdaptor> requestDefinitions;
    private Map<String, Controller> controllers;

    Map<RequestDefinition, ControllerAdaptor> getRequestDefinitions() {

        if (requestDefinitions == null) {
            requestDefinitions = Maps.newHashMap();
        }

        return requestDefinitions;
    }

    public void setRequestDefinitions(Map<RequestDefinition, ControllerAdaptor> requestDefinitions) {
        this.requestDefinitions = requestDefinitions;
    }

    public Map<String, Controller> getControllers() {

        if (controllers == null) {
            controllers = Maps.newHashMap();
        }

        return controllers;
    }

    public void setControllers(Map<String, Controller> controllers) {
        this.controllers = controllers;
    }

    public void init() {

        if (controllers == null) {
            throw new ContextLoadException("The controller collection is not properly initialized.");
        }

        for (Map.Entry<String, Controller> entry : controllers.entrySet()) {

            Controller controller = entry.getValue();
            Class<? extends Controller> cls = controller.getClass();
            String[] urlPattern;

            // ~ Get the URL pattern of the controller
            RequestMapping classRequestMapping = cls.getAnnotation(RequestMapping.class);
            Method[] clsMethods = cls.getDeclaredMethods();

            // ~ Get the URL pattern of the class annotation
            if(classRequestMapping != null) {
                urlPattern = classRequestMapping.value();
            }
            else {
                urlPattern = new String[]{StringUtils.EMPTY};
            }

            // ~ Get the URL pattern of the class method annotation
            for (Method clsMethod : clsMethods) {
                RequestMapping methodRequestMapping = clsMethod.getAnnotation(RequestMapping.class);

                if (methodRequestMapping == null) {
                    continue;
                }

                String[] methodUrlPattern = methodRequestMapping.value();
                RequestMethod[] httpMethods = methodRequestMapping.method();

                RequestDefinition requestDefinition = new RequestDefinition();
                requestDefinition.setRequestUri(StringUtils.toArrayOuterJoin(urlPattern, methodUrlPattern));
                requestDefinition.setMethod(httpMethods);

                ControllerAdaptor controllerAdaptor = new ControllerAdaptor();
                controllerAdaptor.setController(controller);
                controllerAdaptor.setMethod(clsMethod);

                getRequestDefinitions().put(requestDefinition, controllerAdaptor);
            }
        }
    }

    /**
     * <p>Mapped for a matching controller adaptor with a given request definition.</p>
     *
     * @param requestDefinition - The request information.
     * @return The matching controller adaptor.
     */
    ControllerAdaptor handlerMapping(RequestDefinition requestDefinition) {

        if (requestDefinition == null) {

            return null;
        }


        for (Map.Entry<RequestDefinition, ControllerAdaptor> serverRequestDefinition : requestDefinitions.entrySet()) {

            String[] serverRequestUris = serverRequestDefinition.getKey().getRequestUri();
            RequestMethod[] serverMethods = serverRequestDefinition.getKey().getMethod();
            boolean uriLegal = false;
            boolean methodLegal = false;

            loop1:
            for (String  serverUri : serverRequestUris) {
                for (String requestUri : requestDefinition.getRequestUri()) {
                    if (serverUri.equals(requestUri)) {
                        uriLegal = true;
                        break loop1;
                    }
                }
            }

            if (uriLegal) {

                if (serverMethods.length == 0) {
                    methodLegal = true;
                }
                else {
                    loop2:
                    for (RequestMethod  serverMethod : serverMethods) {
                        for (RequestMethod requestMethod : requestDefinition.getMethod()) {
                            if (serverMethod.equals(requestMethod)) {
                                methodLegal = true;
                                break loop2;
                            }
                        }
                    }
                }
            }

            if (uriLegal && methodLegal) {
                return serverRequestDefinition.getValue();
            }
        }

        return null;
    }

    /**
     * <p>Contains the mapping required information about this request.</p>
     */
    public class RequestDefinition {

        private String[] requestUri;
        private RequestMethod[] method;

        String[] getRequestUri() {
            return requestUri;
        }

        void setRequestUri(String[] requestUri) {
            this.requestUri = requestUri;
        }

        RequestMethod[] getMethod() {
            return method;
        }

        void setMethod(RequestMethod[] method) {
            this.method = method;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + Arrays.hashCode(method);
            result = prime * result + Arrays.hashCode(requestUri);

            return result;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj) {
                return true;
            }

            if (obj == null) {
                return false;
            }

            if (getClass() != obj.getClass()) {
                return false;
            }

            RequestDefinition other = (RequestDefinition) obj;

            if (!getOuterType().equals(other.getOuterType())) {
                return false;
            }

            if (!Arrays.equals(method, other.method)) {
                return false;
            }

            return Arrays.equals(requestUri, other.requestUri);
        }

        private ControllerMapping getOuterType() {

            return ControllerMapping.this;
        }
    }
}