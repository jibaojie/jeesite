package com.baojie.jeesite.login.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @author ：冀保杰
 * @date：2018-08-15
 * @desc：重写模块化用户验证器,根据登录界面传递的loginType参数,获取唯一匹配的realm
 */
public class MyModularRealmAuthenticator extends ModularRealmAuthenticator {

    private final Logger log = LoggerFactory.getLogger(MyModularRealmAuthenticator.class);

    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) throws AuthenticationException {
        Realm uniqueRealm = getUniqueRealm(realms, token);
        if (uniqueRealm == null) {
            throw new RuntimeException("没有匹配类型的realm");
        }
        return uniqueRealm.getAuthenticationInfo(token);
    }

    /**
     * 判断realms是否匹配,并返回唯一的可用的realm,否则返回空
     *
     * @param realms realm集合
     * @param token  登陆信息
     * @return 返回唯一的可用的realm
     */
    private Realm getUniqueRealm(Collection<Realm> realms, AuthenticationToken token) {
        for (Realm realm : realms) {
            if (realm.supports(token)) {
                return realm;
            }
        }
        log.error("一个可用的realm都没有找到......");
        return null;
    }

}
