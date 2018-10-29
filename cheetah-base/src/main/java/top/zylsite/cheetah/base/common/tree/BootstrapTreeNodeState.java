package top.zylsite.cheetah.base.common.tree;

/**
 * 树节点状态
 * 
 * @author: zhaoyl
 * @since: 2018年3月30日 上午9:47:13
 * @history:
 */
public class BootstrapTreeNodeState {

	private boolean checked;

	public BootstrapTreeNodeState(boolean checked) {
		this.checked = checked;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
