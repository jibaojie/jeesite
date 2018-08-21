package com.baojie.jeesite.rest;

import com.baojie.jeesite.common.base.BaseController;
import com.baojie.jeesite.entity.user.UserInfo;
import com.baojie.jeesite.login.service.UserLoginService;
import com.baojie.jeesite.util.http.ResponseMessage;
import com.baojie.jeesite.util.http.Result;
import com.baojie.jeesite.util.mybatis.BasePageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 冀保杰
 * @date 2018-08-10 12:01:11
 */
@RestController
@RequestMapping(value = "/userInfo")
@Api(value = "/userInfo", description = "用户信息")
public class UserInfoController extends BaseController<UserLoginService, UserInfo> {

    /**
     * 返回Integer还是实体看需求定
     */
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增")
    public ResponseMessage<UserInfo> insert(@RequestBody UserInfo userInfo) {
        return Result.success(baseBiz.insert(userInfo));
    }

    @RequestMapping(value = "deleteLogical", method = RequestMethod.POST)
    @ApiOperation(value = "逻辑删除， 传入del=1")
    public ResponseMessage<Integer> deleteLogical(@RequestBody UserInfo userInfo) {
        return Result.success(baseBiz.deleteLogical(userInfo));
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ApiOperation(value = "更新实体,传入实体(kid必传),null的字段不更新")
    public Integer update(@RequestBody UserInfo userInfo) {
        return baseBiz.updateSelectiveById(userInfo);
    }

    @RequestMapping(value = "getById", method = RequestMethod.GET)
    @ApiOperation(value = "主键查询")
    public ResponseMessage<UserInfo> getById(@RequestParam Integer userId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setDel((short) 0);
        return Result.success(baseBiz.selectById(userInfo));
    }

    @RequestMapping(value = "selectByMap", method = RequestMethod.GET)
    @ApiOperation(value = "分页模糊查询,传入map")
    public ResponseMessage<BasePageBean<UserInfo>> selectByMap(@RequestParam Map<String, Object> map) {
        return Result.success(baseBiz.selectByMap(map));
    }

}