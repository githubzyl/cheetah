package top.zylsite.cheetah.backstage.model.master;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "t_user_view_log")
public class UserViewLog implements Serializable {
    @Id
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
     * 请求描述
     */
    @Column(name = "vc_operation")
    private String vcOperation;

    /**
     * 访问的URL
     */
    @Column(name = "vc_url")
    private String vcUrl;

    /**
     * 请求方式
     */
    @Column(name = "vc_method")
    private String vcMethod;

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

    /**
     * 访问时间
     */
    @Column(name = "d_visit_time")
    private Date dVisitTime;

    /**
     * 携带的参数
     */
    @Column(name = "vc_param")
    private String vcParam;

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
     * 获取请求描述
     *
     * @return vc_operation - 请求描述
     */
    public String getVcOperation() {
        return vcOperation;
    }

    /**
     * 设置请求描述
     *
     * @param vcOperation 请求描述
     */
    public void setVcOperation(String vcOperation) {
        this.vcOperation = vcOperation == null ? null : vcOperation.trim();
    }

    /**
     * 获取访问的URL
     *
     * @return vc_url - 访问的URL
     */
    public String getVcUrl() {
        return vcUrl;
    }

    /**
     * 设置访问的URL
     *
     * @param vcUrl 访问的URL
     */
    public void setVcUrl(String vcUrl) {
        this.vcUrl = vcUrl == null ? null : vcUrl.trim();
    }

    /**
     * 获取请求方式
     *
     * @return vc_method - 请求方式
     */
    public String getVcMethod() {
        return vcMethod;
    }

    /**
     * 设置请求方式
     *
     * @param vcMethod 请求方式
     */
    public void setVcMethod(String vcMethod) {
        this.vcMethod = vcMethod == null ? null : vcMethod.trim();
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

    /**
     * 获取访问时间
     *
     * @return d_visit_time - 访问时间
     */
    public Date getdVisitTime() {
        return dVisitTime;
    }

    /**
     * 设置访问时间
     *
     * @param dVisitTime 访问时间
     */
    public void setdVisitTime(Date dVisitTime) {
        this.dVisitTime = dVisitTime;
    }

    /**
     * 获取携带的参数
     *
     * @return vc_param - 携带的参数
     */
    public String getVcParam() {
        return vcParam;
    }

    /**
     * 设置携带的参数
     *
     * @param vcParam 携带的参数
     */
    public void setVcParam(String vcParam) {
        this.vcParam = vcParam == null ? null : vcParam.trim();
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
        UserViewLog other = (UserViewLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getlUserId() == null ? other.getlUserId() == null : this.getlUserId().equals(other.getlUserId()))
            && (this.getVcUserName() == null ? other.getVcUserName() == null : this.getVcUserName().equals(other.getVcUserName()))
            && (this.getVcOperation() == null ? other.getVcOperation() == null : this.getVcOperation().equals(other.getVcOperation()))
            && (this.getVcUrl() == null ? other.getVcUrl() == null : this.getVcUrl().equals(other.getVcUrl()))
            && (this.getVcMethod() == null ? other.getVcMethod() == null : this.getVcMethod().equals(other.getVcMethod()))
            && (this.getVcIp() == null ? other.getVcIp() == null : this.getVcIp().equals(other.getVcIp()))
            && (this.getVcDeviceType() == null ? other.getVcDeviceType() == null : this.getVcDeviceType().equals(other.getVcDeviceType()))
            && (this.getVcBrowserType() == null ? other.getVcBrowserType() == null : this.getVcBrowserType().equals(other.getVcBrowserType()))
            && (this.getdVisitTime() == null ? other.getdVisitTime() == null : this.getdVisitTime().equals(other.getdVisitTime()))
            && (this.getVcParam() == null ? other.getVcParam() == null : this.getVcParam().equals(other.getVcParam()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getlUserId() == null) ? 0 : getlUserId().hashCode());
        result = prime * result + ((getVcUserName() == null) ? 0 : getVcUserName().hashCode());
        result = prime * result + ((getVcOperation() == null) ? 0 : getVcOperation().hashCode());
        result = prime * result + ((getVcUrl() == null) ? 0 : getVcUrl().hashCode());
        result = prime * result + ((getVcMethod() == null) ? 0 : getVcMethod().hashCode());
        result = prime * result + ((getVcIp() == null) ? 0 : getVcIp().hashCode());
        result = prime * result + ((getVcDeviceType() == null) ? 0 : getVcDeviceType().hashCode());
        result = prime * result + ((getVcBrowserType() == null) ? 0 : getVcBrowserType().hashCode());
        result = prime * result + ((getdVisitTime() == null) ? 0 : getdVisitTime().hashCode());
        result = prime * result + ((getVcParam() == null) ? 0 : getVcParam().hashCode());
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
        sb.append(", vcOperation=").append(vcOperation);
        sb.append(", vcUrl=").append(vcUrl);
        sb.append(", vcMethod=").append(vcMethod);
        sb.append(", vcIp=").append(vcIp);
        sb.append(", vcDeviceType=").append(vcDeviceType);
        sb.append(", vcBrowserType=").append(vcBrowserType);
        sb.append(", dVisitTime=").append(dVisitTime);
        sb.append(", vcParam=").append(vcParam);
        sb.append("]");
        return sb.toString();
    }
}