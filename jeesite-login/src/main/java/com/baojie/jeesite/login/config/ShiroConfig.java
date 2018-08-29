package com.baojie.jeesite.login.config;

import com.baojie.jeesite.login.chache.RedisCacheManager;
import com.baojie.jeesite.login.shiro.SystemAuthorizingRealm;
import com.baojie.jeesite.login.shiro.redissession.RedisSessionDAO;
import com.baojie.jeesite.util.constants.GlobalConfig;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
//        Map<String, Filter> filterMap = new LinkedHashMap<>();
//        filterMap.put("authc", new FormAuthenticationFilter());
//        filterMap.put("role", new RoleAuthorizationFilter());
//        shiroFilterFactoryBean.setFilters(filterMap);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        filterChainDefinitionMap.put("/websocket/**", "anon");

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
        filterChainDefinitionMap.put("/**/**", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }



    @Bean(name = "securityManager")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(systemAuthorizingRealm());
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
        //使用redis管理session
//        sessionManager.setSessionDAO(getRedisSessionDao());
        //不使用缓存
//        sessionManager.setCacheManager(cacheManager());
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

    @Bean(name = "redisCacheManager")
    public RedisCacheManager redisCacheManager() {
        return new RedisCacheManager();
    }

    @Bean
    public RedisSessionDAO getRedisSessionDao(){
        return  new RedisSessionDAO();
    }

    /**
     * 在方法中 注入 securityManager,进行代理控制
     */
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
        bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        bean.setArguments(new Object[]{securityManager()});
        return bean;
    }

    /**
     * 保证实现了Shiro内部lifecycle函数的bean执行
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 启用shrio授权注解拦截方式，AOP式方法级权限检查
     */
//    @Bean
//    @DependsOn(value = "lifecycleBeanPostProcessor") //依赖其他bean的初始化
//    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
//        return new DefaultAdvisorAutoProxyCreator();
//    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }


}
