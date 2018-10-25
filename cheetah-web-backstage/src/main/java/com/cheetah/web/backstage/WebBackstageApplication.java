package com.cheetah.web.backstage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDiscoveryClient
@EnableEurekaClient
@EnableTransactionManagement
@SpringBootApplication
@ComponentScan(basePackages = "top.zylsite.cheetah.backstage.service,com.cheetah.web.backstage.controller")
@MapperScan(basePackages = {"top.zylsite.cheetah.backstage.mapper"})
public class WebBackstageApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebBackstageApplication.class, args);
	}
}
