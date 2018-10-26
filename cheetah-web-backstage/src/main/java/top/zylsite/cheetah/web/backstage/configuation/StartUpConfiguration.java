package top.zylsite.cheetah.web.backstage.configuation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import top.zylsite.cheetah.base.utils.SpringUtil;

/**
 * Description: 项目启动后需要进行的操作可在此处进行设置
 * @author jason
 * 2018年10月26日
 * @version 1.0
 */
@Configuration
public class StartUpConfiguration implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext applicationContext = event.getApplicationContext();
		SpringUtil.setApplicationContext(applicationContext);
	}

}
