package top.zylsite.cheetah.backstage.model.vo;

import top.zylsite.cheetah.backstage.model.master.Permission;

public class PermissionVO extends Permission {

	private static final long serialVersionUID = 1L;

	private String parentName;
	private Integer level;

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

}
