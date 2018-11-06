package top.zylsite.cheetah.web.backstage.configuation;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import top.zylsite.cheetah.backstage.model.master.ScheduledJob;
import top.zylsite.cheetah.backstage.service.common.quartz.BaseQuartzJob;
import top.zylsite.cheetah.backstage.service.common.quartz.QuartzManager;
import top.zylsite.cheetah.backstage.service.master.IScheduledJobService;
import top.zylsite.cheetah.base.utils.SpringUtil;

/**
 * Description: 项目启动后需要进行的操作可在此处进行设置
 * 
 * @author jason 2018年10月26日
 * @version 1.0
 */
@Configuration
public class StartUpConfiguration implements ApplicationListener<ContextRefreshedEvent> {
	
	private final static String EXEC_ON_STARTUP = "1";

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext applicationContext = event.getApplicationContext();
		SpringUtil.setApplicationContext(applicationContext);

		// 启动所有运行中的任务
		startAllJob();
	}

	// 启动所有运行中的任务
	@SuppressWarnings("unchecked")
	private void startAllJob() {
		IScheduledJobService scheduledJobService = SpringUtil.getBean(IScheduledJobService.class);
		List<ScheduledJob> list = scheduledJobService.getAllJob(false);
		if (null != list && list.size() > 0) {
			Class<? extends BaseQuartzJob> jobClass = null;
			for (ScheduledJob job : list) {
				try {
					jobClass = (Class<? extends BaseQuartzJob>) Class.forName(job.getVcJobclassName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				if (EXEC_ON_STARTUP.equals(job.getcExecOnStartup())) {
					QuartzManager.executeJobNow(job.getId().toString(), jobClass, job.getVcCronExpression(), false);
				} else {
					QuartzManager.addJob(job.getId().toString(), jobClass, job.getVcCronExpression());
				}
			}
		}
	}
	
}
