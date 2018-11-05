package top.zylsite.cheetah.backstage.service.master;
 
import top.zylsite.cheetah.base.common.BaseService;

import java.util.List;

import top.zylsite.cheetah.backstage.model.dto.SessionUser;
import top.zylsite.cheetah.backstage.model.master.Permission;
import top.zylsite.cheetah.backstage.model.master.User;
import top.zylsite.cheetah.backstage.model.master.UserRole;

public interface IUserService extends BaseService<User>{

	SessionUser findByUserName(String username);

	void changeLockStatus(Integer id, String locked);

	List<UserRole> queryRolesByUserId(Integer id);

	List<Permission> queryPermissionListByUserId(Integer id);
	
	void setPermissionTree(SessionUser sessionUser);
	
	void saveRoleInfo(Integer userId, Integer[] roles);
	
	void changeStatus(Integer userId, String status);
	
	void updatePassword(Integer userId, String password);
 
}