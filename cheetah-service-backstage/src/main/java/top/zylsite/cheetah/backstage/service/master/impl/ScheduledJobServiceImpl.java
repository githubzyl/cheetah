package top.zylsite.cheetah.backstage.service.master.impl;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import top.zylsite.cheetah.backstage.mapper.master.ScheduledJobMapper;
import top.zylsite.cheetah.backstage.mapper.master.extend.ScheduledJobExtendMapper;
import top.zylsite.cheetah.backstage.model.master.ScheduledJob;
import top.zylsite.cheetah.backstage.service.common.quartz.BaseQuartzJob;
import top.zylsite.cheetah.backstage.service.master.IScheduledJobService;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.base.common.BaseServiceImpl;

@Service
public class ScheduledJobServiceImpl extends BaseServiceImpl<ScheduledJob> implements IScheduledJobService {
 
	@Autowired
	private ScheduledJobMapper scheduledJobMapper;
	
	@Autowired
	private ScheduledJobExtendMapper scheduledJobMapperExtend;

	@Override
	protected BaseMapper<ScheduledJob> getBaseMapper() {
		return scheduledJobMapper;
	}
	
	@Override
	@Transactional
	public void updateStatus(Integer[] ids, String status) {
		if (null != ids && ids.length > 0) {
			for (Integer id : ids) {
				scheduledJobMapperExtend.updateStatusById(id, status);
			}
		} else {
			scheduledJobMapperExtend.updateStatus(status);
		}
	}

	@Override
	public List<ScheduledJob> getAllJob(boolean containStopped) {
		Example example = null;
		if (!containStopped) {
			example = new Example(ScheduledJob.class);
			Example.Criteria criteria = example.createCriteria();
			criteria.andEqualTo("cStatus", BaseQuartzJob.JOB_STATUS_RUNNING);
		}
		List<ScheduledJob> list = super.queryList(example);
		return list;
	}

	@Override
	public void updateExecuteStatus(int id, long startTime, long endTime, String executeStatus) {
		scheduledJobMapperExtend.updateExecuteStatus(id, startTime, endTime, executeStatus);
	}
	
}