package cn.gin.webmvc.demo.service;

import cn.gin.webmvc.demo.entity.User;
import cn.gin.webmvc.demo.service.exception.ParameterException;
import cn.gin.webmvc.demo.service.exception.ServiceException;

public interface UserService {

    /**
     * <p>The user login service.</p>
     *
     * @param account - The user account provided by the client.
     * @param password - The password provided by the client.
     * @return
     *
     * @throws ParameterException - The client parameters are not regular.
     * @throws ServiceException - The client parameters are not right with database.
     */
    public User login(String account, String password) throws ParameterException, ServiceException;

    /**
     * <p>Get the user's data by UserDao.</p>
     *
     * @param userId - The account of the user which needs to be queried.
     * @return The DAO's result.
     */
    public User getByUserId(Integer userId);
}