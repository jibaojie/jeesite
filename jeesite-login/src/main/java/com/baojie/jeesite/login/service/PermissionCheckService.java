package com.baojie.jeesite.login.service;

import com.baojie.jeesite.login.shiro.ShiroUser;
import com.baojie.jeesite.login.util.UserUtils;
import com.baojie.jeesite.util.util.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：冀保杰
 * @date：2018-08-21
 * @desc：检查权限
 */
@Service
public class PermissionCheckService {


    public boolean check(Object[] permissions) {
        //获取当前登录人员信息
        ShiroUser user = UserUtils.getShiroUser();
        if (null == user) {
            return false;
        }
        Subject subject = UserUtils.getSubject();
        //变量当前用户是否有该权限
        for (Object obj: permissions){
            if (subject.hasRole(obj.toString())){
                return true;
            }
        }
        return false;
    }

    public boolean checkAll() {
//        HttpServletRequest request = HttpKit.getRequest();
//        ShiroUser user = ShiroKit.getUser();
//        if (null == user) {
//            return false;
//        }
//        String requestURI = request.getRequestURI().replace(ConfigListener.getConf().get("contextPath"), "");
//        String[] str = requestURI.split("/");
//        if (str.length > 3) {
//            requestURI = "/" + str[1] + "/" + str[2];
//        }
//        if (ShiroKit.hasPermission(requestURI)) {
//            return true;
//        }
        return false;
    }
}
