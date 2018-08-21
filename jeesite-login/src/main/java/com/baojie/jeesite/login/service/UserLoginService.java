package com.baojie.jeesite.login.service;


import com.baojie.jeesite.common.base.BaseService;
import com.baojie.jeesite.entity.user.UserInfo;
import com.baojie.jeesite.login.dao.UserLoginMapper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


/**
 * @author 冀保杰
 * @date 2018-08-10 12:01:11
 * @desc
 */
@Service
public class UserLoginService extends BaseService<UserLoginMapper, UserInfo> {

    /**
     * 根据用户登录名称获取用户信息
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
        if (list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }
}