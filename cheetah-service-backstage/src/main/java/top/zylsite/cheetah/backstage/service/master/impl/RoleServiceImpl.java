package top.zylsite.cheetah.backstage.service.master.impl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.backstage.service.master.IRoleService;
import top.zylsite.cheetah.backstage.mapper.master.RoleMapper;
import top.zylsite.cheetah.backstage.model.master.Role;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements IRoleService {
 
	@Autowired
	private RoleMapper roleMapper;

	@Override
	protected BaseMapper<Role> getBaseMapper() {
		return roleMapper;
	}
	
}