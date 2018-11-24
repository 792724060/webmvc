package cn.gin.webmvc.jdbc.core;

import cn.gin.webmvc.jdbc.exception.DBException;
import cn.gin.webmvc.support.util.DBUtils;
import com.google.common.collect.Lists;

import java.sql.*;
import java.util.List;

public class JDBCTemplate<T> {

    public int insert(String sql, JDBCCallback<T> callback) {

        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet res = null;
        int id = 0;

        try {
            conn = DBUtils.getConnection();
            stat = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            callback.injectParams(stat);
            stat.executeUpdate();
            res = stat.getGeneratedKeys();

            if (res.next()) {
                id = res.getInt(1);
            }
        }
        catch (SQLException exception) {
            throw new DBException(exception);
        }
        finally {
            DBUtils.release(conn, stat, res);
        }

        return id;
    }

    public int delete(String sql, AbstractJDBCCallback<T> callback) {

        Connection conn = null;
        PreparedStatement stat = null;
        int data = 0;

        try {
            conn = DBUtils.getConnection();
            stat = conn.prepareStatement(sql);
            callback.injectParams(stat);
            data = stat.executeUpdate();

        }
        catch (SQLException exception) {
            throw new DBException(exception);
        }
        finally {
            DBUtils.release(conn, stat, null);
        }

        return data;
    }

    public int update(String sql, AbstractJDBCCallback<T> callback) {

        Connection conn = null;
        PreparedStatement stat = null;
        int data = 0;

        try {
            conn = DBUtils.getConnection();
            stat = conn.prepareStatement(sql);
            callback.injectParams(stat);
            data = stat.executeUpdate();

        }
        catch (SQLException exception) {
            throw new DBException(exception);
        }
        finally {
            DBUtils.release(conn, stat, null);
        }

        return data;
    }

    /**
     * <p>Execute a single record query statement, the query will return a row of database and
     * it will be packed to an entity by the callback interface.</p>
     *
     * @param sql - The SQL need to execute.
     * @param callback - {@link JDBCCallback}
     *
     * @return An object which is packed by callback interface.
     */
    public T get(String sql, JDBCCallback<T> callback) {

        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet res = null;
        T data;

        try {
            conn = DBUtils.getConnection();
            stat = conn.prepareStatement(sql);
            callback.injectParams(stat);
            res = stat.executeQuery();
            data = callback.readObject(res);
        }
        catch (SQLException exception) {
            throw new DBException(exception);
        }
        finally {
            DBUtils.release(conn, stat, res);
        }

        return data;
    }

    /**
     * <p>Execute a multiple records query statement, the query will return multiple rows of database and
     * they will be packed to an entity list by the callback interface.</p>
     *
     * @param sql - The SQL need to execute.
     * @param callback - {@link JDBCCallback}
     *
     * @return An list contained all the data that be packed by callback interface.
     */
    public List<T> find(String sql, JDBCCallback<T> callback) {

        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet res = null;
        List<T> data = Lists.newArrayList();

        try {
            conn = DBUtils.getConnection();
            stat = conn.prepareStatement(sql);
            res = stat.executeQuery();

            while (res.next()) {
                T t = callback.readObject(res);
                data.add(t);
            }
        }
        catch (SQLException exception) {
            throw new DBException(exception);
        }
        finally {
            DBUtils.release(conn, stat, res);
        }

        return data;
    }
}