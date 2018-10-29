package top.zylsite.cheetah.backstage.model.master;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_user_login_log")
public class UserLoginLog implements Serializable {
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "l_user_id")
    private Integer lUserId;

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
     * 访问IP
     */
    @Column(name = "vc_ip")
    private String vcIp;

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
            && (this.getdLoginTime() == null ? other.getdLoginTime() == null : this.getdLoginTime().equals(other.getdLoginTime()))
            && (this.getdLogoutTime() == null ? other.getdLogoutTime() == null : this.getdLogoutTime().equals(other.getdLogoutTime()))
            && (this.getVcIp() == null ? other.getVcIp() == null : this.getVcIp().equals(other.getVcIp()))
            && (this.getVcDeviceType() == null ? other.getVcDeviceType() == null : this.getVcDeviceType().equals(other.getVcDeviceType()))
            && (this.getVcBrowserType() == null ? other.getVcBrowserType() == null : this.getVcBrowserType().equals(other.getVcBrowserType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getlUserId() == null) ? 0 : getlUserId().hashCode());
        result = prime * result + ((getdLoginTime() == null) ? 0 : getdLoginTime().hashCode());
        result = prime * result + ((getdLogoutTime() == null) ? 0 : getdLogoutTime().hashCode());
        result = prime * result + ((getVcIp() == null) ? 0 : getVcIp().hashCode());
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
        sb.append(", dLoginTime=").append(dLoginTime);
        sb.append(", dLogoutTime=").append(dLogoutTime);
        sb.append(", vcIp=").append(vcIp);
        sb.append(", vcDeviceType=").append(vcDeviceType);
        sb.append(", vcBrowserType=").append(vcBrowserType);
        sb.append("]");
        return sb.toString();
    }
}