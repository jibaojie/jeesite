package com.baojie.jeesite.login.util;

import com.baojie.jeesite.common.service.RoleModuleService;
import com.baojie.jeesite.common.service.RoleUserService;
import com.baojie.jeesite.entity.sys.RoleUser;
import com.baojie.jeesite.login.shiro.ShiroUser;
import com.baojie.jeesite.util.constants.GlobalConfig;
import com.baojie.jeesite.util.redis.RedisUtil;
import com.baojie.jeesite.util.spring.SpringContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.*;

/**
 * @author ：冀保杰
 * @date：2018-08-13
 * @desc：用户工具类
 */
public class UserUtils {

    public static final String CURRENT_USER = "currentUser";
    public static final String CACHE_MENU_LIST = "menuList";
    public static final String CACHE_MENU_TREE = "menuTree";

    /**
     * 不可使用@Autowired
     */
    private static RedisUtil redisUtil = SpringContextHolder.getBean(RedisUtil.class);

    private static RoleUserService roleUserService = SpringContextHolder.getBean(RoleUserService.class);

    private static RoleModuleService roleModuleService = SpringContextHolder.getBean(RoleModuleService.class);


    /**
     * 获取subject
     */
    public static Subject getSubject () {
        return  SecurityUtils.getSubject();
    }

    /**
     * 获取ShiroUser
     */
    public static ShiroUser getShiroUser () {
        return (ShiroUser) UserUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    }

    /**
     * 退出
     */
    public static void logout() {
        try {
            UserUtils.getSubject().logout();
        } catch (UnavailableSecurityManagerException e) {
            e.printStackTrace();
        } catch (InvalidSessionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前登录用户ID
     *
     */
    public static int getUserId() {
        int userId = 0;
        try {
            Subject subject = SecurityUtils.getSubject();
//            SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal) subject.getPrincipal();
//            if (principal != null) {
//                userId = principal.getUserId();
//            }
        } catch (UnavailableSecurityManagerException e) {
        } catch (InvalidSessionException e) {
        }
        return userId;
    }

    /**
     * 获取当前登录用户信息
     *
     */
//    public static UserInfoEO getUser() {
//        UserInfoEO user = (UserInfoEO) CacheUtils.getCache(CURRENT_USER);
//        if (user == null) {
//            int userId = getUserId();
//            if(userId != 0) {
//                Map<String, Object> map = redisUtil.get(userId+"");
////                UserInfoEO userInDb = userService.getUserById(userId);
//
//                UserInfoEO userInDb = new UserInfoEO();
//                userInDb.setOrgId(map.get("OrgID")==null?-1:Integer.parseInt(String.valueOf(map.get("OrgID"))));
//                userInDb.setOrgName(String.valueOf(map.get("OrgName")));
//                userInDb.setDeptId(map.get("DeptID")==null?-1:Integer.parseInt(String.valueOf(map.get("DeptID"))));
//                userInDb.setDepartmentName(String.valueOf(map.get("DeptName")));
//                userInDb.setDepartId(map.get("DepartID")==null?-1:Integer.parseInt(String.valueOf(map.get("DepartID"))));
//                userInDb.setDepartName(String.valueOf(map.get("DepartName")));
//                userInDb.setName(String.valueOf(map.get("Name")));
//                userInDb.setUserId(map.get("UserID")==null?-1:Integer.parseInt(String.valueOf(map.get("UserID"))));
//
//                user = ObjectUtils.clone(userInDb);
//                user.setPassword(null);
//
//                CacheUtils.putCache(CURRENT_USER, user);
//            }
//        }
//        return user;
//    }

//    public static void updateUserInfo(UserInfoEO userInfoEO) {
//        UserInfoEO user = (UserInfoEO) CacheUtils.getCache(CURRENT_USER);
//        if (user != null) {
//            int userId = getUserId();
//
//            if (userId != 0) {
//                UserInfoEO userInDb = userService.get(userId);
//                userInDb.setName(userInfoEO.getName());
////                userInDb.setIdentityNumber(userInfoEO.getIdentityNumber());
//
//                userService.save(userInDb);
//
//                CacheUtils.removeCache(CURRENT_USER);
//            }
//        }
//    }

    /**
     * 判断用户是否是超级管理员
     * @param userInfoEO
     * @return
     */
//    public static boolean isAdmin(UserInfoEO userInfoEO) {
//        return userInfoEO != null && userInfoEO.getUserId() == 1;
//    }

    /**
     * 获取用户菜单权限信息
     * @return
     */
    public static SimpleAuthorizationInfo getAuthInfo(ShiroUser shiroUser) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roleNameSet = new HashSet<>();
        Set<String> permissionSet = new HashSet<>();
        //用户角色
        RoleUser roleUser = new RoleUser();
        roleUser.setUserId(shiroUser.getUserId());
        List<RoleUser> roleUserList = roleUserService.selectList(roleUser);
        for (RoleUser roleUser1 : roleUserList) {
            roleNameSet.add(roleUser1.getRoleId().toString());
        }
        //用户权限
        List<String> roleModuleList = roleModuleService.getMenusByRoleIds(roleNameSet);
        for (String url : roleModuleList) {
            if (StringUtils.isNotBlank(url)){
                permissionSet.add(url);
            }
        }
        info.addStringPermissions(permissionSet);
        info.addRoles(roleNameSet);
//        Set<Permission> permissions = new HashSet<Permission>();
//        Collection<Permission> perms = Collections.emptySet();
//        perms = new LinkedHashSet<Permission>(permissionSet.size());
//        for (String strPermission : permissionSet) {
//            Permission permission = new WildcardPermission(strPermission);
//            perms.add(permission);
//        }
//        permissions.addAll(perms);
//        info.setObjectPermissions(permissions);
        return info;
    }

    /**
     * 更新用户的登录时间以及登录IP
     */
    public static void flushUserLoginTimeAndIp() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            String loginIp = SecurityUtils.getSubject().getSession().getHost();
//            userService.updateUserLoginInfo(UserUtils.getUserId(), loginIp);
        }
    }

    public static void flush() {
//        CacheUtils.removeCache(CURRENT_USER);
//        CacheUtils.removeCache(CACHE_MENU_LIST);
//        CacheUtils.removeCache(CACHE_MENU_TREE);
    }

    /**
     * 判断是否需要验证验证码
     *
     * @param account
     * @return
     */
    public static boolean isNeedValidCode(String account) {
        Integer loginFailNum = redisUtil.get(GlobalConfig.VALICATE_CODE_PRE + GlobalConfig.LOGIN_FAIL_COUNT + "_" + account);
        if (loginFailNum == null) {
            loginFailNum = 0;
        }
        return loginFailNum >= GlobalConfig.MAX_LOGIN_ERROE_COUNT;
    }

    /**
     * 登录失败次数增加一次
     *
     * @param account
     * @return
     */
    public static void increaseLoginErrorCount(String account) {
        Integer loginFailNum = redisUtil.get(GlobalConfig.VALICATE_CODE_PRE + GlobalConfig.LOGIN_FAIL_COUNT + "_" + account);
        if (loginFailNum == null) {
            loginFailNum = 0;
        }
        loginFailNum++;
        redisUtil.set(GlobalConfig.VALICATE_CODE_PRE + GlobalConfig.LOGIN_FAIL_COUNT + "_" + account, loginFailNum, GlobalConfig.LOGIN_FAIL_COUNT_LIMIT_TIME);
    }

}
