package top.zylsite.cheetah.backstage.service.common.quartz;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.JobExecutionContextImpl;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.quartz.spi.TriggerFiredBundle;

public class QuartzManager {

	private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

	// 线程池
	private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

	private final static String EXECUTE_METHOD_NAME = "execute";
	public static String JOB_NAME_PREFIX = "EXTJWEB_JOB_NAME";
	private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
	private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";
	private static String TRIGGER_NAME_SUFFIX = "EXTJWEB_TRIGGER_NAME";

	public static void addJob(String jobName, Class<? extends BaseQuartzJob> jobClass, String cron) {
		addJob(getJobName(jobName), JOB_GROUP_NAME, getTrriggerName(jobName), TRIGGER_GROUP_NAME, jobClass, cron);
	}

	public static void modifyJobTime(String jobName, String cron, Class<? extends BaseQuartzJob> jobClass) {
		modifyJobTime(getJobName(jobName), JOB_GROUP_NAME, getTrriggerName(jobName), TRIGGER_GROUP_NAME, cron,
				jobClass);
	}

	public static void removeJob(String jobName) {
		removeJob(getJobName(jobName), JOB_GROUP_NAME, getTrriggerName(jobName), TRIGGER_GROUP_NAME);
	}

	public static void removeAllJob(List<String> jobNames) {
		if (null == jobNames || jobNames.size() <= 0) {
			return;
		}
		shutdownAllJob();
		for (String jobName : jobNames) {
			removeJob(jobName);
		}
	}

	public static void executeJobNow(String jobName, Class<? extends BaseQuartzJob> jobClass, String cron,
			boolean onlyExecute) {
		fixedThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				if (onlyExecute) {
					// 立即执行一次
					executeNow(jobName, jobClass);
				} else {
					// 先删除原有任务
					removeJob(jobName);
					// 立即执行一次
					executeNow(jobName, jobClass);
					// 添加任务
					addJob(jobName, jobClass, cron);
				}
			}
		});
	}

	public static boolean startJob(String jobName) {
		JobKey jobKey = getJob(jobName);
		if (null != jobKey) {
			try {
				Scheduler sched = schedulerFactory.getScheduler();
				sched.triggerJob(jobKey);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	public static boolean pauseJob(String jobName) {
		JobKey jobKey = getJob(jobName);
		if (null != jobKey) {
			try {
				Scheduler sched = schedulerFactory.getScheduler();
				sched.pauseJob(jobKey);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	public static JobKey getJob(String jobName) {
		jobName = getJobName(jobName);
		try {
			Scheduler sched = schedulerFactory.getScheduler();
			JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
			JobDetail jobDetail = sched.getJobDetail(jobKey);
			if (null != jobDetail) {
				return jobKey;
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Description: 添加一个定时任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobGroupName
	 *            任务组名
	 * @param triggerName
	 *            触发器名
	 * @param triggerGroupName
	 *            触发器组名
	 * @param jobClass
	 *            任务
	 * @param cron
	 *            时间设置，参考quartz说明文档
	 */
	public static void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
			Class<? extends BaseQuartzJob> jobClass, String cron) {
		try {
			Scheduler sched = schedulerFactory.getScheduler();
			// 任务名，任务组，任务执行类
			JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();

			// 触发器
			TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
			// 触发器名,触发器组
			triggerBuilder.withIdentity(triggerName, triggerGroupName);
			triggerBuilder.startNow();
			// 触发器时间设定
			triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
			// 创建Trigger对象
			CronTrigger trigger = (CronTrigger) triggerBuilder.build();

			// 调度容器设置JobDetail和Trigger
			sched.scheduleJob(jobDetail, trigger);

			// 启动
			if (!sched.isShutdown()) {
				sched.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description: 修改一个任务的触发时间
	 * 
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 *            触发器名
	 * @param triggerGroupName
	 *            触发器组名
	 * @param cron
	 *            时间设置，参考quartz说明文档
	 */
	public static void modifyJobTime(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
			String cron, Class<? extends BaseQuartzJob> jobClass) {
		try {
			Scheduler sched = schedulerFactory.getScheduler();
			TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
			CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
			if (trigger == null) {
				return;
			}

			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(cron)) {
				// 触发器
				TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
				// 触发器名,触发器组
				triggerBuilder.withIdentity(triggerName, triggerGroupName);
				triggerBuilder.startNow();
				// 触发器时间设定
				triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
				// 创建Trigger对象
				trigger = (CronTrigger) triggerBuilder.build();
				// 方式一 ：修改一个任务的触发时间
				sched.rescheduleJob(triggerKey, trigger);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description: 移除一个任务
	 * 
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 */
	public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
		try {
			Scheduler sched = schedulerFactory.getScheduler();

			TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

			sched.pauseTrigger(triggerKey);// 停止触发器
			sched.unscheduleJob(triggerKey);// 移除触发器
			sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description:启动所有定时任务
	 */
	public static void startAllJob() {
		try {
			Scheduler sched = schedulerFactory.getScheduler();
			sched.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description:关闭所有定时任务
	 */
	public static void shutdownAllJob() {
		try {
			Scheduler sched = schedulerFactory.getScheduler();
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getJobName(String jobName) {
		return JOB_NAME_PREFIX + jobName;
	}

	private static String getTrriggerName(String jobName) {
		return TRIGGER_NAME_SUFFIX + jobName;
	}

	private static void executeNow(String jobId, Class<? extends BaseQuartzJob> jobClass) {
		try {
			Method method = jobClass.getMethod(EXECUTE_METHOD_NAME, new Class[] { JobExecutionContext.class });
			BaseQuartzJob job = jobClass.newInstance();
			job.setJobId(jobId);
			JobExecutionContext context = new JobExecutionContextImpl(new StdScheduler(null), new TriggerFiredBundle(
					new JobDetailImpl(), new SimpleTriggerImpl(), null, false, null, null, null, null), job);
			method.invoke(jobClass.newInstance(), new Object[] { context });
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}

}
