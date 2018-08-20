package com.baojie.jeesite.common.advice;

import com.baojie.jeesite.util.http.ResponseMessage;
import com.baojie.jeesite.util.http.Result;
import com.baojie.jeesite.util.http.ValidError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * @author ：冀保杰
 * @date：2018-08-17
 * @desc：全局异常处理
 */
@ControllerAdvice
public class NotValidExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(NotValidExceptionAdvice.class);

    /**
     * 参数校验
     * @param exception
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseMessage<ValidError> handlerConstraintViolationException(MethodArgumentNotValidException exception) {
        String result = "";
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            sb.append("参数" + fieldError.getField()).append(fieldError.getDefaultMessage()).append(",");
        }
        if (sb.length() > 0){
            result = sb.substring(0, sb.length() - 1);
        }
        return  Result.error(result);
    }

}
