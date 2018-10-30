package top.zylsite.cheetah.backstage.model.dto;

import java.util.List;

import top.zylsite.cheetah.backstage.model.master.User;
import top.zylsite.cheetah.base.common.tree.BaseTree;

/**
 * Description: 存放在session中的用户信息
 * @author jason
 * 2018年10月26日
 * @version 1.0
 */
public class SessionUser extends User {

	private static final long serialVersionUID = -6165247803503895708L;
	
	private Integer loginLogId;//登录日志ID
	
	private List<? extends BaseTree> menuTree;

	public List<? extends BaseTree> getMenuTree() {
		return menuTree;
	}

	public void setMenuTree(List<? extends BaseTree> menuTree) {
		this.menuTree = menuTree;
	}

	public Integer getLoginLogId() {
		return loginLogId;
	}

	public void setLoginLogId(Integer loginLogId) {
		this.loginLogId = loginLogId;
	}

}
