package top.zylsite.cheetah.backstage.service.impl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.backstage.service.IScheduledJobService;
import top.zylsite.cheetah.backstage.mapper.ScheduledJobMapper;
import top.zylsite.cheetah.backstage.model.ScheduledJob;

@Service
public class ScheduledJobServiceImpl extends BaseServiceImpl<ScheduledJob> implements IScheduledJobService {
 
	@Autowired
	private ScheduledJobMapper scheduledJobMapper;

	@Override
	protected BaseMapper<ScheduledJob> getBaseMapper() {
		return scheduledJobMapper;
	}
	
}