package com.baojie.jeesite;

import com.baojie.jeesite.util.spring.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import tk.mybatis.spring.annotation.MapperScan;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author 冀保杰
 * @date 2018-07-30
 */

@MapperScan(basePackages = "com.baojie.jeesite.*.dao")
@ComponentScan("com.baojie.jeesite")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class JeeSiteApplication {

    private static final Logger logger = LoggerFactory.getLogger(JeeSiteApplication.class);

	public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(JeeSiteApplication.class, args);
        SpringContextHolder.setApplicationContext(applicationContext);
        Environment env = applicationContext.getEnvironment();

        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        //访问路径
        String contextPath = env.getProperty("server.context-path");
        if (contextPath == null){
            contextPath = "";
        }
        try {
            //拼接项目的访问路径，并打印出来
            logger.info("\n----------------------------------------------------------\n\t" +
                            "Application '{}' is running! Access URLs:\n\t" +
                            "Local: \t\t{}://localhost:{}\n\t" +
                            "External: \t{}://{}:{}\n\t" +
                            "接口文档地址: \t\t{}://localhost:{}\n\t" +
                            "Profile(s): \t{}\n----------------------------------------------------------",
                    env.getProperty("application.name"),
                    protocol,
                    env.getProperty("server.port")+contextPath,
                    protocol,
                    InetAddress.getLocalHost().getHostAddress(),
                    env.getProperty("server.port")+contextPath,
                    protocol,env.getProperty("server.port")+contextPath+"swagger-ui.html",
                    env.getActiveProfiles()
            );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
	}

}
