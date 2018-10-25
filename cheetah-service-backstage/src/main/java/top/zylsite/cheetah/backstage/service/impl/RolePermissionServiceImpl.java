package top.zylsite.cheetah.backstage.service.impl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.backstage.service.IRolePermissionService;
import top.zylsite.cheetah.backstage.mapper.RolePermissionMapper;
import top.zylsite.cheetah.backstage.model.RolePermission;

@Service
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermission> implements IRolePermissionService {
 
	@Autowired
	private RolePermissionMapper rolePermissionMapper;

	@Override
	protected BaseMapper<RolePermission> getBaseMapper() {
		return rolePermissionMapper;
	}
	
}