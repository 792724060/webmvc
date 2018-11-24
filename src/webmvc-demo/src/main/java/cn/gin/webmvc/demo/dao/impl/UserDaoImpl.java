package cn.gin.webmvc.demo.dao.impl;

import cn.gin.webmvc.demo.common.jdbc.AbstractJDBCCallback;
import cn.gin.webmvc.demo.common.jdbc.JDBCTemplate;
import cn.gin.webmvc.demo.dao.UserDao;
import cn.gin.webmvc.demo.entity.User;
import cn.gin.webmvc.support.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    private JDBCTemplate<User> jdbcTemplate;

    public JDBCTemplate<User> getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JDBCTemplate<User> jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getByAccount(final String account) {

        JDBCTemplate<User> jdbcTemplate = new JDBCTemplate<User> ();
        User user = jdbcTemplate.get(Constants.SQL_USER_GETBYACCOUNT, new AbstractJDBCCallback<User> () {

            @Override
            public void injectParams(PreparedStatement stat) throws SQLException {

                stat.setString(1, account);
            }

            @Override
            public User readObject(ResultSet res) throws SQLException {

                return singleUserWrapper(res);
            }
        });

        return user;
    }

    @Override
    public User getById(final Integer userId) {

        JDBCTemplate<User> jdbcTemplate = new JDBCTemplate<User> ();
        User user = jdbcTemplate.get(Constants.SQL_USER_GETBYID, new AbstractJDBCCallback<User>() {

            @Override
            public void injectParams(PreparedStatement stat) throws SQLException {

                stat.setInt(1, userId);
            }

            @Override
            public User readObject(ResultSet res) throws SQLException {

                return singleUserWrapper(res);
            }
        });

        return user;
    }

    /**
     * <p>Wrapper the data in the ResultSet as an User object.</p>
     *
     * @param res - The ResultSet which contain the data.
     * @return - An User object contain the data.
     *
     * @throws SQLException ResultSet may throw this exception when it reads the data.
     */
    private User singleUserWrapper(ResultSet res) throws SQLException {

        User user = null;

        if (res.next()) {
            user = new User();
            user.setId(res.getInt(res.getInt(Constants.FIELD_USER_ID)));
            user.setAccount(res.getString(Constants.FIELD_USER_ACCOUNT));
            user.setSalt(res.getString(Constants.FIELD_USER_SALT));
            user.setPassword(res.getString(Constants.FIELD_USER_PASSWORD));
            user.setNickname(res.getString(Constants.FIELD_USER_NICKNAME));
        }

        return user;
    }
}