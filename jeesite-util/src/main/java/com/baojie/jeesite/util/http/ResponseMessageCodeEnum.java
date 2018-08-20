package com.baojie.jeesite.util.http;

/**
 * @author ：冀保杰
 * @date：2018-08-13
 * @desc：
 */
public enum ResponseMessageCodeEnum {

    SUCCESS("0"),
    ERROR("-1"),
    EXCEPTION("-2"),
    VALID_ERROR("1000"),
    RE_LOGIN("999");

    private String code;

    ResponseMessageCodeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
