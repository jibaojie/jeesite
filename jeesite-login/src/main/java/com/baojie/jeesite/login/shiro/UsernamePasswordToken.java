package com.baojie.jeesite.login.shiro;

import lombok.Data;

/**
 * @author ：冀保杰
 * @date：2018-08-13
 * @desc：
 */
@Data
public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

    /**
     * 当前登录的账户
     */
    private String account;

    private String userId;
    /**
     * 验证码
     */
    private String captcha;
    /**
     * 登录类型
     */
    private Integer loginType;

    public UsernamePasswordToken() {
        super();
    }

    public UsernamePasswordToken(String username, String password, Integer loginType) {
        super(username, password);
        this.loginType = loginType;
    }

    public UsernamePasswordToken(String username, String password, String captcha, Integer loginType) {
        super(username, password);
        this.captcha = captcha;
        this.loginType = loginType;
    }

    public UsernamePasswordToken(String account, String username, String password, String captcha) {
        super(username, password);
        this.captcha = captcha;
        this.account = account;
    }

    public UsernamePasswordToken(String account, String username, String password, String host, String captcha, Integer loginType) {
        super(username, password, host);
        this.captcha = captcha;
        this.account = account;
        this.loginType= loginType;
    }


}
