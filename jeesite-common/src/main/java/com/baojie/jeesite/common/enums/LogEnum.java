package com.baojie.jeesite.common.enums;

/**
 * @author ：冀保杰
 * @date：2018-08-16
 * @desc：
 */
public enum LogEnum {

    LOGIN("登录日志"),

    LOGIN_FAIL("登录失败日志"),

    EXIT("退出日志"),

    EXCEPTION("异常日志"),

    BUSSINESS("业务日志"),

    SUCCESS("成功"),

    FAIL("失败");



    String message;

    LogEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
