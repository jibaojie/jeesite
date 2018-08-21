package com.baojie.jeesite.common.log;

import com.baojie.jeesite.common.enums.LogEnum;
import com.baojie.jeesite.common.service.LoginLogService;
import com.baojie.jeesite.common.service.OperationLogService;
import com.baojie.jeesite.entity.log.LoginLog;
import com.baojie.jeesite.entity.log.OperationLog;
import com.baojie.jeesite.util.spring.SpringContextHolder;
import com.baojie.jeesite.util.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.TimerTask;

/**
 * @author ：冀保杰
 * @date：2018-08-16
 * @desc：
 */
public class LogTaskFactory {

    private static Logger logger = LoggerFactory.getLogger(LogTaskFactory.class);

    private static LoginLogService loginLogService = SpringContextHolder.getBean(LoginLogService.class);

    private static OperationLogService operationLogService = SpringContextHolder.getBean(OperationLogService.class);


    /**
     * 登录日志
     * @param userId 用户名
     * @param ip 登录ip
     * @return
     */
    public static TimerTask loginLog(final Integer userId, final String ip, final String logEnum) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    LoginLog loginLog = new LoginLog();
                    loginLog.setKid(UUID.randomUUID(32));
                    loginLog.setUserId(userId);
                    loginLog.setLogName("");
                    loginLog.setIp(ip);
                    loginLog.setMessage("");
                    loginLogService.insert(loginLog);
                } catch (Exception e) {
                    logger.error("创建登录日志异常!", e);
                }
            }
        };
    }

    /**
     * 登出日志
     * @param userId
     * @param ip
     * @return
     */
    public static TimerTask exitLog(final Integer userId, final String ip, final String exit) {
        return new TimerTask() {
            @Override
            public void run() {
                LoginLog loginLog = new LoginLog();
                loginLog.setKid(UUID.randomUUID(32));
                loginLog.setUserId(userId);
                loginLog.setLogName("");
                loginLog.setIp(ip);
                loginLog.setMessage("");
                try {
                    loginLogService.insert(loginLog);
                } catch (Exception e) {
                    logger.error("创建退出日志异常!", e);
                }
            }
        };
    }

    /**
     * 业务系统日志
     * @param userId
     * @param bussinessName 业务名称
     * @param clazzName 类名
     * @param methodName 方法名称
     * @param msg 信息
     * @return
     */
    public static TimerTask bussinessLog(final Integer userId,
                                         final Integer logType,
                                         final String bussinessName,
                                         final String clazzName,
                                         final String methodName,
                                         final String msg) {
        return new TimerTask() {
            @Override
            public void run() {
                OperationLog operationLog = new OperationLog();
                operationLog.setKid(UUID.randomUUID(32));
                operationLog.setLogType(logType);
                operationLog.setClassName(clazzName);
                operationLog.setMethod(methodName);
                operationLog.setMessage(msg);
                try {
                    operationLogService.insert(operationLog);
                } catch (Exception e) {
                    logger.error("创建业务日志异常!", e);
                }
            }
        };
    }








}
