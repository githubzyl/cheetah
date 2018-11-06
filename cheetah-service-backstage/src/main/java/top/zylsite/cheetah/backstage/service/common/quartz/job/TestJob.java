package top.zylsite.cheetah.backstage.service.common.quartz.job;

import java.time.LocalDate;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;

import top.zylsite.cheetah.backstage.service.common.quartz.BaseQuartzJob;
import top.zylsite.cheetah.base.utils.LoggerFactoryUtil;

/**
 * Description: 测试任务
 * @author jason
 * 2018年11月6日
 * @version 1.0
 */
@DisallowConcurrentExecution
public class TestJob extends BaseQuartzJob {

	private final Logger logger = LoggerFactoryUtil.getLogger(TestJob.class);

	@Override
	protected void handle(JobExecutionContext context, Integer jobId) throws Exception {
		logger.info("正在执行测试定时任务...." + LocalDate.now());
	}

}
