package com.baojie.jeesite.login.service;


import com.baojie.jeesite.common.base.BaseService;
import com.baojie.jeesite.common.service.RoleModuleService;
import com.baojie.jeesite.common.service.RoleUserService;
import com.baojie.jeesite.entity.sys.RoleModule;
import com.baojie.jeesite.entity.sys.RoleUser;
import com.baojie.jeesite.entity.sys.UserInfo;
import com.baojie.jeesite.login.dao.UserLoginMapper;
import com.baojie.jeesite.login.shiro.ShiroUser;
import com.baojie.jeesite.login.shiro.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author 冀保杰
 * @date 2018-08-10 12:01:11
 * @desc
 */
@Service
public class UserLoginService extends BaseService<UserLoginMapper, UserInfo> {

    @Autowired
    private RoleUserService roleUserService;

    @Autowired
    private RoleModuleService roleModuleService;

    /**
     * 根据用户登录名称获取用户信息
     *
     * @param account 手机号或者userId
     * @return
     */
    public UserInfo getUserByAccount(String account) {
        Example example = new Example(UserInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("del", 0);
//        criteria.andCondition("user_id = " + account + " OR telephone = " + account );
        criteria.andEqualTo("telephone", account);
        List<UserInfo> list = mapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 获取ShiroUser
     *
     * @param userInfo
     * @param token
     * @return
     */
    public ShiroUser getShiroUser(UserInfo userInfo, UsernamePasswordToken token) {
        ShiroUser shiroUser = new ShiroUser();
        shiroUser.setAccount(token.getAccount());
        shiroUser.setUserId(userInfo.getUserId());
        shiroUser.setLoginType(token.getLoginType());
        shiroUser.setUserName(userInfo.getUserName());
        shiroUser.setOrgId(userInfo.getOrgId());
        shiroUser.setAdminType(userInfo.getAdminType());
        shiroUser.setTelephone(userInfo.getTelephone());

        return shiroUser;
    }

}