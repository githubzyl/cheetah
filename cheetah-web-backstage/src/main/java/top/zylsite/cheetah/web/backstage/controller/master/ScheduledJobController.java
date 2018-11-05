package top.zylsite.cheetah.web.backstage.controller.master;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import top.zylsite.cheetah.backstage.model.master.ScheduledJob;
import top.zylsite.cheetah.backstage.service.common.quartz.BaseQuartzJob;
import top.zylsite.cheetah.backstage.service.common.quartz.QuartzManager;
import top.zylsite.cheetah.backstage.service.master.IScheduledJobService;
import top.zylsite.cheetah.base.common.BaseRequestController;
import top.zylsite.cheetah.base.common.BaseService;
import top.zylsite.cheetah.base.common.QueryParameter;

@RestController
@RequestMapping("/scheduledJob")
public class ScheduledJobController extends BaseRequestController<ScheduledJob> {

	@Autowired
	private IScheduledJobService scheduledJobService;

	@Override
	protected BaseService<ScheduledJob> getService() {
		return scheduledJobService;
	}

	@GetMapping("/list")
	public Object list(QueryParameter queryParameter, HttpServletRequest request) {
		return super.list(queryParameter, request);
	}

	@GetMapping("/{id}")
	public Object queryById(@PathVariable Integer id) {
		return super.queryByPrimaryKey(id);
	}

	@GetMapping("/remove/{id}")
	public Object remove(@PathVariable Integer id) {
		return super.removeByPrimaryKey(id);
	}

	@PostMapping("/save")
	public Object save(ScheduledJob entity, HttpServletRequest request) throws Exception {
		boolean startNow = Boolean.parseBoolean(StringUtils.defaultIfBlank(request.getParameter("startNow"), "false"));
		if (null == entity.getId()) {
			// 任务名称不能重复验证
			if (isExistJobName(entity.getVcJobName())) {
				return this.ajaxParamError("任务名称已经存在");
			}
			// 任务执行类和cron表达式不能同时相同的校验
			if (checkJobClassAndCron(entity.getVcJobclassName(), entity.getVcCronExpression())) {
				return this.ajaxParamError("任务执行类和cron表达式不能同时相同");
			}
			entity.setcStatus(BaseQuartzJob.JOB_STATUS_RUNNING);
			super.insertAndGetId(entity);
			int id = entity.getId();
			// 将任务添加到quartz中
			updateJob(id, entity, true);
		} else {
			super.update(entity);
			if (BaseQuartzJob.JOB_STATUS_RUNNING.equals(entity.getcStatus()) || startNow) {
				updateJob(entity.getId(), entity, false);
				if (startNow) {
					scheduledJobService.updateStatus(new Integer[] { entity.getId() },
							BaseQuartzJob.JOB_STATUS_RUNNING);
				}
			}
		}
		return this.ajaxSuccess(null);
	}

	@GetMapping("/remove")
	public Object remove(Integer[] ids) {
		// 添加从quartz任务中删除任务的操作
		if (null != ids && ids.length > 0) {
			for (Integer jobName : ids) {
				QuartzManager.removeJob(Integer.toString(jobName));
			}
		}
		return super.remove(ids);
	}
	
	/**
	 * 启动任务
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @create: 2018年4月11日 下午2:24:14 zhaoyl
	 * @history:
	 */
	@GetMapping("/startJob")
	public Object startJob(Integer[] id) throws Exception {
		if (null != id && id.length > 0) {
			for (Integer jobName : id) {
				QuartzManager.removeJob(Integer.toString(jobName));
				ScheduledJob job = getService().queryInfoByPrimaryKey(jobName);
				updateJob(jobName, job, true);
			}
		} else {
			List<ScheduledJob> list = scheduledJobService.getAllJob(true);
			addJobs(list);
		}
		scheduledJobService.updateStatus(id, BaseQuartzJob.JOB_STATUS_RUNNING);
		return this.ajaxSuccess(null);
	}

	/**
	 * 停止任务
	 * 
	 * @param id
	 * @return
	 * @create: 2018年4月11日 下午2:24:14 zhaoyl
	 * @history:
	 */
	@GetMapping("/pauseJob")
	public Object pauseJob(Integer[] id) {
		if (null != id && id.length > 0) {
			for (Integer jobName : id) {
				QuartzManager.removeJob(jobName.toString());
			}
		} else {
			List<ScheduledJob> list = scheduledJobService.getAllJob(true);
			removeJobs(list);
		}
		scheduledJobService.updateStatus(id, BaseQuartzJob.JOB_STATUS_STOPPED);
		return this.ajaxSuccess(null);
	}

	@GetMapping("/executeJobNow")
	public Object executeJobNow(Integer id) throws ClassNotFoundException {
		if (null != id) {
			ScheduledJob job = scheduledJobService.queryInfoByPrimaryKey(id);
			if (null != job && StringUtils.isNotBlank(job.getVcJobclassName())
					&& StringUtils.isNotBlank(job.getVcCronExpression())) {
				@SuppressWarnings("unchecked")
				Class<? extends BaseQuartzJob> jobClass = (Class<? extends BaseQuartzJob>) Class
						.forName(job.getVcJobclassName());
				boolean flag = true;
				// 如果是运行中的任务点了立即执行，那么要删除原来的任务，待执行完后再添加到任务队列
				if (BaseQuartzJob.JOB_STATUS_RUNNING.equals(job.getcStatus())) {
					flag = false;
				}
				QuartzManager.executeJobNow(id.toString(), jobClass, job.getVcCronExpression(), flag);
			}
		}
		return this.ajaxSuccess(null);
	}

	@Override
	protected Example getExample(QueryParameter queryParameter, HttpServletRequest request) {
		String vcJobName = request.getParameter("vcJobName");
		Example example = new Example(ScheduledJob.class);
		Example.Criteria criteria = example.createCriteria();
		super.andFullLike(criteria, "vcJobName", vcJobName);
		return example;
	}

	private boolean isExistJobName(String jobName) {
		Example example = new Example(ScheduledJob.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("vcJobName", jobName);
		List<ScheduledJob> list = scheduledJobService.queryList(example);
		if (null != list && list.size() > 0) {
			return true;
		}
		return false;
	}

	private boolean checkJobClassAndCron(String jobClass, String cron) {
		Example example = new Example(ScheduledJob.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("vcJobclassName", jobClass);
		criteria.andEqualTo("vcCronExpression", cron);
		List<ScheduledJob> list = scheduledJobService.queryList(example);
		if (null != list && list.size() > 0) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private void updateJob(int id, ScheduledJob entity, boolean isAdd) throws Exception {
		Class<? extends BaseQuartzJob> clazz = (Class<? extends BaseQuartzJob>) Class
				.forName(entity.getVcJobclassName());
		if (isAdd) {
			QuartzManager.addJob(Integer.toString(id), clazz, entity.getVcCronExpression());
		} else {
			QuartzManager.modifyJobTime(Integer.toString(id), entity.getVcCronExpression(), clazz);
		}
	}

	private void addJobs(List<ScheduledJob> list) throws Exception {
		removeJobs(list);
		if (null != list && list.size() > 0) {
			for (ScheduledJob job : list) {
				updateJob(job.getId(), job, true);
			}
			QuartzManager.startAllJob();
		}
	}

	private void removeJobs(List<ScheduledJob> list) {
		if (null != list && list.size() > 0) {
			ArrayList<String> jobNames = new ArrayList<>(list.size());
			for (ScheduledJob job : list) {
				jobNames.add(job.getId().toString());
			}
			QuartzManager.removeAllJob(jobNames);
		}
	}
	
}