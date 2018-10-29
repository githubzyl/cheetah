package top.zylsite.cheetah.backstage.service.master;
 
import java.util.List;

import top.zylsite.cheetah.backstage.model.master.Permission;
import top.zylsite.cheetah.backstage.model.master.RolePermission;
import top.zylsite.cheetah.base.common.BaseService;
import top.zylsite.cheetah.base.common.tree.BaseTree;

public interface IPermissionService extends BaseService<Permission>{

	/**
	 * 根据菜单列表获取菜单树
	 * 
	 * @param permissionList
	 * @param rolePermissions
	 * @param containButton
	 * @param ztree
	 * @return
	 * @create: 2018年4月3日 下午4:28:30 zhaoyl
	 * @history:
	 */
	List<? extends BaseTree> getPermissionTreeWithPermissions(List<Permission> permissionList,
			List<RolePermission> rolePermissions, boolean containButton, boolean ztree);
 
}