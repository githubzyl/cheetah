package top.zylsite.cheetah.backstage.service.master;
 
import java.util.List;

import top.zylsite.cheetah.backstage.model.master.Permission;
import top.zylsite.cheetah.backstage.model.master.RolePermission;
import top.zylsite.cheetah.backstage.model.vo.PermissionVO;
import top.zylsite.cheetah.base.common.BaseService;
import top.zylsite.cheetah.base.common.tree.BaseTree;
import top.zylsite.cheetah.base.common.tree.ZTreeNode;

public interface IPermissionService extends BaseService<Permission>{

	List<? extends BaseTree> getPermissionTreeWithPermissions(List<Permission> permissionList,
			List<RolePermission> rolePermissions, boolean containButton);

	List<Permission> getPermissionByParentId(int parentId);

	PermissionVO queryById(Integer id);

	void changeStatus(Integer permissionId, String status);
	
	BaseTree createNode(Permission permission, boolean async);
	
	ZTreeNode getRootNode(); 
	
}