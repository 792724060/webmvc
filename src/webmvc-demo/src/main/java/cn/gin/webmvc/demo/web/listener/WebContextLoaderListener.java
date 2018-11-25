package cn.gin.webmvc.demo.web.listener;

import cn.gin.webmvc.context.ApplicationContext;
import cn.gin.webmvc.support.Constants;
import cn.gin.webmvc.support.config.Global;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class WebContextLoaderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {

        ApplicationContext context = new ApplicationContext();
        context.init();
        ServletContext servletContext = contextEvent.getServletContext();
        servletContext.setAttribute(Constants.CONTEXT_ATTR_KEY, context);

        String root = (String) servletContext.getAttribute(Constants.ATTR_APPLICATION_ROOT);
        String server = (String) servletContext.getAttribute(Constants.ATTR_APPLICATION_SERVER);

        if (StringUtils.isEmpty(root)) {
            root = Global.getConfig(Constants.PROPERTIES_KEY_ROOT_URI);
            servletContext.setAttribute(Constants.ATTR_APPLICATION_ROOT, root);
        }

        if (StringUtils.isEmpty(server)) {
            server = Global.getConfig(Constants.PROPERTIES_KEY_SERVER);
            servletContext.setAttribute(Constants.ATTR_APPLICATION_SERVER, server);
        }

        String realPath = servletContext.getRealPath(Constants.SEPARATOR_SLASH);
        Global.putConfig(Constants.PROPERTIES_KEY_ROOT_REAL, realPath);
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    }
}