package top.zylsite.cheetah.web.backstage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDiscoveryClient
@EnableEurekaClient
@EnableTransactionManagement
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan(basePackages = "top.zylsite.cheetah.backstage.service,top.zylsite.cheetah.web.backstage.configuation,top.zylsite.cheetah.web.backstage.controller")
public class WebBackstageApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebBackstageApplication.class, args);
	}

}
