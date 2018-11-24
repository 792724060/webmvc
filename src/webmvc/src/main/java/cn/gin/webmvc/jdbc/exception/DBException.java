package cn.gin.webmvc.jdbc.exception;

public class DBException extends RuntimeException {

    private static final long serialVersionUID = 1605473487039117808L;

    public DBException() {

    }

    public DBException(Exception e) {
        super(e);
    }
}