package com.baojie.jeesite.entity.job;

import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author ：冀保杰
 * @date：2018-08-17
 * @desc：定时任务实体
 */
@Data
@Table(name = "quartz.qrtz_job_details")
public class QrtzJobDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private String schedName;
    private String jobName;
    private String jobGroup;
    private String description;
    private String jobClassName;
    private String isDurable;
    private String isNonconcurrent;
    private String isUpdateData;
    private String requestsRecovery;
    private String jobData;

    @Transient
    private String cronExpression;

}
