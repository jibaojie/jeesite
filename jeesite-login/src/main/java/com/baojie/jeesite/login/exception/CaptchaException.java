package com.baojie.jeesite.login.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author ：冀保杰
 * @date：2018-08-13
 * @desc：验证码异常
 */
public class CaptchaException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public CaptchaException() {
        super();
    }

    public CaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(Throwable cause) {
        super(cause);
    }

}
