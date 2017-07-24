package com.party.core.exception;

/**
 * party
 * Created by wei.li
 * on 2016/10/8 0008.
 */
public class OrderException extends BusinessException {

    private static final long serialVersionUID = 3649160264592228927L;

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public void setMessage(String message) {
        super.setMessage(message);
    }

    @Override
    public Integer getCode() {
        return super.getCode();
    }

    @Override
    public void setCode(Integer code) {
        super.setCode(code);
    }

    public OrderException() {
        super();
    }

    public OrderException(String message) {
        super(message);
    }

    public OrderException(Integer code, String message) {
        super(code, message);
    }

    public OrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public OrderException(Throwable cause) {
        super(cause);
    }
}
