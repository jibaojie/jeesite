package com.baojie.jeesite.login.shiro;

import com.baojie.jeesite.entity.user.UserInfo;
import com.baojie.jeesite.login.exception.CaptchaException;
import com.baojie.jeesite.login.service.UserLoginService;
import com.baojie.jeesite.login.util.UserUtils;
import com.baojie.jeesite.util.constants.GlobalConfig;
import com.baojie.jeesite.util.exception.BaseException;
import com.baojie.jeesite.util.http.CookieUtils;
import com.baojie.jeesite.util.redis.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：冀保杰
 * @date：2018-08-13
 * @desc：
 */
@Component
public class SystemAuthorizingRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(SystemAuthorizingRealm.class);

    private static final String HASH_ALGORITHM = "MD5";
    private static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 设置realm名称
     * @param name
     */
    @Override
    public void setName(String name) {
        super.setName("systemAuthorizingRealm");
    }

    /**
     * 登录验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        UserInfo userInfo = null;

        // 如果登录失败超过3次需要验证码
        if (UserUtils.isNeedValidCode(token.getAccount())) {
            HttpServletRequest request = WebUtils.getHttpRequest(SecurityUtils.getSubject());
            //获取登录cookie
            String cookieValue = CookieUtils.getCookieValue(request);
            //获取验证码
            String validCode = redisUtil.get("login: validCode: " + cookieValue + "_" + GlobalConfig.LOGIN_VALID_CODE);
            if (token.getCaptcha() == null) {
                throw new CaptchaException("验证码不能为空.");
            }
            if (token.getCaptcha() != null && !"".equals(token.getCaptcha()) && !token.getCaptcha().toUpperCase().equals(validCode)) {
                throw new CaptchaException("验证码错误.");
            }
            //删除本地验证码
            redisUtil.remove("login: validCode: " + cookieValue + "_" + GlobalConfig.LOGIN_VALID_CODE);
        }

        //判断登录类型
        if (token.getLoginType() == 1){
            //网页端登录
            userInfo = userLoginService.getUserByAccount(token.getAccount());
        }else {
            //其他方式登录 TODO
        }

        if (userInfo == null){
            return null;
        }
        if (userInfo.getState() == 0){
            throw new BaseException("该人员已被禁用！");
        }

        ByteSource credentialsSalt = new Md5Hash(userInfo.getSalt());
        return new SimpleAuthenticationInfo(new ShiroUser(userInfo), userInfo.getPassword(), credentialsSalt, this.getName());
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置----------------------");
//        Principal principal = (Principal) getAvailablePrincipal(principals);
//        UserInfoEO user = userService.getUserByLoginName(principal.getUserId());
//        if (user != null) {
//            UserUtils.flushUserLoginTimeAndIp();
//            return UserUtils.getAuthInfo();
//        } else {
//            return null;
//        }
        return null;
    }
    /**
     * 设置认证加密方式
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher md5CredentialsMatcher = new HashedCredentialsMatcher();
        md5CredentialsMatcher.setHashAlgorithmName(GlobalConfig.HASH_ALGORITHM_NAME);
        md5CredentialsMatcher.setHashIterations(GlobalConfig.HASH_ITERATIONS);
        super.setCredentialsMatcher(md5CredentialsMatcher);
    }

    /**
     * 清空用户关联权限认证，待下次使用时重新加载
     */
    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清空所有关联认证
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }

}
