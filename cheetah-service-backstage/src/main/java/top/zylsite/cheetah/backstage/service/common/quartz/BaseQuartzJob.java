package top.zylsite.cheetah.backstage.service.common.quartz;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;

import top.zylsite.cheetah.backstage.model.master.ScheduledJob;
import top.zylsite.cheetah.backstage.service.master.IScheduledJobService;
import top.zylsite.cheetah.base.utils.LoggerFactoryUtil;
import top.zylsite.cheetah.base.utils.SpringUtil;

public abstract class BaseQuartzJob implements org.quartz.Job {

	protected static IScheduledJobService scheduledJobService = SpringUtil.getBean(IScheduledJobService.class);

	private final Logger logger = LoggerFactoryUtil.getLogger(BaseQuartzJob.class);

	// 运行状态
	public final static String JOB_STATUS_RUNNING = "0";// 运行中
	public final static String JOB_STATUS_STOPPED = "1";// 已停止

	// 执行状态
	public final static String JOB_EXECUTE_NORMAL = "0";// 正常
	public final static String JOB_EXECUTE_EXCEPTION = "1";// 异常

	protected ScheduledJob scheduledJob;
	protected String jobId;

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Integer jobId = getJobId(context);

		this.scheduledJob = scheduledJobService.queryInfoByPrimaryKey(jobId);

		long startTime = System.currentTimeMillis();
		String executeStatus = JOB_EXECUTE_NORMAL;
		try {
			this.handle(context, jobId);
		} catch (Exception e) {
			logger.error("id=" + jobId + "的任务执行失败：", e);
			executeStatus = JOB_EXECUTE_EXCEPTION;
		}
		long endTime = System.currentTimeMillis();
		if (null != jobId) {
			try {
				scheduledJobService.updateExecuteStatus(jobId, startTime, endTime, executeStatus);
			} catch (Exception e) {
				logger.error("id=" + jobId + "的任务状态更新失败：", e);
			}
		}
	}

	private Integer getJobId(JobExecutionContext context) {
		String jobId = null;
		Object obj = context.getJobInstance();
		if (null != obj && obj instanceof BaseQuartzJob) {
			BaseQuartzJob job = (BaseQuartzJob) obj;
			jobId = job.getJobId();
		}
		if (StringUtils.isBlank(jobId)) {
			JobDetail jobDetail = context.getJobDetail();
			JobKey jobKey = jobDetail.getKey();
			jobId = jobKey.getName().substring(QuartzManager.JOB_NAME_PREFIX.length());
		}
		if (StringUtils.isNotBlank(jobId)) {
			return Integer.parseInt(jobId);
		}
		return null;
	}

	protected abstract void handle(JobExecutionContext context, Integer jobId) throws Exception;

}
