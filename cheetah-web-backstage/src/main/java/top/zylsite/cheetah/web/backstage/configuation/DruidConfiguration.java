package top.zylsite.cheetah.web.backstage.configuation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;

/**
 * Description: Druid拦截器,spring监控配置
 * @author jason
 * 2018年10月26日
 * @version 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DruidConfiguration {

	private List<String> aopPatterns;

	{
		aopPatterns = new ArrayList<>();
		aopPatterns.add("top.zylsite.cheetah.backstage.service.*");
	}

	@Bean
	public DruidStatInterceptor druidStatInterceptor() {
		return new DruidStatInterceptor();
	}

	@Bean
	public JdkRegexpMethodPointcut druidStatPointcut() {
		JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
		if (null != aopPatterns && !CollectionUtils.isEmpty(aopPatterns)) {
			Set<String> patternSet = new HashSet<>(aopPatterns);
			String[] patterns = new String[patternSet.size()];
			patternSet.toArray(patterns);
			pointcut.setPatterns(patterns);
		}
		return pointcut;
	}

	@Bean
	public Advisor druidStatAdvisor() {
		return new DefaultPointcutAdvisor(druidStatPointcut(), druidStatInterceptor());
	}

}
