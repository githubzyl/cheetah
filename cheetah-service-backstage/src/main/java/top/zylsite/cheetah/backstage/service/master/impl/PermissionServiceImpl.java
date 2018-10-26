package top.zylsite.cheetah.backstage.service.master.impl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.backstage.service.master.IPermissionService;
import top.zylsite.cheetah.backstage.mapper.master.PermissionMapper;
import top.zylsite.cheetah.backstage.model.master.Permission;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements IPermissionService {
 
	@Autowired
	private PermissionMapper permissionMapper;

	@Override
	protected BaseMapper<Permission> getBaseMapper() {
		return permissionMapper;
	}
	
}