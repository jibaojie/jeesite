package com.baojie.jeesite.util.http;

/**
 * @author ：冀保杰
 * @date：2018-08-13
 * @desc：
 */
import org.apache.poi.ss.formula.functions.T;

public class Result {

    private static final ResponseMessage RESPONSE_MESSAGE_SUCCESS = new ResponseMessage(ResponseMessageCodeEnum.SUCCESS.getCode(), "操作成功");

    public static ResponseMessage success() {
        return RESPONSE_MESSAGE_SUCCESS;
    }

    public static <T> ResponseMessage<T> success(T t) {
        return new ResponseMessage(ResponseMessageCodeEnum.SUCCESS.getCode(), "", t);
    }

    public static <T> ResponseMessage<T> success(String message,T t) {
        return new ResponseMessage(ResponseMessageCodeEnum.SUCCESS.getCode(), message, t);
    }

    public static ResponseMessage error() {
        return error("");
    }

    public static ResponseMessage error(String message) {
        return error(ResponseMessageCodeEnum.ERROR.getCode(), message);
    }

    public static ResponseMessage error(String code, String message) {
        return error(code, message, null);
    }

    public static <T> ResponseMessage<T> error(String code, String message, T t) {
        return new ResponseMessage(code, message, t);
    }

    public static ResponseMessage exception() {
        return exception("操作异常");
    }

    public static ResponseMessage exception(String message) {
        return exception(ResponseMessageCodeEnum.EXCEPTION.getCode(), message);
    }

    public static ResponseMessage exception(String code, String message) {
        return exception(code, message, null);
    }

    public static ResponseMessage exception(String code,String message, T t){
        return  new ResponseMessage(code,message,t);
    }
}
