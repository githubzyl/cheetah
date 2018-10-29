package top.zylsite.cheetah.base.common.tree;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ZTreeNode extends BaseTree {

	private String pId; // 树的节点Id，区别于数据库中保存的数据Id。
	private String name; // 节点名称
	private String iconClass;// 图标
	private boolean checked;// 是否选中
	private List<ZTreeNode> children; // 子节点，可以用递归的方法读取

	public ZTreeNode() {
		this.children = new ArrayList<ZTreeNode>();
	}

	public ZTreeNode(Integer id, String pId) {
		this.id = id;
		this.children = new ArrayList<ZTreeNode>();
	}

	/**
	 * 生成一个节点
	 * 
	 * @param nodeId
	 * @param pId
	 * @param text
	 * @param icon
	 * @param href
	 */
	public ZTreeNode(Integer id, String pId, String name, String iconClass) {
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.iconClass = iconClass;
		this.children = new ArrayList<ZTreeNode>();
	}

	public ZTreeNode createTree(List<ZTreeNode> nodes, ZTreeNode root) {
		if (nodes == null || nodes.size() < 0) {
			return null;
		}
		// 添加一级菜单
		for (ZTreeNode node : nodes) {
			if (StringUtils.isBlank(node.getpId()) || node.getpId().equals("0") || node.getpId().equals("root")) {// 根节点自定义，但是要和pid对应好
				// 向根添加一个节点
				root.getChildren().add(node);
			}
		}
		// 将所有节点添加到多叉树中
		for (ZTreeNode node : root.getChildren()) {
			node.setChildren(getChild(node.getId(), nodes));
		}
		return root;
	}

	private List<ZTreeNode> getChild(int id, List<ZTreeNode> rootMenu) {
		// 子菜单
		List<ZTreeNode> childList = new ArrayList<>();
		for (ZTreeNode node : rootMenu) {
			// 遍历所有节点，将父菜单id与传过来的id比较
			if (StringUtils.isNotBlank(node.getpId())) {
				if (node.getpId().equals(Integer.toString(id))) {
					childList.add(node);
				}
			}
		}
		// 把子菜单的子菜单再循环一遍
		for (ZTreeNode node : childList) {// 没有url子菜单还有子菜单
			// 递归
			node.setChildren(getChild(node.getId(), rootMenu));
		} // 递归退出条件
		if (childList.size() == 0) {
			return null;
		}
		return childList;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ZTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<ZTreeNode> children) {
		this.children = children;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
