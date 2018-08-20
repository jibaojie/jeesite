package com.baojie.jeesite.login.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：冀保杰
 * @date：2018-08-13
 * @desc：
 */
@Service
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

    public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
    public static final String DEFAULT_USERID_PARAM = "userId";

    private String captchaParam = DEFAULT_CAPTCHA_PARAM;
    private String userIdParam = DEFAULT_USERID_PARAM;

    public String getCaptchaParam() {
        return captchaParam;
    }

    public String getUserIdParam() {
        return userIdParam;
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        // TODO 不知道什么原因，在Spring Boot应用中，无法初始化WebSubject
        WebSubject.Builder builder = new WebSubject.Builder(request, response);
        WebSubject webSubject = builder.buildWebSubject();
        ThreadContext.bind(webSubject);

        String account = getUserId(request);
        String username = getUsername(request);
        String password = getPassword(request);
        if (password == null) {
            password = "";
        }
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        String captcha = getCaptcha(request);

        return new UsernamePasswordToken(account, username, password, host, captcha, 1);
    }

    private String getUserId(ServletRequest request) {
        return WebUtils.getCleanParam(request, getUserIdParam());
    }


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        boolean b = isLoginRequest(request, response);
        if (b) {
            boolean b2 = isLoginSubmission(request, response);
            if (b2) {
                return executeLogin(request, response);
            } else {
                //allow them to see the login page ;)
                return true;
            }
        } else {
            //如果是Ajax请求，不跳转登录
            if (this.isAjax(httpServletRequest)){
                httpServletResponse.setStatus(401);
            } else {
                saveRequestAndRedirectToLogin(request, response);
            }
            return false;
        }
    }


    /**
     * 判断ajax请求
     * @param request
     * @return
     */
    boolean isAjax(HttpServletRequest request){
        String ss = request.getHeader("X-Requested-With");
        return  (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())   ) ;
    }

}
