package com.baojie.jeesite.rest;

import com.baojie.jeesite.common.base.BaseController;
import com.baojie.jeesite.entity.user.UserInfo;
import com.baojie.jeesite.login.service.UserLoginService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 冀保杰
 * @date 2018-08-10 12:01:11
 */
@RestController
@RequestMapping(value="/userInfo")
@Api(value = "/userInfo",description = "用户信息")
public class UserInfoController extends BaseController<UserLoginService, UserInfo> {



}