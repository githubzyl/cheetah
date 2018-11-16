package top.zylsite.cheetah.backstage.model.master;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "t_user")
public class User implements Serializable {
    /**
     * 用户id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 用户名
     */
    @Column(name = "vc_user_name")
    private String vcUserName;

    /**
     * 登陆密码
     */
    @Column(name = "vc_password")
    private String vcPassword;

    /**
     * 真实姓名
     */
    @Column(name = "vc_real_name")
    private String vcRealName;

    /**
     * 手机号码
     */
    @Column(name = "vc_mobile")
    private String vcMobile;

    /**
     * 邮箱地址
     */
    @Column(name = "vc_email")
    private String vcEmail;

    /**
     * 用户状态(0：正常，1：禁用，默认正常)
     */
    @Column(name = "c_status")
    private String cStatus;

    /**
     * 锁定状态0：非锁定，1：锁定，默认非锁定)
     */
    @Column(name = "c_lock_status")
    private String cLockStatus;

    /**
     * 是否系统管理员（0：否，1：是）
     */
    @Column(name = "c_sys_admin")
    private String cSysAdmin;

    /**
     * 性别（男：'0',女：'1'）
     */
    @Column(name = "c_gender")
    private String cGender;

    /**
     * 头像
     */
    @Column(name = "vc_photo")
    private String vcPhoto;

    /**
     * 部门ID
     */
    @Column(name = "l_department_id")
    private Integer lDepartmentId;

    private static final long serialVersionUID = 1L;

    /**
     * 获取用户id
     *
     * @return id - 用户id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置用户id
     *
     * @param id 用户id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return vc_user_name - 用户名
     */
    public String getVcUserName() {
        return vcUserName;
    }

    /**
     * 设置用户名
     *
     * @param vcUserName 用户名
     */
    public void setVcUserName(String vcUserName) {
        this.vcUserName = vcUserName == null ? null : vcUserName.trim();
    }

    /**
     * 获取登陆密码
     *
     * @return vc_password - 登陆密码
     */
    public String getVcPassword() {
        return vcPassword;
    }

    /**
     * 设置登陆密码
     *
     * @param vcPassword 登陆密码
     */
    public void setVcPassword(String vcPassword) {
        this.vcPassword = vcPassword == null ? null : vcPassword.trim();
    }

    /**
     * 获取真实姓名
     *
     * @return vc_real_name - 真实姓名
     */
    public String getVcRealName() {
        return vcRealName;
    }

    /**
     * 设置真实姓名
     *
     * @param vcRealName 真实姓名
     */
    public void setVcRealName(String vcRealName) {
        this.vcRealName = vcRealName == null ? null : vcRealName.trim();
    }

    /**
     * 获取手机号码
     *
     * @return vc_mobile - 手机号码
     */
    public String getVcMobile() {
        return vcMobile;
    }

    /**
     * 设置手机号码
     *
     * @param vcMobile 手机号码
     */
    public void setVcMobile(String vcMobile) {
        this.vcMobile = vcMobile == null ? null : vcMobile.trim();
    }

    /**
     * 获取邮箱地址
     *
     * @return vc_email - 邮箱地址
     */
    public String getVcEmail() {
        return vcEmail;
    }

    /**
     * 设置邮箱地址
     *
     * @param vcEmail 邮箱地址
     */
    public void setVcEmail(String vcEmail) {
        this.vcEmail = vcEmail == null ? null : vcEmail.trim();
    }

    /**
     * 获取用户状态(0：正常，1：禁用，默认正常)
     *
     * @return c_status - 用户状态(0：正常，1：禁用，默认正常)
     */
    public String getcStatus() {
        return cStatus;
    }

    /**
     * 设置用户状态(0：正常，1：禁用，默认正常)
     *
     * @param cStatus 用户状态(0：正常，1：禁用，默认正常)
     */
    public void setcStatus(String cStatus) {
        this.cStatus = cStatus == null ? null : cStatus.trim();
    }

    /**
     * 获取锁定状态0：非锁定，1：锁定，默认非锁定)
     *
     * @return c_lock_status - 锁定状态0：非锁定，1：锁定，默认非锁定)
     */
    public String getcLockStatus() {
        return cLockStatus;
    }

    /**
     * 设置锁定状态0：非锁定，1：锁定，默认非锁定)
     *
     * @param cLockStatus 锁定状态0：非锁定，1：锁定，默认非锁定)
     */
    public void setcLockStatus(String cLockStatus) {
        this.cLockStatus = cLockStatus == null ? null : cLockStatus.trim();
    }

    /**
     * 获取是否系统管理员（0：否，1：是）
     *
     * @return c_sys_admin - 是否系统管理员（0：否，1：是）
     */
    public String getcSysAdmin() {
        return cSysAdmin;
    }

