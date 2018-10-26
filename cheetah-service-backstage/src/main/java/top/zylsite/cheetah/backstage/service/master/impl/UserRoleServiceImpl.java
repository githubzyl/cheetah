package top.zylsite.cheetah.backstage.service.master.impl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.backstage.service.master.IUserRoleService;
import top.zylsite.cheetah.backstage.mapper.master.UserRoleMapper;
import top.zylsite.cheetah.backstage.model.master.UserRole;

@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements IUserRoleService {
 
	@Autowired
	private UserRoleMapper userRoleMapper;

	@Override
	protected BaseMapper<UserRole> getBaseMapper() {
		return userRoleMapper;
	}
	
}