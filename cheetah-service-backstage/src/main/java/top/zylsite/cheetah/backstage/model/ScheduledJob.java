package top.zylsite.cheetah.backstage.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "t_scheduled_job")
public class ScheduledJob implements Serializable {
    @Id
    private Integer id;

    /**
     * 任务名称
     */
    @Column(name = "vc_job_name")
    private String vcJobName;

    /**
     * 任务组名
     */
    @Column(name = "vc_jobgroup_name")
    private String vcJobgroupName;

    /**
     * 触发器名
     */
    @Column(name = "vc_trigger_name")
    private String vcTriggerName;

    /**
     * 触发器组名
     */
    @Column(name = "vc_triggergroup_name")
    private String vcTriggergroupName;

    /**
     * 任务执行类
     */
    @Column(name = "vc_jobclass_name")
    private String vcJobclassName;

    /**
     * cron表达式
     */
    @Column(name = "vc_cron_expression")
    private String vcCronExpression;

    /**
     * 任务状态(0：运行中，1：已停止)
     */
    @Column(name = "c_status")
    private String cStatus;

    /**
     * 程序启动时立即执行(0：否，1：是，默认否)
     */
    @Column(name = "c_exec_on_startup")
    private String cExecOnStartup;

    /**
     * 最后一次开始执行时间
     */
    @Column(name = "l_last_start_time")
    private Long lLastStartTime;

    /**
     * 最后一次执行完成时间
     */
    @Column(name = "l_last_end_time")
    private Long lLastEndTime;

    /**
     * 最后一次执行状态（0：正常，1：异常）
     */
    @Column(name = "l_last_exec_status")
    private String lLastExecStatus;

    /**
     * 事件编码
     */
    @Column(name = "l_event_id")
    private Integer lEventId;

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
     * 获取任务名称
     *
     * @return vc_job_name - 任务名称
     */
    public String getVcJobName() {
        return vcJobName;
    }

    /**
     * 设置任务名称
     *
     * @param vcJobName 任务名称
     */
    public void setVcJobName(String vcJobName) {
        this.vcJobName = vcJobName == null ? null : vcJobName.trim();
    }

    /**
     * 获取任务组名
     *
     * @return vc_jobgroup_name - 任务组名
     */
    public String getVcJobgroupName() {
        return vcJobgroupName;
    }

    /**
     * 设置任务组名
     *
     * @param vcJobgroupName 任务组名
     */
    public void setVcJobgroupName(String vcJobgroupName) {
        this.vcJobgroupName = vcJobgroupName == null ? null : vcJobgroupName.trim();
    }

    /**
     * 获取触发器名
     *
     * @return vc_trigger_name - 触发器名
     */
    public String getVcTriggerName() {
        return vcTriggerName;
    }

    /**
     * 设置触发器名
     *
     * @param vcTriggerName 触发器名
     */
    public void setVcTriggerName(String vcTriggerName) {
        this.vcTriggerName = vcTriggerName == null ? null : vcTriggerName.trim();
    }

    /**
     * 获取触发器组名
     *
     * @return vc_triggergroup_name - 触发器组名
     */
    public String getVcTriggergroupName() {
        return vcTriggergroupName;
    }

    /**
     * 设置触发器组名
     *
     * @param vcTriggergroupName 触发器组名
     */
    public void setVcTriggergroupName(String vcTriggergroupName) {
        this.vcTriggergroupName = vcTriggergroupName == null ? null : vcTriggergroupName.trim();
    }

    /**
     * 获取任务执行类
     *
     * @return vc_jobclass_name - 任务执行类
     */
    public String getVcJobclassName() {
        return vcJobclassName;
    }

    /**
     * 设置任务执行类
     *
     * @param vcJobclassName 任务执行类
     */
    public void setVcJobclassName(String vcJobclassName) {
        this.vcJobclassName = vcJobclassName == null ? null : vcJobclassName.trim();
    }

    /**
     * 获取cron表达式
     *
     * @return vc_cron_expression - cron表达式
     */
    public String getVcCronExpression() {
        return vcCronExpression;
    }

    /**
     * 设置cron表达式
     *
     * @param vcCronExpression cron表达式
     */
    public void setVcCronExpression(String vcCronExpression) {
        this.vcCronExpression = vcCronExpression == null ? null : vcCronExpression.trim();
    }

    /**
     * 获取任务状态(0：运行中，1：已停止)
     *
     * @return c_status - 任务状态(0：运行中，1：已停止)
     */
    public String getcStatus() {
        return cStatus;
    }

    /**
     * 设置任务状态(0：运行中，1：已停止)
     *
     * @param cStatus 任务状态(0：运行中，1：已停止)
     */
    public void setcStatus(String cStatus) {
        this.cStatus = cStatus == null ? null : cStatus.trim();
    }

    /**
     * 获取程序启动时立即执行(0：否，1：是，默认否)
     *
     * @return c_exec_on_startup - 程序启动时立即执行(0：否，1：是，默认否)
     */
    public String getcExecOnStartup() {
        return cExecOnStartup;
    }

    /**
     * 设置程序启动时立即执行(0：否，1：是，默认否)
     *
     * @param cExecOnStartup 程序启动时立即执行(0：否，1：是，默认否)
     */
    public void setcExecOnStartup(String cExecOnStartup) {
        this.cExecOnStartup = cExecOnStartup == null ? null : cExecOnStartup.trim();
    }

    /**
     * 获取最后一次开始执行时间
     *
     * @return l_last_start_time - 最后一次开始执行时间
     */
    public Long getlLastStartTime() {
        return lLastStartTime;
    }

