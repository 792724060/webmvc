package cn.gin.webmvc.controller;

import cn.gin.webmvc.anno.RequestMethod;
import cn.gin.webmvc.context.ApplicationContext;
import cn.gin.webmvc.controller.model.Cookies;
import cn.gin.webmvc.controller.model.ParameterMap;
import cn.gin.webmvc.controller.model.RequestMap;
import cn.gin.webmvc.controller.model.SessionMap;
import cn.gin.webmvc.exception.ContextLoadException;
import cn.gin.webmvc.support.Constants;
import cn.gin.webmvc.support.config.Global;
import cn.gin.webmvc.support.util.Servlets;
import cn.gin.webmvc.support.util.StringUtils;
import cn.gin.webmvc.view.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ControllerMapping controllerMapping;

    public ControllerMapping getControllerMapping() {
        return controllerMapping;
    }

    public void setControllerMapping(ControllerMapping controllerMapping) {
        this.controllerMapping = controllerMapping;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        super.init(config);
        ServletContext servletContext = config.getServletContext();
        ApplicationContext context = (ApplicationContext) servletContext.getAttribute(Constants.CONTEXT_ATTR_KEY);
        controllerMapping = context.getControllerMapping();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ~ Handler the static files
        String requestURI = req.getRequestURI();
        String staticPath = Global.getConfig(Constants.PROPERTIES_KEY_STATIC);
        String[] staticPaths = staticPath.replaceAll(Constants.MARK_WHITESPACE, StringUtils.EMPTY).split(Constants.MARK_COMMA);
        boolean isStaticResource = false;

        for (String path : staticPaths) {
            Pattern pattern = Pattern.compile(path);
            Matcher matcher = pattern.matcher(requestURI);

            if (matcher.matches()) {
                isStaticResource = true;
                break;
            }
        }

        //// Handling static resources using the default servlet in Servlet API
        if (isStaticResource) {

            RequestDispatcher dispatcher = req.getServletContext().getNamedDispatcher(Constants.COMMON_DEFAULT_SERVLET_NAME);

            if (dispatcher == null) {
                throw new IllegalStateException(Constants.ERROR_DEFAULT_SERVLET_NOTFOUND +  Constants.COMMON_DEFAULT_SERVLET_NAME);
            }

            dispatcher.forward(req, resp);

            return;
        }

        //// The request is not a static resource request
        if (controllerMapping == null) {
            throw new ContextLoadException("The controller mapping is not initialized.");
        }

        ControllerMapping.RequestDefinition requestDefinition = controllerMapping.new RequestDefinition();
        requestURI = requestURI.replace(req.getContextPath(), StringUtils.EMPTY);
        requestDefinition.setRequestUri(new String[]{requestURI});
        requestDefinition.setMethod(new RequestMethod[]{getHttpMethod(req)});

        ControllerAdaptor controllerAdaptor = controllerMapping.handlerMapping(requestDefinition);

        if (controllerAdaptor != null) {

            Controller controller = controllerAdaptor.getController();
            Method method = controllerAdaptor.getMethod();

            // 1. Prepared the parameter map.
            Class<?>[] targetParams = method.getParameterTypes();
            Object[] injectParams = null;
            ParameterMap parameterMap = new ParameterMap(req.getParameterMap());
            RequestMap requestMap = RequestMap.asMap(req);
            SessionMap sessionMap = SessionMap.asMap(req.getSession());
            Cookies cookies = Cookies.asList(req.getCookies());

            if (targetParams.length > 0) {
                injectParams = new Object[targetParams.length];

                for (int i = 0; i < targetParams.length; i++) {

                    Class<?> paramType = targetParams[i];

                    //// Inject the request parameter maps
                    if(paramType == ParameterMap.class) {
                        injectParams[i] =  parameterMap;
                    }

                    //// Inject the request attribute maps
                    else if(paramType == RequestMap.class) {
                        injectParams[i] = requestMap;
                    }

                    //// Inject the session attribute maps
                    else if(paramType == SessionMap.class) {
                        injectParams[i] = sessionMap;
                    }

                    //// Inject the cookie attribute maps
                    else if(paramType == Cookies.class) {
                        injectParams[i] = cookies;
                    }

                    //// Inject null value
                    else {
                        injectParams[i] = null;
                    }
                }
            }

            // ~ Prepared the view and forward to controller
            ModelAndView modelAndView;

            try {
                modelAndView = (ModelAndView) method.invoke(controller, injectParams);
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
                throw new ServletException(exception);
            }

            if (requestMap.isUpdate()) {
                requestMap.synchronizeData(req);
            }

            if (sessionMap.isInvalid()) {
                req.getSession().invalidate();
            }
            else {
                if (sessionMap.isUpdate()) {
                    sessionMap.synchronizeData(req.getSession());
                }
            }

            if (cookies.isUpdate()) {
                cookies.synchronizeData(resp);
            }

            if (modelAndView != null) {
                if (modelAndView.isAjax()) {
                    PrintWriter out = resp.getWriter();
                    out.print(modelAndView.getBuilder().toString());
                    out.flush();
                    out.close();
                }
                else {
                    if (modelAndView.isRedirect()) {
                        resp.sendRedirect(Servlets.getRedirectPath(modelAndView.getView()));
                    }
                    else {
                        req.getRequestDispatcher(Servlets.getViewPath(modelAndView.getView())).forward(req, resp);
                    }
                }
            }
            else {
                // Handler the AJAX request
            }

        }
        else {
            // Not found.
            resp.sendError(404);
        }
    }

    /**
     *
     * <p>Return the HTTP method of this request.</p>
     *
     * @param req - The {@link HttpServletRequest} object for this request.
     *
     * @return Return the wrapper object of this request's HTTP method.
     */
    private RequestMethod getHttpMethod(HttpServletRequest req) {

        final String method = req.getMethod();

        if(StringUtils.isEmpty(method)) {
            return null;
        }

        return method.equalsIgnoreCase(Constants.HTTP_POST) ? RequestMethod.POST : RequestMethod.GET;
    }
}