package top.zylsite.cheetah.backstage.service.master;
 
import top.zylsite.cheetah.base.common.BaseService;
import top.zylsite.cheetah.base.common.tree.BaseTree;
import top.zylsite.cheetah.base.common.tree.ZTreeNode;

import java.util.List;

import top.zylsite.cheetah.backstage.model.master.Department;
import top.zylsite.cheetah.backstage.model.vo.DepartmentVO;

public interface IDepartmentService extends BaseService<Department>{

	List<? extends BaseTree> getDepartmentTree(List<Department> departmentList, List<Integer> departmentIds);

	ZTreeNode createNode(Department entity, boolean async);

	List<Department> getDepartmentByPid(int id);

	ZTreeNode getRootNode();

	void changeStatus(Integer departmentId, String status);

	DepartmentVO queryById(Integer id);
 
}