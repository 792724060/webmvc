package cn.gin.webmvc.demo.service.impl;

import cn.gin.webmvc.demo.common.util.JCodec;
import cn.gin.webmvc.demo.dao.UserDao;
import cn.gin.webmvc.demo.entity.User;
import cn.gin.webmvc.demo.service.UserService;
import cn.gin.webmvc.demo.service.exception.ParameterException;
import cn.gin.webmvc.demo.service.exception.ServiceException;
import cn.gin.webmvc.support.Constants;
import org.apache.commons.lang.StringUtils;

public class UserServiceImpl implements UserService {

    private static final UserService userService = new UserServiceImpl();

    private UserDao userDao;

    public static UserService getUserService() {
        return userService;
    }

    public UserServiceImpl() {
        // Private constructor.
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User login(String account, String password) throws ParameterException, ServiceException {

        ParameterException paramException = new ParameterException();

        if(StringUtils.isEmpty(account)) {
            paramException.addErrorField(Constants.ACCOUNT, Constants.ERROR_ACCOUNT_EMPTY);
        }

        if(StringUtils.isEmpty(password)) {
            paramException.addErrorField(Constants.PASSWORD, Constants.ERROR_PASSWORD_EMPTY);
        }

        if(paramException.hasErrorField()) {
            throw paramException;
        }
        User user = userDao.getByAccount(account);

        if(user == null) {
            throw new ServiceException(Constants.ERROR_USER_NONEXISTS);
        }

        if(!JCodec.validateSaltEncrypt(password, user.getPassword(), user.getSalt())) {
            throw new ServiceException(Constants.ERROR_PASSWORD_UNMATCH);
        }

        return user;
    }

    @Override
    public User getByUserId(Integer userId) {

        if(userId == null || userId == 0) {
            return null;
        }

        return userDao.getById(userId);
    }
}