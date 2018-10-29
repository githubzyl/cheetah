package top.zylsite.cheetah.backstage.service.master.impl;
 
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.zylsite.cheetah.backstage.mapper.master.PermissionMapper;
import top.zylsite.cheetah.backstage.model.master.Permission;
import top.zylsite.cheetah.backstage.model.master.RolePermission;
import top.zylsite.cheetah.backstage.service.master.IPermissionService;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.tree.BaseTree;
import top.zylsite.cheetah.base.common.tree.BootstrapTreeNode;
import top.zylsite.cheetah.base.common.tree.BootstrapTreeNodeState;
import top.zylsite.cheetah.base.common.tree.ZTreeNode;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements IPermissionService {
 
	@Autowired
	private PermissionMapper permissionMapper;

	@Override
	protected BaseMapper<Permission> getBaseMapper() {
		return permissionMapper;
	}

	@Override
	public List<? extends BaseTree> getPermissionTreeWithPermissions(List<Permission> permissionList,
			List<RolePermission> rolePermissions, boolean containButton, boolean ztree) {
		if (ztree) {
			return getZTree(permissionList, rolePermissions, containButton);
		}
		return getBootstrapTree(permissionList, rolePermissions, containButton);
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
				if (!"2".equals(permission.getcResourceType())) {
					node = (ZTreeNode) createNode(permission, true);
					node.setChecked(getChecked(node, rolePermissions));
					nodes.add(node);// 添加到节点容
				}
			} else {
				node = (ZTreeNode) createNode(permission, true);
				node.setChecked(getChecked(node, rolePermissions));
				nodes.add(node);// 添加到节点容
			}
		}
		ZTreeNode root = new ZTreeNode(0, "0", "根权限", "iconfont icon-xitong");// 根节点自定义，但是要和pid对应好
		// 只要有一个被选中，根节点就被选中
		if (rolePermissions != null && rolePermissions.size() > 0) {
			root.setChecked(true);
		}
		ZTreeNode tree = root.createTree(nodes, root);
		list.add(tree);
		return list;
	}

	private List<BootstrapTreeNode> getBootstrapTree(List<Permission> permissionList,
			List<RolePermission> rolePermissions, boolean containButton) {
		List<BootstrapTreeNode> list = new ArrayList<>(1);
		if (null == permissionList || permissionList.size() <= 0) {
			return list;
		}
		List<BootstrapTreeNode> nodes = new ArrayList<>();// 把所有资源转换成树模型的节点集合，此容器用于保存所有节点
		BootstrapTreeNode node = null;
		BootstrapTreeNodeState state = null;
		for (Permission permission : permissionList) {
			state = new BootstrapTreeNodeState(getChecked(node, rolePermissions));
			if (!containButton) {
				if (!"2".equals(permission.getcResourceType())) {
					node = (BootstrapTreeNode) createNode(permission, false);
					node.setState(state);
					nodes.add(node);// 添加到节点容
				}
			} else {
				node = (BootstrapTreeNode) createNode(permission, false);
				node.setState(state);
				nodes.add(node);// 添加到节点容
			}
		}
		BootstrapTreeNode root = new BootstrapTreeNode(0, "0", "", "根权限", "iconfont icon-xitong", "", "0");// 根节点自定义，但是要和pid对应好
		// 只要有一个被选中，根节点就被选中
		if (rolePermissions != null && rolePermissions.size() > 0) {
			root.setState(new BootstrapTreeNodeState(true));
		}
		BootstrapTreeNode tree = root.createTree(nodes, root);
		list.add(tree);
		return list;
	}

	private BaseTree createNode(Permission permission, boolean ztree) {
		if (ztree) {
			ZTreeNode node = new ZTreeNode();
			node.setId(permission.getId());
			node.setIconClass(permission.getVcIcon());
			node.setpId(permission.getParentId().toString());
			node.setName(permission.getVcName());
			return node;
		}
		BootstrapTreeNode node = new BootstrapTreeNode();
		node.setId(permission.getId());
		node.setHref(permission.getVcUrl());
		node.setIcon(permission.getVcIcon());
		node.setNodeId(permission.getId().toString());
		node.setPid(permission.getParentId().toString());
		node.setText(permission.getVcName());
		node.setTarget((StringUtils.isBlank(permission.getcTargetBlank()) ? "0" : permission.getcTargetBlank()));
		return node;
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
	
}