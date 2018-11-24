package cn.gin.webmvc.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractJDBCCallback<T> implements JDBCCallback<T> {

    @Override
    public T readObject(ResultSet res) throws SQLException {

        return null;
    }

    @Override
    public void injectParams(PreparedStatement stat) throws SQLException {

    }
}