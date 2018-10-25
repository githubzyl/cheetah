package top.zylsite.cheetah.backstage.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "t_dict_type")
public class DictionaryType implements Serializable {
    @Id
    private Integer id;

    /**
     * 类型编码
     */
    @Column(name = "vc_code")
    private String vcCode;

    /**
     * 类型名称
     */
    @Column(name = "vc_name")
    private String vcName;

    /**
     * 类型描述
     */
    @Column(name = "vc_description")
    private String vcDescription;

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
     * 获取类型编码
     *
     * @return vc_code - 类型编码
     */
    public String getVcCode() {
        return vcCode;
    }

    /**
     * 设置类型编码
     *
     * @param vcCode 类型编码
     */
    public void setVcCode(String vcCode) {
        this.vcCode = vcCode == null ? null : vcCode.trim();
    }

    /**
     * 获取类型名称
     *
     * @return vc_name - 类型名称
     */
    public String getVcName() {
        return vcName;
    }

    /**
     * 设置类型名称
     *
     * @param vcName 类型名称
     */
    public void setVcName(String vcName) {
        this.vcName = vcName == null ? null : vcName.trim();
    }

    /**
     * 获取类型描述
     *
     * @return vc_description - 类型描述
     */
    public String getVcDescription() {
        return vcDescription;
    }

    /**
     * 设置类型描述
     *
     * @param vcDescription 类型描述
     */
    public void setVcDescription(String vcDescription) {
        this.vcDescription = vcDescription == null ? null : vcDescription.trim();
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
        DictionaryType other = (DictionaryType) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getVcCode() == null ? other.getVcCode() == null : this.getVcCode().equals(other.getVcCode()))
            && (this.getVcName() == null ? other.getVcName() == null : this.getVcName().equals(other.getVcName()))
            && (this.getVcDescription() == null ? other.getVcDescription() == null : this.getVcDescription().equals(other.getVcDescription()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getVcCode() == null) ? 0 : getVcCode().hashCode());
        result = prime * result + ((getVcName() == null) ? 0 : getVcName().hashCode());
        result = prime * result + ((getVcDescription() == null) ? 0 : getVcDescription().hashCode());
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
        sb.append(", vcDescription=").append(vcDescription);
        sb.append("]");
        return sb.toString();
    }
}