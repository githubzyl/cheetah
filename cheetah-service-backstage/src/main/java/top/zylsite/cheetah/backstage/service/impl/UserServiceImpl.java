package top.zylsite.cheetah.backstage.service.impl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.backstage.service.IUserService;
import top.zylsite.cheetah.backstage.mapper.UserMapper;
import top.zylsite.cheetah.backstage.model.User;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {
 
	@Autowired
	private UserMapper userMapper;

	@Override
	protected BaseMapper<User> getBaseMapper() {
		return userMapper;
	}
	
}