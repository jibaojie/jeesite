package com.baojie.jeesite.util.http;

import lombok.Data;

/**
 * @author ：冀保杰
 * @date：2018-08-17
 * @desc：
 */
@Data
public class ValidError {

    private String field;
    private String message;

    public ValidError(){}

    public ValidError(String field, String message){
        this.field = field;
        this.message = message;
    }

}
