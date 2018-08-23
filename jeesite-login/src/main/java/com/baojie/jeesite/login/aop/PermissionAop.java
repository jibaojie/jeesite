package com.baojie.jeesite.login.aop;

import com.baojie.jeesite.common.annotion.Permission;
import com.baojie.jeesite.login.service.PermissionCheckService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.NoPermissionException;
import java.lang.reflect.Method;

/**
 * @author ：冀保杰
 * @date：2018-08-21
 * @desc：AOP 权限自定义检查
 */
@Aspect
@Component
public class PermissionAop {

    @Autowired
    private PermissionCheckService permissionCheckService;

    @Pointcut(value = "@annotation(com.baojie.jeesite.common.annotion.Permission)")
    private void cutPermission() {
    }

    @Around("cutPermission()")
    public Object doPermission(ProceedingJoinPoint point) throws Throwable {
        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        Permission permission = method.getAnnotation(Permission.class);
        Object[] permissions = permission.value();
        return point.proceed();
//        if (permissions == null || permissions.length == 0) {
//            //检查全体角色
//            boolean result = permissionCheckService.checkAll();
//            if (result) {
//                return point.proceed();
//            } else {
//                throw new NoPermissionException();
//            }
//        } else {
//            //检查指定角色
//            boolean result = permissionCheckService.check(permissions);
//            if (result) {
//                return point.proceed();
//            } else {
//                throw new NoPermissionException();
//            }
//        }

    }


}
