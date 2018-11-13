package top.zylsite.cheetah.base.common.tree;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZTreeNode extends BaseTree {

	private Integer pId; // 树的节点Id，区别于数据库中保存的数据Id。
	private String name; // 节点名称
	private String iconClass;// 图标
	private String href; // 点击节点触发的链接
	private String target;// 是否在新窗口打开（0：否，1：是，默认否）
	private boolean checked;// 是否选中
	private List<ZTreeNode> children; // 子节点，可以用递归的方法读取
	private String parentNames;//父节点名称
	private boolean parent;//是否父节点
	private boolean open;//是否展开
	private String resourceType;//节点类型

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
	public ZTreeNode(Integer id, Integer pId, String name, String iconClass) {
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
			if (null == node.getpId() || 0 == node.getpId()) {// 根节点自定义，但是要和pid对应好
				// 向根添加一个节点
				root.getChildren().add(node);
			}
		}
		setIsParent(root);
		// 将所有节点添加到多叉树中
		for (ZTreeNode node : root.getChildren()) {
			node.setChildren(getChild(node.getId(), nodes));
			setIsParent(node);
		}
		return root;
	}

	private List<ZTreeNode> getChild(int id, List<ZTreeNode> nodes) {
		// 子菜单
		List<ZTreeNode> childList = new ArrayList<>();
		for (ZTreeNode node : nodes) {
			// 遍历所有节点，将父菜单id与传过来的id比较
			if (null != node.getpId()) {
				if (node.getpId() == id) {
					childList.add(node);
				}
			}
		}
		// 把子菜单的子菜单再循环一遍
		for (ZTreeNode node : childList) {// 没有url子菜单还有子菜单
			// 递归
			node.setChildren(getChild(node.getId(), nodes));
			setIsParent(node);
		} // 递归退出条件
		if (childList.size() == 0) {
			return null;
		}
		return childList;
	}
	
	private void setIsParent(ZTreeNode node) {
		if(null != node.getChildren() && node.getChildren().size() > 0) {
			node.setParent(true);
		}
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
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

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getParentNames() {
		return parentNames;
	}

	public void setParentNames(String parentNames) {
		this.parentNames = parentNames;
	}

	/*@JsonProperty放在get方法上，代表输出json时使用标注的属性名代替原来的属性名;
	 * 若标注在set方法上，则输入的属性名要与标注的属性名一致
	 */
	@JsonProperty("isParent")
	public boolean isParent() {
		return parent;
	}

	public void setParent(boolean parent) {
		this.parent = parent;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

}
