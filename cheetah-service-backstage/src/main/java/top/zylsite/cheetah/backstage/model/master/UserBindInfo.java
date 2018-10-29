package top.zylsite.cheetah.backstage.model.master;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "t_user_bind_info")
public class UserBindInfo implements Serializable {
    /**
     * 自增ID
     */
    @Id
    private Integer id;

    /**
     * 绑定的用户ID
     */
    @Column(name = "l_user_id")
    private Integer lUserId;

    /**
     * 第三方账号
     */
    @Column(name = "vc_account")
    private String vcAccount;

    /**
     * 昵称
     */
    @Column(name = "vc_nick_name")
    private String vcNickName;

    /**
     * 用户头像链接
     */
    @Column(name = "vc_photo")
    private String vcPhoto;

    /**
     * 账号类型
     */
    @Column(name = "c_type")
    private String cType;

    private static final long serialVersionUID = 1L;

    /**
     * 获取自增ID
     *
     * @return id - 自增ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置自增ID
     *
     * @param id 自增ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取绑定的用户ID
     *
     * @return l_user_id - 绑定的用户ID
     */
    public Integer getlUserId() {
        return lUserId;
    }

    /**
     * 设置绑定的用户ID
     *
     * @param lUserId 绑定的用户ID
     */
    public void setlUserId(Integer lUserId) {
        this.lUserId = lUserId;
    }

    /**
     * 获取第三方账号
     *
     * @return vc_account - 第三方账号
     */
    public String getVcAccount() {
        return vcAccount;
    }

    /**
     * 设置第三方账号
     *
     * @param vcAccount 第三方账号
     */
    public void setVcAccount(String vcAccount) {
        this.vcAccount = vcAccount == null ? null : vcAccount.trim();
    }

    /**
     * 获取昵称
     *
     * @return vc_nick_name - 昵称
     */
    public String getVcNickName() {
        return vcNickName;
    }

    /**
     * 设置昵称
     *
     * @param vcNickName 昵称
     */
    public void setVcNickName(String vcNickName) {
        this.vcNickName = vcNickName == null ? null : vcNickName.trim();
    }

    /**
     * 获取用户头像链接
     *
     * @return vc_photo - 用户头像链接
     */
    public String getVcPhoto() {
        return vcPhoto;
    }

    /**
     * 设置用户头像链接
     *
     * @param vcPhoto 用户头像链接
     */
    public void setVcPhoto(String vcPhoto) {
        this.vcPhoto = vcPhoto == null ? null : vcPhoto.trim();
    }

    /**
     * 获取账号类型
     *
     * @return c_type - 账号类型
     */
    public String getcType() {
        return cType;
    }

    /**
     * 设置账号类型
     *
     * @param cType 账号类型
     */
    public void setcType(String cType) {
        this.cType = cType == null ? null : cType.trim();
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
        UserBindInfo other = (UserBindInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getlUserId() == null ? other.getlUserId() == null : this.getlUserId().equals(other.getlUserId()))
            && (this.getVcAccount() == null ? other.getVcAccount() == null : this.getVcAccount().equals(other.getVcAccount()))
            && (this.getVcNickName() == null ? other.getVcNickName() == null : this.getVcNickName().equals(other.getVcNickName()))
            && (this.getVcPhoto() == null ? other.getVcPhoto() == null : this.getVcPhoto().equals(other.getVcPhoto()))
            && (this.getcType() == null ? other.getcType() == null : this.getcType().equals(other.getcType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getlUserId() == null) ? 0 : getlUserId().hashCode());
        result = prime * result + ((getVcAccount() == null) ? 0 : getVcAccount().hashCode());
        result = prime * result + ((getVcNickName() == null) ? 0 : getVcNickName().hashCode());
        result = prime * result + ((getVcPhoto() == null) ? 0 : getVcPhoto().hashCode());
        result = prime * result + ((getcType() == null) ? 0 : getcType().hashCode());
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
        sb.append(", vcAccount=").append(vcAccount);
        sb.append(", vcNickName=").append(vcNickName);
        sb.append(", vcPhoto=").append(vcPhoto);
        sb.append(", cType=").append(cType);
        sb.append("]");
        return sb.toString();
    }
}