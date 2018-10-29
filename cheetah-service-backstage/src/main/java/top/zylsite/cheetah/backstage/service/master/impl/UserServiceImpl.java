package top.zylsite.cheetah.backstage.service.master.impl;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import top.zylsite.cheetah.backstage.mapper.master.UserMapper;
import top.zylsite.cheetah.backstage.mapper.master.UserRoleMapper;
import top.zylsite.cheetah.backstage.mapper.master.extend.UserInfoExtendMapper;
import top.zylsite.cheetah.backstage.model.dto.SessionUser;
import top.zylsite.cheetah.backstage.model.master.Permission;
import top.zylsite.cheetah.backstage.model.master.User;
import top.zylsite.cheetah.backstage.model.master.UserRole;
import top.zylsite.cheetah.backstage.service.master.IPermissionService;
import top.zylsite.cheetah.backstage.service.master.IUserService;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.tree.BaseTree;
import top.zylsite.cheetah.base.common.tree.BootstrapTreeNode;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {
 
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Autowired
	private UserInfoExtendMapper userInfoExtendMapper;
	
	@Autowired
	private IPermissionService permissionService;

	@Override
	protected BaseMapper<User> getBaseMapper() {
		return userMapper;
	}

	@Override
	public SessionUser findByUserName(String username) {
		Example example = new Example(User.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("vcUserName", username);
		List<User> list = userMapper.selectByExample(example);

		if (null != list && !list.isEmpty()) {
			User user = list.get(0);
			SessionUser sessionUser = new SessionUser();
			sessionUser.setId(user.getId());
			sessionUser.setVcEmail(user.getVcEmail());
			sessionUser.setVcUserName(user.getVcUserName());
			sessionUser.setVcRealName(user.getVcRealName());
			sessionUser.setVcMobile(user.getVcMobile());
			sessionUser.setVcPassword(user.getVcPassword());
			return sessionUser;
		}

		return null;
	}

	@Override
	public void changeLockStatus(Integer id, String locked) {
		userInfoExtendMapper.updateStatus(id, locked);
	}

	@Override
	public List<UserRole> queryRolesByUserId(Integer userId) {
		Example example = new Example(UserRole.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", userId);
		return userRoleMapper.selectByExample(example);
	}

	@Override
	public List<Permission> queryPermissionListByUserId(Integer id) {
		if (null == id) {
			return null;
		}
		return userInfoExtendMapper.selectPermissionListByUserId(id);
	}

	@Override
	public void setMenuTree(SessionUser sessionUser) {
		sessionUser.setVcPassword(null);
		// 获取菜单信息
		Example example = new Example(Permission.class);
		example.setOrderByClause("l_sort asc");
		List<Permission> permissions = permissionService.queryList(example);
		if (!"sysadmin".equals(sessionUser.getVcUserName())) {
			permissions = this.queryPermissionListByUserId(sessionUser.getId());
		}
		List<? extends BaseTree> tree = permissionService.getPermissionTreeWithPermissions(permissions, null, false,
				false);
		if (null != tree && tree.size() > 0) {
			BootstrapTreeNode node = (BootstrapTreeNode) tree.get(0);
			if (null != node) {
				tree = node.getNodes();
			}
		}
		sessionUser.setMenuTree(tree);
	}
	
}