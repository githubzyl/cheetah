package top.zylsite.cheetah.backstage.model.master;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_user_login_log")
public class UserLoginLog implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "l_user_id")
    private Integer lUserId;

    /**
     * 用户名
     */
    @Column(name = "vc_user_name")
    private String vcUserName;

    /**
     * 登录时间
     */
    @Column(name = "d_login_time")
    private Date dLoginTime;

    /**
     * 退出时间
     */
    @Column(name = "d_logout_time")
    private Date dLogoutTime;

    /**
     * 登录类型
     */
    @Column(name = "c_login_type")
    private String cLoginType;

    /**
     * 访问IP
     */
    @Column(name = "vc_ip")
    private String vcIp;

    /**
     * IP归属地
     */
    @Column(name = "vc_location")
    private String vcLocation;

    /**
     * 操作设备类型
     */
    @Column(name = "vc_device_type")
    private String vcDeviceType;

    /**
     * 浏览器类型
     */
    @Column(name = "vc_browser_type")
    private String vcBrowserType;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户ID
     *
     * @return l_user_id - 用户ID
     */
    public Integer getlUserId() {
        return lUserId;
    }

    /**
     * 设置用户ID
     *
     * @param lUserId 用户ID
     */
    public void setlUserId(Integer lUserId) {
        this.lUserId = lUserId;
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
     * 获取登录时间
     *
     * @return d_login_time - 登录时间
     */
    public Date getdLoginTime() {
        return dLoginTime;
    }

    /**
     * 设置登录时间
     *
     * @param dLoginTime 登录时间
     */
    public void setdLoginTime(Date dLoginTime) {
        this.dLoginTime = dLoginTime;
    }

    /**
     * 获取退出时间
     *
     * @return d_logout_time - 退出时间
     */
    public Date getdLogoutTime() {
        return dLogoutTime;
    }

    /**
     * 设置退出时间
     *
     * @param dLogoutTime 退出时间
     */
    public void setdLogoutTime(Date dLogoutTime) {
        this.dLogoutTime = dLogoutTime;
    }

    /**
     * 获取登录类型
     *
     * @return c_login_type - 登录类型
     */
    public String getcLoginType() {
        return cLoginType;
    }

    /**
     * 设置登录类型
     *
     * @param cLoginType 登录类型
     */
    public void setcLoginType(String cLoginType) {
        this.cLoginType = cLoginType == null ? null : cLoginType.trim();
    }

    /**
     * 获取访问IP
     *
     * @return vc_ip - 访问IP
     */
    public String getVcIp() {
        return vcIp;
    }

    /**
     * 设置访问IP
     *
     * @param vcIp 访问IP
     */
    public void setVcIp(String vcIp) {
        this.vcIp = vcIp == null ? null : vcIp.trim();
    }

    /**
     * 获取IP归属地
     *
     * @return vc_location - IP归属地
     */
    public String getVcLocation() {
        return vcLocation;
    }

    /**
     * 设置IP归属地
     *
     * @param vcLocation IP归属地
     */
    public void setVcLocation(String vcLocation) {
        this.vcLocation = vcLocation == null ? null : vcLocation.trim();
    }

    /**
     * 获取操作设备类型
     *
     * @return vc_device_type - 操作设备类型
     */
    public String getVcDeviceType() {
        return vcDeviceType;
    }

    /**
     * 设置操作设备类型
     *
     * @param vcDeviceType 操作设备类型
     */
    public void setVcDeviceType(String vcDeviceType) {
        this.vcDeviceType = vcDeviceType == null ? null : vcDeviceType.trim();
    }

    /**
     * 获取浏览器类型
     *
     * @return vc_browser_type - 浏览器类型
     */
    public String getVcBrowserType() {
        return vcBrowserType;
    }

    /**
     * 设置浏览器类型
     *
     * @param vcBrowserType 浏览器类型
     */
    public void setVcBrowserType(String vcBrowserType) {
        this.vcBrowserType = vcBrowserType == null ? null : vcBrowserType.trim();
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
        UserLoginLog other = (UserLoginLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getlUserId() == null ? other.getlUserId() == null : this.getlUserId().equals(other.getlUserId()))
            && (this.getVcUserName() == null ? other.getVcUserName() == null : this.getVcUserName().equals(other.getVcUserName()))
            && (this.getdLoginTime() == null ? other.getdLoginTime() == null : this.getdLoginTime().equals(other.getdLoginTime()))
            && (this.getdLogoutTime() == null ? other.getdLogoutTime() == null : this.getdLogoutTime().equals(other.getdLogoutTime()))
            && (this.getcLoginType() == null ? other.getcLoginType() == null : this.getcLoginType().equals(other.getcLoginType()))
            && (this.getVcIp() == null ? other.getVcIp() == null : this.getVcIp().equals(other.getVcIp()))
            && (this.getVcLocation() == null ? other.getVcLocation() == null : this.getVcLocation().equals(other.getVcLocation()))
            && (this.getVcDeviceType() == null ? other.getVcDeviceType() == null : this.getVcDeviceType().equals(other.getVcDeviceType()))
            && (this.getVcBrowserType() == null ? other.getVcBrowserType() == null : this.getVcBrowserType().equals(other.getVcBrowserType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getlUserId() == null) ? 0 : getlUserId().hashCode());
        result = prime * result + ((getVcUserName() == null) ? 0 : getVcUserName().hashCode());
        result = prime * result + ((getdLoginTime() == null) ? 0 : getdLoginTime().hashCode());
        result = prime * result + ((getdLogoutTime() == null) ? 0 : getdLogoutTime().hashCode());
        result = prime * result + ((getcLoginType() == null) ? 0 : getcLoginType().hashCode());
        result = prime * result + ((getVcIp() == null) ? 0 : getVcIp().hashCode());
        result = prime * result + ((getVcLocation() == null) ? 0 : getVcLocation().hashCode());
        result = prime * result + ((getVcDeviceType() == null) ? 0 : getVcDeviceType().hashCode());
        result = prime * result + ((getVcBrowserType() == null) ? 0 : getVcBrowserType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", lUserId=").append(lUserId);
        sb.append(", vcUserName=").append(vcUserName);
        sb.append(", dLoginTime=").append(dLoginTime);
        sb.append(", dLogoutTime=").append(dLogoutTime);
        sb.append(", cLoginType=").append(cLoginType);
        sb.append(", vcIp=").append(vcIp);
        sb.append(", vcLocation=").append(vcLocation);
        sb.append(", vcDeviceType=").append(vcDeviceType);
        sb.append(", vcBrowserType=").append(vcBrowserType);
        sb.append("]");
        return sb.toString();
    }
}