package com.baojie.jeesite.common.log;

import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ：冀保杰
 * @date：2018-08-16
 * @desc：
 */
public class LogManager {

    //日志记录操作延时
    private final int OPERATE_DELAY_TIME = 10;

    /**
     * 异步操作记录日志的线程池
     */
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);

    private LogManager() {
    }

    public static LogManager logManager = new LogManager();

    public static LogManager me() {
        return logManager;
    }

    public void executeLog(TimerTask task) {
        executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }
}
