package com.baojie.jeesite.login.controller;

import com.baojie.jeesite.entity.user.UserInfo;
import com.baojie.jeesite.login.service.UserInfoService;
import com.baojie.jeesite.util.basemybatis.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 冀保杰
 * @date 2018-08-10 12:01:11
 */
@RestController
@RequestMapping(value="/provider/userInfo")
@Api(value = "/userInfo",description = "用户信息")
public class UserInfoController extends BaseController<UserInfoService, UserInfo> {


  
}