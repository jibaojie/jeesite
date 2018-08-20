package com.baojie.jeesite.login.shiro;

import com.baojie.jeesite.entity.user.UserInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：冀保杰
 * @date：2018-08-16
 * @desc：自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息
 */
@Data
public class ShiroUser implements Serializable {

    private static final long serialVersionUID = 1L;

    public Integer userId;

    public String account;

    public String userName;

    public Integer loginType;

    private Map<String, Object> cacheMap = new HashMap<>();

    /**
     * 角色集
     */
    public List<Integer> roleList;

    /**
     * 角色名称集
     */
    public List<String> roleNames;

    public ShiroUser (){}

    public ShiroUser (UserInfo userInfo){
        this.userId = userInfo.getUserId();
        this.userName = userInfo.getUserName();
    }
}
