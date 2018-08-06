package com.baojie.jeesite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author 冀保杰
 * @date 2018-07-30
 */

@MapperScan(basePackages = "com.baojie.jeesite.dao")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class JeeSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(JeeSiteApplication.class, args);
	}
}
