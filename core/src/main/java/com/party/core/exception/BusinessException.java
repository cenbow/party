package com.party.core.exception;

/**
 * 业务异常信息
 *
 * @author wei.li
 * @Date:8/18/15 Time:5:33 PM
 */
public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = 6401507641198338287L;

    /** 异常代码 */
    protected Integer code;

    /** 异常消息 */
    protected String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        this(99999, message);
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message, Throwable cause) {
        this(99999, message, cause);
    }

    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return "错误代码: " + code + ", 描述: " + message;
    }
}
