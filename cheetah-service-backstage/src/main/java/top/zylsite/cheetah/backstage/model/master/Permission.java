package top.zylsite.cheetah.backstage.model.master;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "t_permission")
public class Permission implements Serializable {
    @Id
    private Integer id;

    /**
     * 权限编码
     */
    @Column(name = "vc_code")
    private String vcCode;

    /**
     * 权限名称
     */
    @Column(name = "vc_name")
    private String vcName;

    /**
     * 资源URL
     */
    @Column(name = "vc_url")
    private String vcUrl;

    /**
     * 资源图标
     */
    @Column(name = "vc_icon")
    private String vcIcon;

    /**
     * 父节点ID
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 是否启用(0：禁用，1：启用，默认是1)
     */
    @Column(name = "c_enable")
    private String cEnable;

    /**
     * 排序
     */
    @Column(name = "l_sort")
    private Integer lSort;

    /**
     * 1:代表菜单，2：代表按钮
     */
    @Column(name = "c_resource_type")
    private String cResourceType;

    /**
     * 父节点ID集合
     */
    @Column(name = "parent_ids")
    private String parentIds;

    /**
     * 在新窗口打开
     */
    @Column(name = "c_target_blank")
    private String cTargetBlank;

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
     * 获取权限编码
     *
     * @return vc_code - 权限编码
     */
    public String getVcCode() {
        return vcCode;
    }

    /**
     * 设置权限编码
     *
     * @param vcCode 权限编码
     */
    public void setVcCode(String vcCode) {
        this.vcCode = vcCode == null ? null : vcCode.trim();
    }

    /**
     * 获取权限名称
     *
     * @return vc_name - 权限名称
     */
    public String getVcName() {
        return vcName;
    }

    /**
     * 设置权限名称
     *
     * @param vcName 权限名称
     */
    public void setVcName(String vcName) {
        this.vcName = vcName == null ? null : vcName.trim();
    }

    /**
     * 获取资源URL
     *
     * @return vc_url - 资源URL
     */
    public String getVcUrl() {
        return vcUrl;
    }

    /**
     * 设置资源URL
     *
     * @param vcUrl 资源URL
     */
    public void setVcUrl(String vcUrl) {
        this.vcUrl = vcUrl == null ? null : vcUrl.trim();
    }

    /**
     * 获取资源图标
     *
     * @return vc_icon - 资源图标
     */
    public String getVcIcon() {
        return vcIcon;
    }

    /**
     * 设置资源图标
     *
     * @param vcIcon 资源图标
     */
    public void setVcIcon(String vcIcon) {
        this.vcIcon = vcIcon == null ? null : vcIcon.trim();
    }

    /**
     * 获取父节点ID
     *
     * @return parent_id - 父节点ID
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父节点ID
     *
     * @param parentId 父节点ID
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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
     * 获取1:代表菜单，2：代表按钮
     *
     * @return c_resource_type - 1:代表菜单，2：代表按钮
     */
    public String getcResourceType() {
        return cResourceType;
    }

    /**
     * 设置1:代表菜单，2：代表按钮
     *
     * @param cResourceType 1:代表菜单，2：代表按钮
     */
    public void setcResourceType(String cResourceType) {
        this.cResourceType = cResourceType == null ? null : cResourceType.trim();
    }

    /**
     * 获取父节点ID集合
     *
     * @return parent_ids - 父节点ID集合
     */
    public String getParentIds() {
        return parentIds;
    }

    /**
     * 设置父节点ID集合
     *
     * @param parentIds 父节点ID集合
     */
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds == null ? null : parentIds.trim();
    }

    /**
     * 获取在新窗口打开
     *
     * @return c_target_blank - 在新窗口打开
     */
    public String getcTargetBlank() {
        return cTargetBlank;
    }

    /**
     * 设置在新窗口打开
     *
     * @param cTargetBlank 在新窗口打开
     */
    public void setcTargetBlank(String cTargetBlank) {
        this.cTargetBlank = cTargetBlank == null ? null : cTargetBlank.trim();
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
        Permission other = (Permission) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getVcCode() == null ? other.getVcCode() == null : this.getVcCode().equals(other.getVcCode()))
            && (this.getVcName() == null ? other.getVcName() == null : this.getVcName().equals(other.getVcName()))
            && (this.getVcUrl() == null ? other.getVcUrl() == null : this.getVcUrl().equals(other.getVcUrl()))
            && (this.getVcIcon() == null ? other.getVcIcon() == null : this.getVcIcon().equals(other.getVcIcon()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getcEnable() == null ? other.getcEnable() == null : this.getcEnable().equals(other.getcEnable()))
            && (this.getlSort() == null ? other.getlSort() == null : this.getlSort().equals(other.getlSort()))
            && (this.getcResourceType() == null ? other.getcResourceType() == null : this.getcResourceType().equals(other.getcResourceType()))
            && (this.getParentIds() == null ? other.getParentIds() == null : this.getParentIds().equals(other.getParentIds()))
            && (this.getcTargetBlank() == null ? other.getcTargetBlank() == null : this.getcTargetBlank().equals(other.getcTargetBlank()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getVcCode() == null) ? 0 : getVcCode().hashCode());
        result = prime * result + ((getVcName() == null) ? 0 : getVcName().hashCode());
        result = prime * result + ((getVcUrl() == null) ? 0 : getVcUrl().hashCode());
        result = prime * result + ((getVcIcon() == null) ? 0 : getVcIcon().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getcEnable() == null) ? 0 : getcEnable().hashCode());
        result = prime * result + ((getlSort() == null) ? 0 : getlSort().hashCode());
        result = prime * result + ((getcResourceType() == null) ? 0 : getcResourceType().hashCode());
        result = prime * result + ((getParentIds() == null) ? 0 : getParentIds().hashCode());
        result = prime * result + ((getcTargetBlank() == null) ? 0 : getcTargetBlank().hashCode());
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
        sb.append(", vcUrl=").append(vcUrl);
        sb.append(", vcIcon=").append(vcIcon);
        sb.append(", parentId=").append(parentId);
        sb.append(", cEnable=").append(cEnable);
        sb.append(", lSort=").append(lSort);
        sb.append(", cResourceType=").append(cResourceType);
        sb.append(", parentIds=").append(parentIds);
        sb.append(", cTargetBlank=").append(cTargetBlank);
        sb.append("]");
        return sb.toString();
    }
}