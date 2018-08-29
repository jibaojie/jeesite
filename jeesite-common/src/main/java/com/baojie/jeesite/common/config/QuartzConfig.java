package com.baojie.jeesite.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

/**
 * @author ：冀保杰
 * @date：2018-08-17
 * @desc：
 */
@Configuration
@ConditionalOnProperty(prefix = "jeesite", name = "quartz", havingValue = "true")
public class QuartzConfig {

    @Autowired
    SpringJobFactory springJobFactory;

    /**
     * 这里使用项目的名称作为SCHEDULER_NAME,不存在时给默认值，这样不同项目可以使用同一个定时任务，不会冲突
     */
    @Value("${spring.application.name: default_schedulerName}" )
    private String schedulerName;

    /**
     * 这里quartz使用web项目的数据源，也可以自己新建数据源
     */
    @Autowired
    private Environment environment;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        //设置数据源
//        schedulerFactoryBean.setDataSource(databaseConfig.dataSource());
        schedulerFactoryBean.setQuartzProperties(quartzProperties());
        schedulerFactoryBean.setJobFactory(springJobFactory);
        schedulerFactoryBean.setSchedulerName(schedulerName);
        return schedulerFactoryBean;
    }

    private Properties quartzProperties() {
        Properties prop = new Properties();
        // org.quartz.scheduler.instanceName属性可为任何值，用在 JDBC JobStore
        // 中来唯一标识实例，但是所有集群节点中必须相同。
        prop.put("org.quartz.scheduler.instanceName", "MyQuartzScheduler");
        // instanceId 属性为 AUTO即可，基于主机名和时间戳来产生实例 ID。
        prop.put("org.quartz.scheduler.instanceId", "AUTO");

        // Quartz内置了一个“更新检查”特性，因此Quartz项目每次启动后都会检查官网，Quartz是否存在新版本。这个检查是异步的，不影响Quartz项目本身的启动和初始化。
        // 设置org.quartz.scheduler.skipUpdateCheck的属性为true来跳过更新检查
        prop.put("org.quartz.scheduler.skipUpdateCheck", "false");

        prop.put("org.quartz.scheduler.jobFactory.class", "org.quartz.simpl.SimpleJobFactory");

        // org.quartz.jobStore.class属性为 JobStoreTX，将任务持久化到数据中。因为集群中节点依赖于数据库来传播
        // Scheduler 实例的状态，你只能在使用 JDBC JobStore 时应用 Quartz 集群。
        // 这意味着你必须使用 JobStoreTX 或是 JobStoreCMT 作为 Job 存储；你不能在集群中使用 RAMJobStore。
        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        //mysql使用
//    	prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        //sqlserver使用
        prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.MSSQLDelegate");
        prop.put("org.quartz.jobStore.dataSource", "quartz");
        //指定数据库名称
        prop.put("org.quartz.jobStore.tablePrefix", "quartz.QRTZ_");
        // isClustered属性为 true，你就告诉了Scheduler实例要它参与到一个集群当中。这一属性会贯穿于调度框架的始终
        prop.put("org.quartz.jobStore.isClustered", "false");

        // clusterCheckinInterval属性定义了Scheduler实例检入到数据库中的频率(单位：毫秒)。Scheduler查是否其他的实例到了它们应当检入的时候未检入；
        // 这能指出一个失败的 Scheduler 实例，且当前 Scheduler 会以此来接管任何执行失败并可恢复的 Job。
        // 通过检入操作，Scheduler 也会更新自身的状态记录。clusterChedkinInterval 越小，Scheduler
        // 节点检查失败的 Scheduler 实例就越频繁。默认值是 15000 (即15 秒)
//    	prop.put("org.quartz.jobStore.clusterCheckinInterval", "20000");
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "10");
        prop.put("org.quartz.dataSource.quartz.driver", environment.getProperty("spring.datasource.driver-class-name"));
        prop.put("org.quartz.dataSource.quartz.URL", environment.getProperty("spring.datasource.url"));
        prop.put("org.quartz.dataSource.quartz.user", environment.getProperty("spring.datasource.username"));
        prop.put("org.quartz.dataSource.quartz.password", environment.getProperty("spring.datasource.password"));
        prop.put("org.quartz.dataSource.quartz.maxConnections", "100");
        prop.put("org.quartz.dataSource.quartz.validationQuery", "SELECT 1 FROM DUAL");
        return prop;
    }

}
