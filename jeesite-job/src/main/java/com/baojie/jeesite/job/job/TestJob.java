package com.baojie.jeesite.job.job;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：冀保杰
 * @date：2018-08-17
 * @desc：
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class TestJob implements Job{

    protected final Logger logger= LoggerFactory.getLogger(TestJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("任务执行了。。。。。。。。。。。。");
    }
}
