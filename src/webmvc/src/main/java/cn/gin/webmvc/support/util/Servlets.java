package cn.gin.webmvc.support.util;

import cn.gin.webmvc.support.Constants;
import cn.gin.webmvc.support.config.Global;

public class Servlets {

    public static String getViewPath(String view) {

        if(view == null || (!view.startsWith(Constants.SEPARATOR_SLASH) && !view.startsWith(Constants.SEPARATOR_BACKSLASH))) {
            view = Global.getConfig(Constants.PROPERTIES_KEY_VIEW_404);
        }

        return Global.getConfig(Constants.PROPERTIES_KEY_VIEW_PREFIX) + view + Global.getConfig(Constants.PROPERTIES_KEY_VIEW_SUFFIX);
    }

    public static String getRedirectPath(String view) {

        if(view == null || (!view.startsWith(Constants.SEPARATOR_SLASH) && !view.startsWith(Constants.SEPARATOR_BACKSLASH))) {
            return Global.getConfig(Constants.PROPERTIES_KEY_ROOT_URI);
        }

        return Global.getConfig(Constants.PROPERTIES_KEY_ROOT_URI) + view;
    }
}
