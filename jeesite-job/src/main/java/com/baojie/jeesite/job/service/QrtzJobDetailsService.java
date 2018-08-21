package com.baojie.jeesite.job.service;

import com.baojie.jeesite.common.base.BaseService;
import com.baojie.jeesite.entity.job.QrtzJobDetails;
import com.baojie.jeesite.job.dao.QrtzJobDetailsMapper;
import com.baojie.jeesite.job.exception.DynamicQuartzException;
import com.baojie.jeesite.job.job.TestJob;
import com.baojie.jeesite.util.spring.SpringContextHolder;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author ：冀保杰
 * @date：2018-08-20
 * @desc：
 */
@Service
@Transactional(rollbackFor=Exception.class)
public class QrtzJobDetailsService extends BaseService<QrtzJobDetailsMapper, QrtzJobDetails>{

    @Autowired
    private QrtzJobDetailsMapper qrtzJobDetailsMapper;

	/** jobName 前缀*/
	private static final String JOB_NAME_PREFIX = "jobName.";
	/** triggerName 前缀*/
	private static final String TRIGGER_NAME_PREFIX = "triggerName.";
	/** jobName/triggerName 默认组 */
	private static final String GROUP_DEFAULT = "DEFAULT";

	@Autowired
	private Scheduler scheduler;
	
	public Integer createQrtzJobDetails(QrtzJobDetails qrtzJobDetails) throws Exception{

		// 定时服务有效性校验 (校验是否存在对应的servcie.method )
//		this.checkServiceAndMethod(qrtzJobDetails.getJobName());

		// 唯一性校验
		String jobName = JOB_NAME_PREFIX + qrtzJobDetails.getJobName();
		String triggerName = TRIGGER_NAME_PREFIX + qrtzJobDetails.getJobName();
		String jobGroup = StringUtils.isBlank(qrtzJobDetails.getJobGroup())? GROUP_DEFAULT : qrtzJobDetails.getJobGroup();
		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		if (scheduler.checkExists(jobKey)) {
			throw new DynamicQuartzException(qrtzJobDetails.getJobName() + "服务方法对应定时任务已经存在!");
		}

		// 构建job信息
		JobDetail job = JobBuilder.newJob(TestJob.class).withIdentity(jobKey).withDescription(qrtzJobDetails.getDescription()).build();
		TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, jobGroup);

        // 构建job的触发规则 cronExpression
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).startNow()
        		.withSchedule(CronScheduleBuilder.cronSchedule(qrtzJobDetails.getCronExpression())).build();

		// 注册job和trigger信息
        scheduler.scheduleJob(job, trigger);  

		return 1;
	}
	
	public Integer updateQrtzJobDetails(QrtzJobDetails qrtzJobDetails) throws Exception {
		Map<String, Object> resultMap = new HashMap<>(16);
		JobKey jobKey = JobKey.jobKey(qrtzJobDetails.getJobName(), qrtzJobDetails.getJobGroup());
		TriggerKey triggerKey = null;
		List<? extends Trigger> list = scheduler.getTriggersOfJob(jobKey);
		if (list == null || list.size() != 1) {
			return 0;
		}
		for (Trigger trigger : list) {
			//暂停触发器
			scheduler.pauseTrigger(trigger.getKey());
			triggerKey = trigger.getKey();
		}
		Trigger newTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).startNow()
              .withSchedule(CronScheduleBuilder.cronSchedule(qrtzJobDetails.getCronExpression())).build();
		scheduler.rescheduleJob(newTrigger.getKey(), newTrigger);
		logger.info("update job name:{} success", qrtzJobDetails.getJobName());
		return 1;
	}
	
	public Map<String, Object> updateQrtzJobDetails(List<QrtzJobDetails> qrtzJobDetailsList) throws Exception{
		Map<String, Object> resultMap = new HashMap<>(16);

		return resultMap;
	}


	public Integer deleteQrtzJobDetails(QrtzJobDetails qrtzJobDetails) throws Exception {
		JobKey jobKey = JobKey.jobKey(qrtzJobDetails.getJobName(), qrtzJobDetails.getJobGroup());
		boolean b = scheduler.deleteJob(jobKey);
        logger.info("delete job name:{} success", qrtzJobDetails.getJobName());
		return 1;
	}
	
	public Integer deleteQrtzJobDetails(List<QrtzJobDetails> qrtzJobDetailsList) throws Exception{

		return 1;
	}
	
	public QrtzJobDetails findQrtzJobDetailsByPrimaryKey(String id) {
		return mapper.selectByPrimaryKey(id);
	}
	
	public Page<QrtzJobDetails> findListByPage(QrtzJobDetails qrtzJobDetails, Page<QrtzJobDetails> page) {
		page = PageHelper.startPage(page.getPageNum(), page.getPageSize());
        mapper.select(qrtzJobDetails);
		return page;

	}

	public List<QrtzJobDetails> findMapList(QrtzJobDetails qrtzJobDetails) {
		return mapper.select(qrtzJobDetails);
	}
	
	public List<QrtzJobDetails> findList(QrtzJobDetails qrtzJobDetails){
		return mapper.selectByExample(qrtzJobDetails);
	}

	public Integer pauseJob(QrtzJobDetails qrtzJobDetails)
			throws Exception {
		scheduler.pauseJob(JobKey.jobKey(qrtzJobDetails.getJobName(), qrtzJobDetails.getJobGroup()));
		logger.info("pause job name:{} success", qrtzJobDetails.getJobName());
		return null;
	}

	public Integer resumeJob(QrtzJobDetails qrtzJobDetails)
			throws Exception {
		scheduler.resumeJob(JobKey.jobKey(qrtzJobDetails.getJobName(), qrtzJobDetails.getJobGroup()));
		logger.info("resume job name:{} success", qrtzJobDetails.getJobName());
		return null;
	}


	/**
	 * <li>校验服务和方法是否存在</li>
	 * @param jobName
	 * @throws DynamicQuartzException
	 */
	private void checkServiceAndMethod(String jobName) throws DynamicQuartzException {
		String[] serviceInfo = jobName.split("\\.");
		String beanName = serviceInfo[0];
		String methodName = serviceInfo[1];
		if (! SpringContextHolder.existBean(beanName)) {
			throw new DynamicQuartzException("找不到对应服务");
		}
		if (! SpringContextHolder.existBeanAndMethod(beanName, methodName, null)) {
			throw new DynamicQuartzException("服务方法不存在");
		}
		

	}
	




}
