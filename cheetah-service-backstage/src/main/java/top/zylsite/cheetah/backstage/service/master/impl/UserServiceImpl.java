package top.zylsite.cheetah.backstage.service.master.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
import top.zylsite.cheetah.base.common.enums.YesOrNoEnum;
import top.zylsite.cheetah.base.common.tree.BaseTree;
import top.zylsite.cheetah.base.common.tree.ZTreeNode;

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
			BeanCopier copier = BeanCopier.create(User.class, SessionUser.class, false);
			copier.copy(user, sessionUser, null);
			sessionUser.setSysadmin(StringUtils.defaultIfBlank(user.getcSysAdmin(), YesOrNoEnum.NO.code()).equals(YesOrNoEnum.YES.code()));
			return sessionUser;
		}

		return null;
	}

	@Override
	public void changeLockStatus(Integer id, String locked) {
		userInfoExtendMapper.updateLockStatus(id, locked);
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
	public void setPermissionTree(SessionUser sessionUser) {
		sessionUser.setVcPassword(null);
		// 获取菜单信息
		Example example = new Example(Permission.class);
		example.setOrderByClause("l_sort asc");
		List<Permission> permissions = permissionService.queryList(example);
		if(!sessionUser.isSysadmin()) {
			permissions = this.queryPermissionListByUserId(sessionUser.getId());
		}
		if (!CollectionUtils.isEmpty(permissions)) {
			for (Permission permission : permissions) {
				permission.setParentIds(getParentName(permission.getParentIds()));
			}
		}
		List<? extends BaseTree> tree = permissionService.getPermissionTreeWithPermissions(permissions, null, false);
		if (null != tree && tree.size() > 0) {
			ZTreeNode node = (ZTreeNode) tree.get(0);
			if (null != node) {
				tree = node.getChildren();
			}
		}
		sessionUser.setPermissionTree(tree);
	}

	private String getParentName(String parentIds) {
		if (StringUtils.isNotBlank(parentIds)) {
			String[] pIds = parentIds.split(",");
			if (pIds.length > 1) {
				StringBuilder pName = new StringBuilder();
				String[] pNames = new String[pIds.length - 1];
				Permission permission = null;
				for (int i = 0, length = pIds.length; i < length; i++) {
					if(pIds[i].equals("0")) {
						continue;
					}else {
						permission = permissionService.queryInfoByPrimaryKey(Integer.parseInt(pIds[i]));
						if(null == permission) {
							
						}else {
							pNames[pNames.length - 1] = permission.getVcName();
						}
					}
				}
				for(int j = 0, length = pNames.length ; j < length ; j++) {
					pName.append(",").append(pNames[j]);
				}
				return pName.substring(1);
			}
		}
		return null;
	}

	@Override
	public void saveRoleInfo(Integer userId, Integer[] roles) {
		deletRoleById(userId);
		if (null != roles && roles.length > 0) {
			UserRole userRole = null;
			for (Integer role : roles) {
				userRole = new UserRole();
				userRole.setUserId(userId);
				userRole.setRoleId(role);
				userRoleMapper.insert(userRole);
			}
		}
	}

	@Override
	public void changeStatus(Integer userId, String status) {
		userInfoExtendMapper.updateStatus(userId, status);
	}

	@Override
	public void updatePassword(Integer userId, String password) {
		userInfoExtendMapper.updatePassword(userId, password);
	}
	
	private void deletRoleById(Integer userId) {
		Example example = new Example(UserRole.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", userId);
		userRoleMapper.deleteByExample(example);
	}

}