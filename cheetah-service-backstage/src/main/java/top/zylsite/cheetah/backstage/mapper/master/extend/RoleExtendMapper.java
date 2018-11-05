package top.zylsite.cheetah.backstage.mapper.master.extend;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface RoleExtendMapper {

	@Update("update t_role set c_enable = #{status} where id = #{roleId}")
	void updateStatus(@Param("roleId") Integer roleId, @Param("status") String status);

}