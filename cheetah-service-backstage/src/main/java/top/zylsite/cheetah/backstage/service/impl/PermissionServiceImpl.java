package top.zylsite.cheetah.backstage.service.impl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.backstage.service.IPermissionService;
import top.zylsite.cheetah.backstage.mapper.PermissionMapper;
import top.zylsite.cheetah.backstage.model.Permission;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements IPermissionService {
 
	@Autowired
	private PermissionMapper permissionMapper;

	@Override
	protected BaseMapper<Permission> getBaseMapper() {
		return permissionMapper;
	}
	
}