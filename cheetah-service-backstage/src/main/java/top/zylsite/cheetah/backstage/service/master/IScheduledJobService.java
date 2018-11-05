package top.zylsite.cheetah.backstage.service.master;
 
import top.zylsite.cheetah.base.common.BaseService;

import java.util.List;

import top.zylsite.cheetah.backstage.model.master.ScheduledJob;

public interface IScheduledJobService extends BaseService<ScheduledJob>{

	/**
	 * 更新任务状态
	 * 
	 * @param id
	 * @param status
	 * @create: 2018年4月11日 下午2:35:09 zhaoyl
	 * @history:
	 */
	public void updateStatus(Integer[] ids, String status);

	/**
	 * 获取所有任务
	 * 
	 * @param containStopped
	 *            为true时包括所有已经停止的任务
	 * @create: 2018年4月12日 上午8:51:29 zhaoyl
	 * @history:
	 */
	public List<ScheduledJob> getAllJob(boolean containStopped);

	/**
	 * 更新任务的执行情况
	 * 
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @param status
	 * @create: 2018年4月12日 上午10:40:48 zhaoyl
	 * @history:
	 */
	public void updateExecuteStatus(int id, long startTime, long endTime, String executeStatus);
 
}