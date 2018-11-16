package top.zylsite.cheetah.backstage.model.master;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "t_department")
public class Department implements Serializable {
    @Id
    private Integer id;

    /**
     * 部门编码
     */
    @Column(name = "vc_code")
    private String vcCode;

    /**
     * 部门名称
     */
    @Column(name = "vc_name")
    private String vcName;

    private Integer pid;

    private String pids;

    /**
     * 租户ID
     */
    @Column(name = "l_tenant_id")
    private Integer lTenantId;

    /**
     * 排序
     */
    @Column(name = "l_sort")
    private Integer lSort;

    /**
     * 是否启用(0：禁用，1：启用，默认是1)
     */
    @Column(name = "c_enable")
    private String cEnable;

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
     * 获取部门编码
     *
     * @return vc_code - 部门编码
     */
    public String getVcCode() {
        return vcCode;
    }

    /**
     * 设置部门编码
     *
     * @param vcCode 部门编码
     */
    public void setVcCode(String vcCode) {
        this.vcCode = vcCode == null ? null : vcCode.trim();
    }

    /**
     * 获取部门名称
     *
     * @return vc_name - 部门名称
     */
    public String getVcName() {
        return vcName;
    }

    /**
     * 设置部门名称
     *
     * @param vcName 部门名称
     */
    public void setVcName(String vcName) {
        this.vcName = vcName == null ? null : vcName.trim();
    }

    /**
     * @return pid
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * @return pids
     */
    public String getPids() {
        return pids;
    }

    /**
     * @param pids
     */
    public void setPids(String pids) {
        this.pids = pids == null ? null : pids.trim();
    }

    /**
     * 获取租户ID
     *
     * @return l_tenant_id - 租户ID
     */
    public Integer getlTenantId() {
        return lTenantId;
    }

    /**
     * 设置租户ID
     *
     * @param lTenantId 租户ID
     */
    public void setlTenantId(Integer lTenantId) {
        this.lTenantId = lTenantId;
    }

    /**
     * 获取排序
     *
     * @return l_sort - 排序
     */
    public Integer getlSort() {
        return lSort;
    }

    /**
     * 设置排序
     *
     * @param lSort 排序
     */
    public void setlSort(Integer lSort) {
        this.lSort = lSort;
    }

    /**
     * 获取是否启用(0：禁用，1：启用，默认是1)
     *
     * @return c_enable - 是否启用(0：禁用，1：启用，默认是1)
     */
    public String getcEnable() {
        return cEnable;
    }

    /**
     * 设置是否启用(0：禁用，1：启用，默认是1)
     *
     * @param cEnable 是否启用(0：禁用，1：启用，默认是1)
     */
    public void setcEnable(String cEnable) {
        this.cEnable = cEnable == null ? null : cEnable.trim();
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
        Department other = (Department) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getVcCode() == null ? other.getVcCode() == null : this.getVcCode().equals(other.getVcCode()))
            && (this.getVcName() == null ? other.getVcName() == null : this.getVcName().equals(other.getVcName()))
            && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getPids() == null ? other.getPids() == null : this.getPids().equals(other.getPids()))
            && (this.getlTenantId() == null ? other.getlTenantId() == null : this.getlTenantId().equals(other.getlTenantId()))
            && (this.getlSort() == null ? other.getlSort() == null : this.getlSort().equals(other.getlSort()))
            && (this.getcEnable() == null ? other.getcEnable() == null : this.getcEnable().equals(other.getcEnable()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getVcCode() == null) ? 0 : getVcCode().hashCode());
        result = prime * result + ((getVcName() == null) ? 0 : getVcName().hashCode());
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getPids() == null) ? 0 : getPids().hashCode());
        result = prime * result + ((getlTenantId() == null) ? 0 : getlTenantId().hashCode());
        result = prime * result + ((getlSort() == null) ? 0 : getlSort().hashCode());
        result = prime * result + ((getcEnable() == null) ? 0 : getcEnable().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", vcCode=").append(vcCode);
        sb.append(", vcName=").append(vcName);
        sb.append(", pid=").append(pid);
        sb.append(", pids=").append(pids);
        sb.append(", lTenantId=").append(lTenantId);
        sb.append(", lSort=").append(lSort);
        sb.append(", cEnable=").append(cEnable);
        sb.append("]");
        return sb.toString();
    }
}