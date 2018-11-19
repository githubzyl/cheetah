package top.zylsite.cheetah.backstage.model.vo;

import top.zylsite.cheetah.backstage.model.master.User;

public class UserVO extends User {

	private static final long serialVersionUID = 1L;

	private String deptName;

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Override
	public String toString() {
		return "UserVO [deptName=" + deptName + ", getId()=" + getId() + ", getVcUserName()=" + getVcUserName()
				+ ", getVcPassword()=" + getVcPassword() + ", getVcRealName()=" + getVcRealName() + ", getVcMobile()="
				+ getVcMobile() + ", getVcEmail()=" + getVcEmail() + ", getcStatus()=" + getcStatus()
				+ ", getcLockStatus()=" + getcLockStatus() + ", getcSysAdmin()=" + getcSysAdmin() + ", getcGender()="
				+ getcGender() + ", getVcPhoto()=" + getVcPhoto() + ", getlDepartmentId()=" + getlDepartmentId()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + ", getClass()=" + getClass()
				+ "]";
	}

}
