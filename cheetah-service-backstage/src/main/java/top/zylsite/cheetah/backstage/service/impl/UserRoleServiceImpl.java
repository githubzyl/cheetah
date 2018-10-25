package top.zylsite.cheetah.backstage.service.impl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.backstage.service.IUserRoleService;
import top.zylsite.cheetah.backstage.mapper.UserRoleMapper;
import top.zylsite.cheetah.backstage.model.UserRole;

@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements IUserRoleService {
 
	@Autowired
	private UserRoleMapper userRoleMapper;

	@Override
	protected BaseMapper<UserRole> getBaseMapper() {
		return userRoleMapper;
	}
	
}