package top.zylsite.cheetah.backstage.service.master.impl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.backstage.service.master.IUserViewLogService;
import top.zylsite.cheetah.backstage.mapper.master.UserViewLogMapper;
import top.zylsite.cheetah.backstage.model.master.UserViewLog;

@Service
public class UserViewLogServiceImpl extends BaseServiceImpl<UserViewLog> implements IUserViewLogService {
 
	@Autowired
	private UserViewLogMapper userViewLogMapper;

	@Override
	protected BaseMapper<UserViewLog> getBaseMapper() {
		return userViewLogMapper;
	}
	
}