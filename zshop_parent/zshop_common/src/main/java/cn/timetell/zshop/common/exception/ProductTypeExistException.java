package cn.timetell.zshop.common.exception;

/**
 * Created by Timetellu on 2020/4/28.
 */
public class ProductTypeExistException extends Exception {

    public ProductTypeExistException() {
        super();
    }

    public ProductTypeExistException(String message) {
        super(message);
    }

    public ProductTypeExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductTypeExistException(Throwable cause) {
        super(cause);
    }

    protected ProductTypeExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
