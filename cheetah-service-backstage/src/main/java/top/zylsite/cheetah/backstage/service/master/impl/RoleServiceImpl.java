package top.zylsite.cheetah.backstage.service.master.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import top.zylsite.cheetah.backstage.mapper.master.RoleMapper;
import top.zylsite.cheetah.backstage.mapper.master.RolePermissionMapper;
import top.zylsite.cheetah.backstage.mapper.master.extend.RoleExtendMapper;
import top.zylsite.cheetah.backstage.model.master.Role;
import top.zylsite.cheetah.backstage.model.master.RolePermission;
import top.zylsite.cheetah.backstage.service.master.IRoleService;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.base.common.BaseServiceImpl;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements IRoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private RolePermissionMapper rolePermissionMapper;

	@Autowired
	private RoleExtendMapper roleExtendMapper;

	@Override
	protected BaseMapper<Role> getBaseMapper() {
		return roleMapper;
	}

	@Override
	public void saveRoleInfo(Integer roleId, Integer[] permissions) {
		deleteRolePermission(roleId);
		if (null != permissions && permissions.length > 0) {
			RolePermission rolePermission = null;
			for (Integer permission : permissions) {
				rolePermission = new RolePermission();
				rolePermission.setRoleId(roleId);
				rolePermission.setPermissionId(permission);
				rolePermissionMapper.insert(rolePermission);
			}
		}
	}

	@Override
	public List<RolePermission> getRolePermissionsByRoleId(Integer roleId) {
		// 查询所有的角色权限
		Example example = new Example(RolePermission.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("roleId", roleId);
		List<RolePermission> roleMenus = rolePermissionMapper.selectByExample(example);
		return roleMenus;
	}

	@Override
	public void changeStatus(Integer roleId, String status) {
		roleExtendMapper.updateStatus(roleId, status);
	}

	@Override
	public void remove(Integer[] roleIds) {
		if (null != roleIds && roleIds.length > 0) {
			for (Integer roleId : roleIds) {
				deleteRolePermission(roleId);
				roleMapper.deleteByPrimaryKey(roleId);
			}
		}
	}

	public void deleteRolePermission(Integer roleId) {
		Example example = new Example(RolePermission.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("roleId", roleId);
		rolePermissionMapper.deleteByExample(example);
	}

}