    /**
     * 设置最后一次开始执行时间
     *
     * @param lLastStartTime 最后一次开始执行时间
     */
    public void setlLastStartTime(Long lLastStartTime) {
        this.lLastStartTime = lLastStartTime;
    }

    /**
     * 获取最后一次执行完成时间
     *
     * @return l_last_end_time - 最后一次执行完成时间
     */
    public Long getlLastEndTime() {
        return lLastEndTime;
    }

    /**
     * 设置最后一次执行完成时间
     *
     * @param lLastEndTime 最后一次执行完成时间
     */
    public void setlLastEndTime(Long lLastEndTime) {
        this.lLastEndTime = lLastEndTime;
    }

    /**
     * 获取最后一次执行状态（0：正常，1：异常）
     *
     * @return l_last_exec_status - 最后一次执行状态（0：正常，1：异常）
     */
    public String getlLastExecStatus() {
        return lLastExecStatus;
    }

    /**
     * 设置最后一次执行状态（0：正常，1：异常）
     *
     * @param lLastExecStatus 最后一次执行状态（0：正常，1：异常）
     */
    public void setlLastExecStatus(String lLastExecStatus) {
        this.lLastExecStatus = lLastExecStatus == null ? null : lLastExecStatus.trim();
    }

    /**
     * 获取事件编码
     *
     * @return l_event_id - 事件编码
     */
    public Integer getlEventId() {
        return lEventId;
    }

    /**
     * 设置事件编码
     *
     * @param lEventId 事件编码
     */
    public void setlEventId(Integer lEventId) {
        this.lEventId = lEventId;
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
        ScheduledJob other = (ScheduledJob) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getVcJobName() == null ? other.getVcJobName() == null : this.getVcJobName().equals(other.getVcJobName()))
            && (this.getVcJobgroupName() == null ? other.getVcJobgroupName() == null : this.getVcJobgroupName().equals(other.getVcJobgroupName()))
            && (this.getVcTriggerName() == null ? other.getVcTriggerName() == null : this.getVcTriggerName().equals(other.getVcTriggerName()))
            && (this.getVcTriggergroupName() == null ? other.getVcTriggergroupName() == null : this.getVcTriggergroupName().equals(other.getVcTriggergroupName()))
            && (this.getVcJobclassName() == null ? other.getVcJobclassName() == null : this.getVcJobclassName().equals(other.getVcJobclassName()))
            && (this.getVcCronExpression() == null ? other.getVcCronExpression() == null : this.getVcCronExpression().equals(other.getVcCronExpression()))
            && (this.getcStatus() == null ? other.getcStatus() == null : this.getcStatus().equals(other.getcStatus()))
            && (this.getcExecOnStartup() == null ? other.getcExecOnStartup() == null : this.getcExecOnStartup().equals(other.getcExecOnStartup()))
            && (this.getlLastStartTime() == null ? other.getlLastStartTime() == null : this.getlLastStartTime().equals(other.getlLastStartTime()))
            && (this.getlLastEndTime() == null ? other.getlLastEndTime() == null : this.getlLastEndTime().equals(other.getlLastEndTime()))
            && (this.getlLastExecStatus() == null ? other.getlLastExecStatus() == null : this.getlLastExecStatus().equals(other.getlLastExecStatus()))
            && (this.getlEventId() == null ? other.getlEventId() == null : this.getlEventId().equals(other.getlEventId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getVcJobName() == null) ? 0 : getVcJobName().hashCode());
        result = prime * result + ((getVcJobgroupName() == null) ? 0 : getVcJobgroupName().hashCode());
        result = prime * result + ((getVcTriggerName() == null) ? 0 : getVcTriggerName().hashCode());
        result = prime * result + ((getVcTriggergroupName() == null) ? 0 : getVcTriggergroupName().hashCode());
        result = prime * result + ((getVcJobclassName() == null) ? 0 : getVcJobclassName().hashCode());
        result = prime * result + ((getVcCronExpression() == null) ? 0 : getVcCronExpression().hashCode());
        result = prime * result + ((getcStatus() == null) ? 0 : getcStatus().hashCode());
        result = prime * result + ((getcExecOnStartup() == null) ? 0 : getcExecOnStartup().hashCode());
        result = prime * result + ((getlLastStartTime() == null) ? 0 : getlLastStartTime().hashCode());
        result = prime * result + ((getlLastEndTime() == null) ? 0 : getlLastEndTime().hashCode());
        result = prime * result + ((getlLastExecStatus() == null) ? 0 : getlLastExecStatus().hashCode());
        result = prime * result + ((getlEventId() == null) ? 0 : getlEventId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", vcJobName=").append(vcJobName);
        sb.append(", vcJobgroupName=").append(vcJobgroupName);
        sb.append(", vcTriggerName=").append(vcTriggerName);
        sb.append(", vcTriggergroupName=").append(vcTriggergroupName);
        sb.append(", vcJobclassName=").append(vcJobclassName);
        sb.append(", vcCronExpression=").append(vcCronExpression);
        sb.append(", cStatus=").append(cStatus);
        sb.append(", cExecOnStartup=").append(cExecOnStartup);
        sb.append(", lLastStartTime=").append(lLastStartTime);
        sb.append(", lLastEndTime=").append(lLastEndTime);
        sb.append(", lLastExecStatus=").append(lLastExecStatus);
        sb.append(", lEventId=").append(lEventId);
        sb.append("]");
        return sb.toString();
    }
}