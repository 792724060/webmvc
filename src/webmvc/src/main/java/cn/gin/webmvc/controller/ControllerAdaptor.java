package cn.gin.webmvc.controller;

import java.lang.reflect.Method;

/**
 *
 */
public class ControllerAdaptor {

    /**
     *
     */
    private Controller controller;

    /**
     *
     */
    private Method method;

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}