package com.baojie.jeesite.login.controller;

import com.baojie.jeesite.entity.system.LoginUser;
import com.baojie.jeesite.login.exception.CaptchaException;
import com.baojie.jeesite.login.shiro.ShiroUser;
import com.baojie.jeesite.login.shiro.UsernamePasswordToken;
import com.baojie.jeesite.login.util.UserUtils;
import com.baojie.jeesite.login.validatecode.IVerifyCodeGen;
import com.baojie.jeesite.login.validatecode.SimpleCharVerifyCodeGenImpl;
import com.baojie.jeesite.login.validatecode.VerifyCode;
import com.baojie.jeesite.util.constants.GlobalConfig;
import com.baojie.jeesite.util.http.CookieUtils;
import com.baojie.jeesite.util.http.ResponseMessage;
import com.baojie.jeesite.util.http.Result;
import com.baojie.jeesite.util.redis.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @author ：冀保杰
 * @date：2018-08-10
 * @desc：
 */
@RestController
@RequestMapping(value = "/login")
@Api(value = "/login", description = "登录相关接口")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 连续登录超失败超过三次，显示验证码
     * @param request
     * @param response
     */
    @ApiOperation(value = "获取验证码")
    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
        IVerifyCodeGen iVerifyCodeGen = new SimpleCharVerifyCodeGenImpl();
        try {
            VerifyCode verifyCode = iVerifyCodeGen.generate(80, 28);
            String cookieValue = CookieUtils.getCookieValueIfNullThenSetCookie(request, response);
            //保存验证码
            redisUtil.set(GlobalConfig.VALICATE_CODE_PRE + cookieValue + "_" + GlobalConfig.LOGIN_VALID_CODE, verifyCode.getCode(), GlobalConfig.REGISTRY_CODE_EXPIRETIME);
            request.getSession().setAttribute("VerifyCode", verifyCode.getCode());
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            response.getOutputStream().write(verifyCode.getImgBytes());
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "登录")
    @PostMapping(value = "/login")
    public ResponseMessage loginRest(@RequestBody @Valid LoginUser loginUser, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        String host = SecurityUtils.getSubject().getSession().getHost();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginUser.getAccount(), null, loginUser.getPassword(), host, loginUser.getVerifyCode(), loginUser.getLoginType());
        try {
            subject.login(usernamePasswordToken);
        } catch (CaptchaException e) {
            logger.info("验证码验证失败");
            return Result.error(e.getMessage());
        } catch (UnknownAccountException e) {
            //记录登录失败次数
            UserUtils.increaseLoginErrorCount(loginUser.getAccount());
            logger.info("用户[{}]身份验证失败", loginUser.getAccount());
            boolean isNeedValidCode = UserUtils.isNeedValidCode(loginUser.getAccount());
            return Result.error("您输入的用户不存在！");
        } catch (IncorrectCredentialsException e) {
            UserUtils.increaseLoginErrorCount(loginUser.getAccount());
            logger.info("用户[{}]密码验证失败", loginUser.getAccount());
            return Result.error("您输入的帐号或密码有误");
        } catch (AuthenticationException e) {
            // 记录日志，有未处理的验证失败
            logger.error(e.getMessage(), e);
            return Result.error(e.getMessage());
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.error(e.getMessage());
        }
        ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
        //记录登录日志
//        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getUserId(), host));

        return Result.success();
    }

    @ApiOperation(value = "退出登录")
    @GetMapping("/logout")
    public ResponseMessage logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.removeCookie(request, response);
        UserUtils.logout();
        return Result.success();
    }

}