    /**
     * 设置是否系统管理员（0：否，1：是）
     *
     * @param cSysAdmin 是否系统管理员（0：否，1：是）
     */
    public void setcSysAdmin(String cSysAdmin) {
        this.cSysAdmin = cSysAdmin == null ? null : cSysAdmin.trim();
    }

    /**
     * 获取性别（男：'0',女：'1'）
     *
     * @return c_gender - 性别（男：'0',女：'1'）
     */
    public String getcGender() {
        return cGender;
    }

    /**
     * 设置性别（男：'0',女：'1'）
     *
     * @param cGender 性别（男：'0',女：'1'）
     */
    public void setcGender(String cGender) {
        this.cGender = cGender == null ? null : cGender.trim();
    }

    /**
     * 获取头像
     *
     * @return vc_photo - 头像
     */
    public String getVcPhoto() {
        return vcPhoto;
    }

    /**
     * 设置头像
     *
     * @param vcPhoto 头像
     */
    public void setVcPhoto(String vcPhoto) {
        this.vcPhoto = vcPhoto == null ? null : vcPhoto.trim();
    }

    /**
     * 获取部门ID
     *
     * @return l_department_id - 部门ID
     */
    public Integer getlDepartmentId() {
        return lDepartmentId;
    }

    /**
     * 设置部门ID
     *
     * @param lDepartmentId 部门ID
     */
    public void setlDepartmentId(Integer lDepartmentId) {
        this.lDepartmentId = lDepartmentId;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getVcUserName() == null ? other.getVcUserName() == null : this.getVcUserName().equals(other.getVcUserName()))
            && (this.getVcPassword() == null ? other.getVcPassword() == null : this.getVcPassword().equals(other.getVcPassword()))
            && (this.getVcRealName() == null ? other.getVcRealName() == null : this.getVcRealName().equals(other.getVcRealName()))
            && (this.getVcMobile() == null ? other.getVcMobile() == null : this.getVcMobile().equals(other.getVcMobile()))
            && (this.getVcEmail() == null ? other.getVcEmail() == null : this.getVcEmail().equals(other.getVcEmail()))
            && (this.getcStatus() == null ? other.getcStatus() == null : this.getcStatus().equals(other.getcStatus()))
            && (this.getcLockStatus() == null ? other.getcLockStatus() == null : this.getcLockStatus().equals(other.getcLockStatus()))
            && (this.getcSysAdmin() == null ? other.getcSysAdmin() == null : this.getcSysAdmin().equals(other.getcSysAdmin()))
            && (this.getcGender() == null ? other.getcGender() == null : this.getcGender().equals(other.getcGender()))
            && (this.getVcPhoto() == null ? other.getVcPhoto() == null : this.getVcPhoto().equals(other.getVcPhoto()))
            && (this.getlDepartmentId() == null ? other.getlDepartmentId() == null : this.getlDepartmentId().equals(other.getlDepartmentId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getVcUserName() == null) ? 0 : getVcUserName().hashCode());
        result = prime * result + ((getVcPassword() == null) ? 0 : getVcPassword().hashCode());
        result = prime * result + ((getVcRealName() == null) ? 0 : getVcRealName().hashCode());
        result = prime * result + ((getVcMobile() == null) ? 0 : getVcMobile().hashCode());
        result = prime * result + ((getVcEmail() == null) ? 0 : getVcEmail().hashCode());
        result = prime * result + ((getcStatus() == null) ? 0 : getcStatus().hashCode());
        result = prime * result + ((getcLockStatus() == null) ? 0 : getcLockStatus().hashCode());
        result = prime * result + ((getcSysAdmin() == null) ? 0 : getcSysAdmin().hashCode());
        result = prime * result + ((getcGender() == null) ? 0 : getcGender().hashCode());
        result = prime * result + ((getVcPhoto() == null) ? 0 : getVcPhoto().hashCode());
        result = prime * result + ((getlDepartmentId() == null) ? 0 : getlDepartmentId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", vcUserName=").append(vcUserName);
        sb.append(", vcPassword=").append(vcPassword);
        sb.append(", vcRealName=").append(vcRealName);
        sb.append(", vcMobile=").append(vcMobile);
        sb.append(", vcEmail=").append(vcEmail);
        sb.append(", cStatus=").append(cStatus);
        sb.append(", cLockStatus=").append(cLockStatus);
        sb.append(", cSysAdmin=").append(cSysAdmin);
        sb.append(", cGender=").append(cGender);
        sb.append(", vcPhoto=").append(vcPhoto);
        sb.append(", lDepartmentId=").append(lDepartmentId);
        sb.append("]");
        return sb.toString();
    }
}