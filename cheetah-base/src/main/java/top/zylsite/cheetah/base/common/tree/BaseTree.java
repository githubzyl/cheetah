package top.zylsite.cheetah.base.common.tree;

import java.io.Serializable;

public class BaseTree implements Serializable{

	private static final long serialVersionUID = 1L;
	protected Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
