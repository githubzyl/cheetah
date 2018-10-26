package top.zylsite.cheetah.backstage.service.master.impl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.backstage.service.master.IRolePermissionService;
import top.zylsite.cheetah.backstage.mapper.master.RolePermissionMapper;
import top.zylsite.cheetah.backstage.model.master.RolePermission;

@Service
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermission> implements IRolePermissionService {
 
	@Autowired
	private RolePermissionMapper rolePermissionMapper;

	@Override
	protected BaseMapper<RolePermission> getBaseMapper() {
		return rolePermissionMapper;
	}
	
}