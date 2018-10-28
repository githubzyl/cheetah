package top.zylsite.cheetah.backstage.service.master.impl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.backstage.service.master.IUserLoginLogService;
import top.zylsite.cheetah.backstage.mapper.master.UserLoginLogMapper;
import top.zylsite.cheetah.backstage.model.master.UserLoginLog;

@Service
public class UserLoginLogServiceImpl extends BaseServiceImpl<UserLoginLog> implements IUserLoginLogService {
 
	@Autowired
	private UserLoginLogMapper userLoginLogMapper;

	@Override
	protected BaseMapper<UserLoginLog> getBaseMapper() {
		return userLoginLogMapper;
	}
	
}