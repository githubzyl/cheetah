package top.zylsite.cheetah.backstage.mapper.master.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import top.zylsite.cheetah.backstage.model.master.Permission;

public interface UserInfoExtendMapper {

	@Select("select distinct p.* from t_permission p inner join t_role_permission rp on p.id = rp.permission_id "
			+ "inner join t_role r on rp.role_id = r.id inner join t_user_role ur on r.id = ur.role_id "
			+ "where r.c_enable = 1 and p.c_enable = 1 and user_id = #{userId} order by l_sort asc")
	List<Permission> selectPermissionListByUserId(@Param("userId") Integer userId);

	@Update("update t_user set c_status = #{status} where id = #{userId}")
	void updateStatus(@Param("userId") Integer userId, @Param("status") String status);

	@Update("update t_user set vc_password = #{password} where id = #{userId}")
	void updatePassword(@Param("userId") Integer userId, @Param("password") String password);

	@Update("update t_user set c_lock_status = #{lockStatus} where id = #{userId}")
	void updateLockStatus(@Param("userId") Integer userId, @Param("lockStatus") String lockStatus);

}