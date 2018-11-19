package top.zylsite.cheetah.backstage.service.master;

import java.util.List;

import com.github.pagehelper.PageInfo;

import top.zylsite.cheetah.backstage.model.dto.SessionUser;
import top.zylsite.cheetah.backstage.model.master.Permission;
import top.zylsite.cheetah.backstage.model.master.User;
import top.zylsite.cheetah.backstage.model.master.UserRole;
import top.zylsite.cheetah.backstage.model.vo.UserVO;
import top.zylsite.cheetah.base.common.BaseService;
import top.zylsite.cheetah.base.common.QueryParameter;

public interface IUserService extends BaseService<User> {

	SessionUser findByUserName(String username);

	void changeLockStatus(Integer id, String locked);

	List<UserRole> queryRolesByUserId(Integer id);

	List<Permission> queryPermissionListByUserId(Integer id);

	void setPermissionTree(SessionUser sessionUser);

	void saveRoleInfo(Integer userId, Integer[] roles);

	void changeStatus(Integer userId, String status);

	void updatePassword(Integer userId, String password);

	/**
	 * 根据用户名，手机号，邮箱查询用户
	 * 
	 * @param account
	 * @return
	 */
	SessionUser findUser(String account);

	UserVO queryById(Integer id);

	PageInfo<UserVO> queryForPage(QueryParameter queryParameter, UserVO userVO);

}