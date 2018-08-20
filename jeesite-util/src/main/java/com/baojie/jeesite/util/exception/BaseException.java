package com.baojie.jeesite.util.exception;

/**
 * @author ：冀保杰
 * @date：2018-08-15
 * @desc：自定义异常
 */
public class BaseException extends RuntimeException{

    private String errorCode;

    public BaseException() {
    }

    public BaseException(String message) {
        this("-1", message);
    }

    public BaseException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
