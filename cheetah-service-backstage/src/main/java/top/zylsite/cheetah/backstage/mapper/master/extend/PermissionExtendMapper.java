package top.zylsite.cheetah.backstage.mapper.master.extend;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import top.zylsite.cheetah.backstage.model.vo.PermissionVO;

public interface PermissionExtendMapper {

	@Update("update t_permission set c_enable = #{status} where id = #{menuId}")
	void updateStatus(@Param("menuId") Integer menuId, @Param("status") String status);

	@Select("select p.id, p.vc_code, p.vc_name, p.vc_url, p.vc_icon, p.parent_id, p.c_enable, p.l_sort, p.c_resource_type, p.c_target_blank, (select n.vc_name from t_permission n where n.id = p.parent_id) parentName from t_permission p where p.id = #{permissionId}")
	PermissionVO selectById(@Param("permissionId") Integer permissionId);

}