package cn.gin.webmvc.support.util;

import cn.gin.webmvc.jdbc.exception.DBException;
import cn.gin.webmvc.support.Constants;
import cn.gin.webmvc.support.config.Global;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.util.Arrays;

public final class DBUtils {

    private static final ThreadLocal<Connection> connections = new ThreadLocal<Connection>();

    public static Connection getConnection() {

        Connection conn;

        try {
            Class.forName(Global.getConfig(Constants.PROPERTIES_KEY_JDBC_DRIVER));
            conn = DriverManager.getConnection(Global.getConfig(Constants.PROPERTIES_KEY_JDBC_URL), Global.getConfig(Constants.PROPERTIES_KEY_JDBC_USERNAME), Global.getConfig(Constants.PROPERTIES_KEY_JDBC_PASSWORD));
        }
        catch (ClassNotFoundException | SQLException exception) {
            throw new DBException(exception);
        }

        return conn;
    }

    public static Connection getThreadLocalConnection() {

        Connection conn = connections.get();

        if(conn == null) {
            conn = getConnection();
            connections.set(conn);
        }

        return conn;
    }

    public static void release(Connection conn, Statement stat, ResultSet res) {

        try {

            if(res != null && !res.isClosed()) {
                res.close();
            }

        }
        catch (SQLException exception) {
            throw new DBException(exception);
        }
        finally {

            try {
                if(stat != null && !stat.isClosed()) {
                    stat.close();
                }
            }
            catch (SQLException exception) {
                throw new DBException(exception);
            }
            finally {

                try {
                    if(conn != null && !conn.isClosed()) {
                        conn.close();
                    }
                }
                catch (SQLException exception) {
                    throw new DBException(exception);
                }
            }
        }
    }

    /**
     * <p>Execute the kind of create, update, delete statement, the characteristics of these SQL is that the result of the statement
     * is basic on the number of rows affected.</p>
     *
     * @param sql - The statement need to execute.
     * @param params - The parameters the SQL need.
     * @return The number of rows affected.
     *
     * @throws DBException The DAO layer does not do any processing.
     */
    public static DBHelper executeUpdate(String sql, Object... params) {

        System.out.println("Execute update sql ==> " + sql + "\r\n\tInject params ==> " + Arrays.toString(params));

        Connection conn = getConnection();
        Statement stat;
        PreparedStatement ps;
        int res;

        try {
            if(params == null || params.length == 0) {
                stat = conn.createStatement();
                res = stat.executeUpdate(sql);
                System.out.println("Update result: " + res + "<==\r\n");

                return new DBHelper(conn, stat, res);
            }
            else {
                ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                for (int i = 1; i <= params.length; i++) {
                    ps.setObject(i, params[i - 1]);
                }
                res = ps.executeUpdate();
                System.out.println("Update result: " + res + "<==\r\n");

                return new DBHelper(conn, ps, res);
            }
        }
        catch(SQLException exception) {
            throw new DBException(exception);
        }
    }

    /**
     * <p>Execute the kind of query statement, the characteristics of these SQL is that the result of the statement
     * is basic on the ResultSet.</p>
     *
     * @param sql - The statement need to execute.
     * @param params - The parameters the SQL need.
     * @return The result that JDBC returned.
     *
     * @throws SQLException The DAO layer does not do any processing.
     */
    public static DBHelper executeQuery(String sql, Object... params) throws SQLException {

        Connection conn = getConnection();
        Statement stat;
        PreparedStatement ps;

        if(params == null || params.length == 0) {
            stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);

            return new DBHelper(conn, stat, res);
        }
        else {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            for (int i = 1; i <= params.length; i++) {
                ps.setObject(i, params[i - 1]);
            }
            ResultSet res = ps.executeQuery();

            return new DBHelper(conn, ps, res);
        }
    }

    /**
     * <p>Used for splicing of SQL statements, especially for dynamic assembly of conditions on
     * condition queries.</p>
     *
     * @param builder - The SQL statement's builder.
     * @param sql - The SQL statement that needs to be appended to the builder.
     */
    public static void append(StringBuilder builder, String sql) {

        if(!StringUtils.isEmpty(sql)) {
            String upperSql = sql.toUpperCase();

            if(upperSql.startsWith("SELECT")) {
                builder.append(sql);
            }
            else if(upperSql.startsWith("WHERE")) {
                builder.append(" ");
                String before = builder.toString().toUpperCase();

                if(before.contains("WHERE")) {
                    sql = sql.substring(6);
                    sql = "AND " + sql;
                }
                builder.append(sql);
            }
            else if(upperSql.startsWith("LIMIT")) {
                builder.append(" ");
                builder.append(sql);
            }
            else if(upperSql.startsWith("ORDER BY")) {
                builder.append(" ");
                builder.append(sql);
            }
            else {
                builder.append(" ");
                builder.append(sql);
            }
        }
    }

    /**
     * <p>Inject the parameter of the prepared statement if the parameter is exists.If the parameter key
     * is not exists in the statement, the SQL statement will be returned directly.</p>
     *
     * @param sql - The SQL statement operated.
     * @param key - The parameter key.
     * @param value - The parameter value needs to be injected.
     *
     * @return The new SQL statement after inject the parameter.
     */
    public static String injectString(String sql, String key, String value) {

        if(sql.contains(key)) {
            sql = sql.replace(key, value);
        }

        return sql;
    }

    /**
     * <p>Inject the parameter of the prepared statement builder if the parameter is exists.If the parameter key
     * is not exists in the statement, the SQL statement will be returned directly.</p>
     *
     * @param builder - The SQL statement builder operated.
     * @param key - The parameter key.
     * @param value - The parameter value needs to be injected.
     *
     * @return The new SQL statement after inject the parameter.
     */
    public static StringBuilder injectString(StringBuilder builder, String key, String value) {

        String sql = builder.toString();

        if(sql.contains(key)) {
            sql = sql.replace(key, value);
        }

        builder.delete(0, builder.length());
        builder.append(sql);

        return builder;
    }

    public static class DBHelper {

        private Connection conn;
        private Statement stat;
        private ResultSet resQuery;
        private int resUpdate;

        public DBHelper(Connection conn, Statement stat, int resUpdate) {
            this.conn = conn;
            this.stat = stat;
            this.resUpdate = resUpdate;
        }

        public DBHelper(Connection conn, Statement stat, ResultSet resQuery) {
            this.conn = conn;
            this.stat = stat;
            this.resQuery = resQuery;
        }

        public Connection getConn() {
            return conn;
        }

        public void setConn(Connection conn) {
            this.conn = conn;
        }

        public Statement getStat() {
            return stat;
        }

        public void setStat(Statement stat) {
            this.stat = stat;
        }

        public ResultSet getResQuery() {
            return resQuery;
        }

        public void setResQuery(ResultSet resQuery) {
            this.resQuery = resQuery;
        }

        public int getResUpdate() {
            return resUpdate;
        }

        public void setResUpdate(int resUpdate) {
            this.resUpdate = resUpdate;
        }
    }
}