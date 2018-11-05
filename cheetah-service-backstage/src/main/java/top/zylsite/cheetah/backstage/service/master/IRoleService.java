package top.zylsite.cheetah.backstage.service.master;
 
import java.util.List;

import top.zylsite.cheetah.backstage.model.master.Role;
import top.zylsite.cheetah.backstage.model.master.RolePermission;
import top.zylsite.cheetah.base.common.BaseService;

public interface IRoleService extends BaseService<Role>{
 
	void saveRoleInfo(Integer roleId, Integer[] permissions);

	List<RolePermission> getRolePermissionsByRoleId(Integer roleId);

	void changeStatus(Integer roleId, String status);
	
	void remove(Integer[] roleIds);
	
}