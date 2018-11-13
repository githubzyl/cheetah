package top.zylsite.cheetah.backstage.service.master.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import tk.mybatis.mapper.entity.Example;
import top.zylsite.cheetah.backstage.mapper.master.PermissionMapper;
import top.zylsite.cheetah.backstage.mapper.master.extend.PermissionExtendMapper;
import top.zylsite.cheetah.backstage.model.master.Permission;
import top.zylsite.cheetah.backstage.model.master.RolePermission;
import top.zylsite.cheetah.backstage.model.vo.PermissionVO;
import top.zylsite.cheetah.backstage.service.common.enums.ResourceTypeEnum;
import top.zylsite.cheetah.backstage.service.master.IPermissionService;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.tree.BaseTree;
import top.zylsite.cheetah.base.common.tree.ZTreeNode;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements IPermissionService {

	private final static String ROOT_PERMISSION_NAME = "根权限";

	@Autowired
	private PermissionMapper permissionMapper;

	@Autowired
	private PermissionExtendMapper permissionMapperExtend;

	@Override
	protected BaseMapper<Permission> getBaseMapper() {
		return permissionMapper;
	}

	@Override
	public List<? extends BaseTree> getPermissionTreeWithPermissions(List<Permission> permissionList,
			List<RolePermission> rolePermissions, boolean containButton) {
		return getZTree(permissionList, rolePermissions, containButton);
	}

	@Override
	public List<Permission> getPermissionByParentId(int parentId) {
		Example example = new Example(Permission.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("parentId", parentId);
		example.orderBy("lSort").asc();
		return permissionMapper.selectByExample(example);
	}

	@Override
	public PermissionVO queryById(Integer id) {
		PermissionVO permissionVO = permissionMapperExtend.selectById(id);
		if (null != permissionVO && StringUtils.isBlank(permissionVO.getParentName())) {
			permissionVO.setParentName(ROOT_PERMISSION_NAME);
		}
		return permissionVO;
	}

	@Override
	public void changeStatus(Integer permissionId, String status) {
		permissionMapperExtend.updateStatus(permissionId, status);
	}

	@Override
	public BaseTree createNode(Permission permission, boolean async) {
		ZTreeNode node = new ZTreeNode();
		node.setId(permission.getId());
		node.setIconClass(permission.getVcIcon());
		node.setpId(permission.getParentId());
		node.setName(permission.getVcName());
		node.setParentNames(permission.getParentIds());
		node.setHref(permission.getVcUrl());
		node.setTarget((StringUtils.isBlank(permission.getcTargetBlank()) ? "0" : permission.getcTargetBlank()));
		if (async) {
			node.setParent(hasChildren(node.getId()));
		}
		node.setResourceType(permission.getcResourceType());
		return node;
	}
	
	@Override
	public ZTreeNode getRootNode() {
		return new ZTreeNode(0, 0, ROOT_PERMISSION_NAME, "iconfont icon-xitong");// 根节点自定义，但是要和pid对应好
	}

	private List<ZTreeNode> getZTree(List<Permission> permissionList, List<RolePermission> rolePermissions,
			boolean containButton) {
		// 生成菜单树
		List<ZTreeNode> list = new ArrayList<>(1);
		if (null == permissionList || permissionList.size() <= 0) {
			return list;
		}
		List<ZTreeNode> nodes = new ArrayList<>();// 把所有资源转换成树模型的节点集合，此容器用于保存所有节点
		ZTreeNode node = null;
		for (Permission permission : permissionList) {
			if (!containButton) {
				if (!ResourceTypeEnum.BUTTON.getCode().equals(permission.getcResourceType())) {
					node = (ZTreeNode) createNode(permission, false);
					node.setChecked(getChecked(node, rolePermissions));
					nodes.add(node);// 添加到节点容
				}
			} else {
				node = (ZTreeNode) createNode(permission, false);
				node.setChecked(getChecked(node, rolePermissions));
				nodes.add(node);// 添加到节点容
			}
		}
		ZTreeNode root = getRootNode();
		// 只要有一个被选中，根节点就被选中
		if (rolePermissions != null && rolePermissions.size() > 0) {
			root.setChecked(true);
		}
		ZTreeNode tree = root.createTree(nodes, root);
		list.add(tree);
		return list;
	}

	private boolean getChecked(BaseTree node, List<RolePermission> rolePermissions) {
		if (null != rolePermissions && rolePermissions.size() > 0) {
			for (RolePermission rolePermission : rolePermissions) {
				if (node.getId() == rolePermission.getPermissionId()) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean hasChildren(int id) {
		List<Permission> list = this.getPermissionByParentId(id);
		return CollectionUtils.isEmpty(list) ? false : true;
	}

}