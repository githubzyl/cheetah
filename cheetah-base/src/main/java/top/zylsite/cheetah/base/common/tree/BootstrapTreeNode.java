package top.zylsite.cheetah.base.common.tree;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 树节点模型
 * 
 * @author: zhaoyl
 * @since: 2018年3月29日 下午5:00:12
 * @history:
 */
public class BootstrapTreeNode extends BaseTree {

	private String pid;
	private String nodeId; // 树的节点Id，区别于数据库中保存的数据Id。
	private String text; // 节点名称
	private String icon;
	private String href; // 点击节点触发的链接
	private String target;// 是否在新窗口打开（0：否，1：是，默认否）
	private List<BootstrapTreeNode> nodes; // 子节点，可以用递归的方法读取
	private BootstrapTreeNodeState state;// 节点状态
	private List<String> parentNodeIds;// 所有的父节点集合

	public BootstrapTreeNode() {
		this.nodes = new ArrayList<BootstrapTreeNode>();
	}

	public BootstrapTreeNode(Integer id, String nodeId, String pId) {
		this.id = id;
		this.nodeId = nodeId;
		this.pid = pId;
		this.nodes = new ArrayList<BootstrapTreeNode>();
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
	public BootstrapTreeNode(Integer id, String nodeId, String pId, String text, String icon, String href,
			String target) {
		this.id = id;
		this.nodeId = nodeId;
		this.pid = pId;
		this.text = text;
		this.icon = icon;
		this.href = href;
		this.target = target;
		this.nodes = new ArrayList<BootstrapTreeNode>();
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public List<BootstrapTreeNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<BootstrapTreeNode> nodes) {
		this.nodes = nodes;
	}

	public BootstrapTreeNodeState getState() {
		return state;
	}

	public void setState(BootstrapTreeNodeState state) {
		this.state = state;
	}

	public List<String> getParentNodeIds() {
		return parentNodeIds;
	}

	public void setParentNodeIds(List<String> parentNodeIds) {
		this.parentNodeIds = parentNodeIds;
	}

	/**
	 * 生成一颗多叉树，根节点为root
	 * 
	 * @param Nodes
	 *            生成多叉树的节点集合
	 * @return root
	 */
	public BootstrapTreeNode createTree(List<BootstrapTreeNode> nodes, BootstrapTreeNode root) {
		if (nodes == null || nodes.size() < 0) {
			return null;
		}
		// 添加一级菜单
		for (BootstrapTreeNode node : nodes) {
			if (StringUtils.isBlank(node.getPid()) || node.getPid().equals("0") || node.getPid().equals("root")) {// 根节点自定义，但是要和pid对应好
				// 向根添加一个节点
				root.getNodes().add(node);
			}
		}
		// 将所有节点添加到多叉树中
		for (BootstrapTreeNode node : root.getNodes()) {
			node.setNodes(getChild(node.getNodeId(), nodes));
		}
		return root;
	}

	private List<BootstrapTreeNode> getChild(String id, List<BootstrapTreeNode> rootMenu) {
		// 子菜单
		List<BootstrapTreeNode> childList = new ArrayList<>();
		for (BootstrapTreeNode node : rootMenu) {
			// 遍历所有节点，将父菜单id与传过来的id比较
			if (StringUtils.isNotBlank(node.getPid())) {
				if (node.getPid().equals(id)) {
					childList.add(node);
				}
			}
		}
		// 把子菜单的子菜单再循环一遍
		for (BootstrapTreeNode node : childList) {// 没有url子菜单还有子菜单
			// 递归
			node.setNodes(getChild(node.getNodeId(), rootMenu));
		} // 递归退出条件
		if (childList.size() == 0) {
			return null;
		}
		return childList;
	}

	/**
	 * 遍历多叉树
	 * 
	 * @param Node
	 *            多叉树节点
	 * @return
	 */
	public String iteratorTree(BootstrapTreeNode Node) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("\n");
		if (Node != null) {
			for (BootstrapTreeNode index : Node.getNodes()) {
				buffer.append(index.getNodeId() + ",");
				if (index.getNodes() != null && index.getNodes().size() > 0) {
					buffer.append(iteratorTree(index));
				}
			}
		}
		buffer.append("\n");
		return buffer.toString();
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
