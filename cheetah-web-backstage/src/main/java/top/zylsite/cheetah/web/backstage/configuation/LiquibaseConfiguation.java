package top.zylsite.cheetah.web.backstage.configuation;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
public class LiquibaseConfiguation {
	
	@Bean
	public SpringLiquibase liquibase(DataSource dataSource) throws Exception {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog("classpath:liquibase/master.xml");
		liquibase.setContexts("development,test,production");
		liquibase.setShouldRun(true);
		return liquibase;
	}
	
}