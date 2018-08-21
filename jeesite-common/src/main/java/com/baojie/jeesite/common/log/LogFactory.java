package com.baojie.jeesite.common.log;

import com.baojie.jeesite.common.enums.LogEnum;
import com.baojie.jeesite.entity.log.LoginLog;
import com.baojie.jeesite.entity.log.OperationLog;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author ：冀保杰
 * @date：2018-08-16
 * @desc：
 */
public class LogFactory {

    public static OperationLog createOperationLog(Integer logType, Integer userId, String bussinessName, String clazzName, String methodName,
                                                  String msg, LogEnum succeed) {
        OperationLog operationLog = new OperationLog();
        operationLog.setLogType(logType);
        operationLog.setLogName(bussinessName);
        operationLog.setUserId(userId);
        operationLog.setClassName(clazzName);
        operationLog.setMethod(methodName);
        operationLog.setAddDate(new Timestamp(System.currentTimeMillis()));
        // TODO
        operationLog.setSucceed(succeed.getMessage());
        operationLog.setMessage(msg);
        return operationLog;
    }

    public static LoginLog createLoginLog(String logName, Integer userId, String msg,String ip) {
        LoginLog loginLog = new LoginLog();
        loginLog.setLogName(logName);
        loginLog.setUserId(userId);
        loginLog.setAddDate(new Timestamp(System.currentTimeMillis()));
        loginLog.setSucceed(LogEnum.SUCCESS.getMessage());
        loginLog.setIp(ip);
        loginLog.setMessage(msg);
        return loginLog;
    }

}
