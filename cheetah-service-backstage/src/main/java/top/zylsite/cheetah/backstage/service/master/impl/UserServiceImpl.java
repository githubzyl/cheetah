package top.zylsite.cheetah.backstage.service.master.impl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zylsite.cheetah.backstage.mapper.master.UserMapper;
import top.zylsite.cheetah.backstage.model.master.User;
import top.zylsite.cheetah.backstage.service.master.IUserService;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.base.common.BaseServiceImpl;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {
 
	@Autowired
	private UserMapper userMapper;

	@Override
	protected BaseMapper<User> getBaseMapper() {
		return userMapper;
	}
	
}