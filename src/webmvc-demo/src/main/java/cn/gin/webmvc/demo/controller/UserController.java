package cn.gin.webmvc.demo.controller;

import cn.gin.webmvc.anno.RequestMapping;
import cn.gin.webmvc.controller.Controller;
import cn.gin.webmvc.demo.service.UserService;
import cn.gin.webmvc.support.Constants;

@RequestMapping(Constants.REQUEST_USER_CONTROLLER)
public class UserController implements Controller {

    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
