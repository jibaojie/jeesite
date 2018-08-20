package com.baojie.jeesite.entity.system;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author ：冀保杰
 * @date：2018-08-16
 * @desc：
 */
@Data
public class LoginUser implements Serializable {

    @NotNull(message = "账号不能为空")
    private String account;

    @NotNull(message = "密码不能为空" )
    private String password;

    private String VerifyCode;

    @NotNull
    private  Integer loginType;
}
