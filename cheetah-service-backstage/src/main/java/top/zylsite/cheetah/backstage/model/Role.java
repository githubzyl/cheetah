package top.zylsite.cheetah.backstage.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "t_role")
public class Role implements Serializable {
    @Id
    private Integer id;

    /**
     * 角色编码
     */
    @Column(name = "vc_code")
    private String vcCode;

    /**
     * 角色名称
     */
    @Column(name = "vc_name")
    private String vcName;

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
     * 获取角色编码
     *
     * @return vc_code - 角色编码
     */
    public String getVcCode() {
        return vcCode;
    }

    /**
     * 设置角色编码
     *
     * @param vcCode 角色编码
     */
    public void setVcCode(String vcCode) {
        this.vcCode = vcCode == null ? null : vcCode.trim();
    }

    /**
     * 获取角色名称
     *
     * @return vc_name - 角色名称
     */
    public String getVcName() {
        return vcName;
    }

    /**
     * 设置角色名称
     *
     * @param vcName 角色名称
     */
    public void setVcName(String vcName) {
        this.vcName = vcName == null ? null : vcName.trim();
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
        Role other = (Role) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getVcCode() == null ? other.getVcCode() == null : this.getVcCode().equals(other.getVcCode()))
            && (this.getVcName() == null ? other.getVcName() == null : this.getVcName().equals(other.getVcName()))
            && (this.getcEnable() == null ? other.getcEnable() == null : this.getcEnable().equals(other.getcEnable()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getVcCode() == null) ? 0 : getVcCode().hashCode());
        result = prime * result + ((getVcName() == null) ? 0 : getVcName().hashCode());
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
        sb.append(", cEnable=").append(cEnable);
        sb.append("]");
        return sb.toString();
    }
}