package com.baojie.jeesite.login.config;

import com.baojie.jeesite.login.shiro.FormAuthenticationFilter;
import com.baojie.jeesite.login.shiro.RoleAuthorizationFilter;
import com.baojie.jeesite.login.shiro.SystemAuthorizingRealm;
import com.baojie.jeesite.util.constants.GlobalConfig;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ：冀保杰
 * @date：2018-08-13
 * @desc：
 */
@Configuration
public class ShiroConfig {

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        shiroFilterFactoryBean.setLoginUrl("/login.html");
        shiroFilterFactoryBean.setSuccessUrl("/index.html");
        shiroFilterFactoryBean.setUnauthorizedUrl("/aa");
        //设置拦截器链
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("authc", new FormAuthenticationFilter());
//        filterMap.put("role", new RoleAuthorizationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //swagger相关不拦截
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");

        //登录接口
        filterChainDefinitionMap.put("/login/login", "anon");
        //验证码接口
        filterChainDefinitionMap.put("/login/verifyCode", "anon");

        /** user 通过rememberme可以访问
         * authc：该过滤器下的页面必须验证后才能访问
         * org.apache.shiro.web.filter.authc.FormAuthenticationFilter */
//        filterChainDefinitionMap.put("/login/*", "authc");
        /**
         * userInfo开头的接口会被user拦截器拦截
         * user拦截器：验证通过或RememberMe登录的都可以
         */
        filterChainDefinitionMap.put("/userInfo/*", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }



    @Bean(name = "securityManager")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(systemAuthorizingRealm());
//        defaultWebSecurityManager.setCacheManager(shiroCacheManager());
        //这个如果执行多次，也是同样的一个对象
        SecurityUtils.setSecurityManager(defaultWebSecurityManager);

        defaultWebSecurityManager.setSessionManager(defaultWebSessionManager());

        return defaultWebSecurityManager;
    }

    @Bean
    public SystemAuthorizingRealm systemAuthorizingRealm() {
        return new SystemAuthorizingRealm();
    }

    @Bean(name = "sessionManager")
    public DefaultWebSessionManager defaultWebSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //sessionManager.setSessionDAO(new CustomSessionDAO());
//        sessionManager.setCacheManager(shiroCacheManager());
        //隔多久检查一次session的有效性
        sessionManager.setSessionValidationInterval(GlobalConfig.SESSION_VALIDATION_INTERVAL);
        //全局的会话信息 session超时
        sessionManager.setGlobalSessionTimeout(GlobalConfig.SESSION_INVALI_DATETIME);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        Cookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
        //设置session名称，前端cookie必须传此参数
        cookie.setName(GlobalConfig.COOKIE_NAME);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        sessionManager.setSessionIdCookie(cookie);
        //SecurityUtils.getSubject().getSession().setTimeout(-1000L);
        return sessionManager;
    }

}
