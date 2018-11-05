package top.zylsite.cheetah.backstage.mapper.master.extend;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface ScheduledJobExtendMapper {

	@Update("update t_scheduled_job set c_status = #{status} where id = #{id}")
	public void updateStatusById(@Param("id") Integer id, @Param("status") String status);

	@Update("update t_scheduled_job set c_status = #{status}")
	public void updateStatus(@Param("status") String status);

	@Update("update t_scheduled_job set l_last_start_time = #{startTime}, l_last_end_time = #{endTime}, l_last_exec_status = #{executeStatus} where id = #{id}")
	public void updateExecuteStatus(@Param("id") Integer id, @Param("startTime") Long startTime,
			@Param("endTime") Long endTime, @Param("executeStatus") String executeStatus);

}