package cn.gin.webmvc.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface JDBCCallback<T> {

    /**
     * <p>Mapped a single record in a ResultMap to a Java object.</p>
     *
     * @param res - The ResultMap which contains the all the results.
     * @return A Java object, typically a physical object.
     *
     * @throws SQLException This exception may be thrown during the ResultMap mapping.
     */
    T readObject(ResultSet res) throws SQLException;

    /**
     * <p>Injected the parameters into the SQL statement through a PreparedStatement.</p>
     *
     * @param stat - A PreparedStatement that has been precompiled.
     *
     * @throws SQLException This exception may be thrown during the PreparedStatement inject the parameters.
     */
    void injectParams(PreparedStatement stat) throws SQLException;
}