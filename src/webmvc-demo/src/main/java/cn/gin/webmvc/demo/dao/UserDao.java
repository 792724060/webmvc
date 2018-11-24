package cn.gin.webmvc.demo.dao;

import cn.gin.webmvc.demo.entity.User;

public interface UserDao {

    /**
     * <p>Get the user's data from table `oes_user` by user id.</p>
     *
     * @param userId - The id of the user which needs to be queried.
     * @return The user's data.
     */
    public User getById(Integer userId);

    /**
     * <p>Get the user's data from table `oes_user` by user account.</p>
     *
     * @param account - The account of the user which needs to be queried.
     * @return The user's data.
     */
    public User getByAccount(String account);
}