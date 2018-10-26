package top.zylsite.cheetah.admin;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import de.codecentric.boot.admin.config.EnableAdminServer;

@Configurable
@EnableDiscoveryClient
@EnableAdminServer
@EnableAutoConfiguration
@SpringBootApplication
public class ModuleAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModuleAdminApplication.class, args);
	}
}
