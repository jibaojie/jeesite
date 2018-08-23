package com.baojie.jeesite.login.shiro;

import com.baojie.jeesite.entity.sys.RoleUser;
import com.baojie.jeesite.entity.sys.UserInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * @author ：冀保杰
 * @date：2018-08-16
 * @desc：自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息
 */
@Data
public class ShiroUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private String account;

    private String userName;
    /**
     * 管理员类型
     * 0：超级管理员，拥有所有权限，1：单位管理员，拥有单位的所有权限
     */
    private Short adminType;

    private String telephone;

    private Integer loginType;

    private Integer orgId;

    private Map<String, Object> cacheMap = new HashMap<>();

    /**
     * 角色集
     */
    private List<RoleUser> roleUserList = new ArrayList<>();

    private Set<String> permissionSet = new HashSet<>();

    private Set<String> roleNameSet = new HashSet<>();

}
