package top.zylsite.cheetah.backstage.model.master;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "t_data_dict")
public class DataDictionary implements Serializable {
    @Id
    private Integer id;

    @Column(name = "c_dict_entry")
    private String cDictEntry;

    @Column(name = "vc_entry_name")
    private String vcEntryName;

    @Column(name = "l_dict_type")
    private Integer lDictType;

    @Column(name = "l_sort")
    private Integer lSort;

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
     * @return c_dict_entry
     */
    public String getcDictEntry() {
        return cDictEntry;
    }

    /**
     * @param cDictEntry
     */
    public void setcDictEntry(String cDictEntry) {
        this.cDictEntry = cDictEntry == null ? null : cDictEntry.trim();
    }

    /**
     * @return vc_entry_name
     */
    public String getVcEntryName() {
        return vcEntryName;
    }

    /**
     * @param vcEntryName
     */
    public void setVcEntryName(String vcEntryName) {
        this.vcEntryName = vcEntryName == null ? null : vcEntryName.trim();
    }

    /**
     * @return l_dict_type
     */
    public Integer getlDictType() {
        return lDictType;
    }

    /**
     * @param lDictType
     */
    public void setlDictType(Integer lDictType) {
        this.lDictType = lDictType;
    }

    /**
     * @return l_sort
     */
    public Integer getlSort() {
        return lSort;
    }

    /**
     * @param lSort
     */
    public void setlSort(Integer lSort) {
        this.lSort = lSort;
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
        DataDictionary other = (DataDictionary) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getcDictEntry() == null ? other.getcDictEntry() == null : this.getcDictEntry().equals(other.getcDictEntry()))
            && (this.getVcEntryName() == null ? other.getVcEntryName() == null : this.getVcEntryName().equals(other.getVcEntryName()))
            && (this.getlDictType() == null ? other.getlDictType() == null : this.getlDictType().equals(other.getlDictType()))
            && (this.getlSort() == null ? other.getlSort() == null : this.getlSort().equals(other.getlSort()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getcDictEntry() == null) ? 0 : getcDictEntry().hashCode());
        result = prime * result + ((getVcEntryName() == null) ? 0 : getVcEntryName().hashCode());
        result = prime * result + ((getlDictType() == null) ? 0 : getlDictType().hashCode());
        result = prime * result + ((getlSort() == null) ? 0 : getlSort().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", cDictEntry=").append(cDictEntry);
        sb.append(", vcEntryName=").append(vcEntryName);
        sb.append(", lDictType=").append(lDictType);
        sb.append(", lSort=").append(lSort);
        sb.append("]");
        return sb.toString();
    }
}