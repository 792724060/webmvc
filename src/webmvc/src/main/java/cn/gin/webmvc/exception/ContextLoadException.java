package cn.gin.webmvc.exception;

public class ContextLoadException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ContextLoadException() {

    }

    public ContextLoadException(String message) {
        super(message);
    }

    public ContextLoadException(Throwable throwable) {
        super(throwable);
    }
}