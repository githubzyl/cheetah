package top.zylsite.cheetah.backstage.service.impl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.backstage.service.IRoleService;
import top.zylsite.cheetah.backstage.mapper.RoleMapper;
import top.zylsite.cheetah.backstage.model.Role;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements IRoleService {
 
	@Autowired
	private RoleMapper roleMapper;

	@Override
	protected BaseMapper<Role> getBaseMapper() {
		return roleMapper;
	}
	
}