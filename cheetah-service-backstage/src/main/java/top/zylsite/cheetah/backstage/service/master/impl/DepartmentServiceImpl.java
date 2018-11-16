package top.zylsite.cheetah.backstage.service.master.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import tk.mybatis.mapper.entity.Example;
import top.zylsite.cheetah.backstage.mapper.master.DepartmentMapper;
import top.zylsite.cheetah.backstage.model.master.Department;
import top.zylsite.cheetah.backstage.model.vo.DepartmentVO;
import top.zylsite.cheetah.backstage.service.master.IDepartmentService;
import top.zylsite.cheetah.base.common.BaseMapper;
import top.zylsite.cheetah.base.common.BaseServiceImpl;
import top.zylsite.cheetah.base.common.tree.BaseTree;
import top.zylsite.cheetah.base.common.tree.ZTreeNode;
import top.zylsite.cheetah.base.utils.ReflectionUtilEX;

@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements IDepartmentService {

	private final static String ROOT_DEPARTMENT_NAME = "根部门";

	@Autowired
	private DepartmentMapper departmentMapper;

	@Override
	protected BaseMapper<Department> getBaseMapper() {
		return departmentMapper;
	}

	@Override
	public List<? extends BaseTree> getDepartmentTree(List<Department> departmentList, List<Integer> departmentIds) {
		return getZTree(departmentList, departmentIds);
	}

	@Override
	public ZTreeNode createNode(Department entity, boolean async) {
		ZTreeNode node = new ZTreeNode();
		node.setId(entity.getId());
		node.setpId(entity.getPid());
		node.setName(entity.getVcName());
		node.setParentNames(entity.getPids());
		if (async) {
			node.setParent(hasChildren(node.getId()));
		}
		return node;
	}

	@Override
	public List<Department> getDepartmentByPid(int id) {
		Example example = new Example(Department.class);
		example.createCriteria().andEqualTo("pid", id);
		example.orderBy("lSort").asc();
		return departmentMapper.selectByExample(example);
	}

	@Override
	public ZTreeNode getRootNode() {
		return new ZTreeNode(0, 0, ROOT_DEPARTMENT_NAME, "iconfont icon-xitong");// 根节点自定义，但是要和pid对应好
	}
	
	@Override
	public void changeStatus(Integer departmentId, String status) {
		Department department = new Department();
		department.setId(departmentId);
		department.setcEnable(status);
		departmentMapper.updateByPrimaryKeySelective(department);
	}
	
	@Override
	public DepartmentVO queryById(Integer id) {
		Department department = departmentMapper.selectByPrimaryKey(id);
		DepartmentVO departmentVO = new DepartmentVO();
		ReflectionUtilEX.copyProperities(department, departmentVO);
		if(null == department.getPid() || 0 == department.getPid()) {
			departmentVO.setpName(ROOT_DEPARTMENT_NAME);
		}else {
			Department parent =departmentMapper.selectByPrimaryKey(department.getPid());
			if(null != parent) {
				departmentVO.setpName(parent.getVcName());
			}
		}
		return departmentVO;
	}

	private List<? extends BaseTree> getZTree(List<Department> departmentList, List<Integer> departmentIds) {
		List<ZTreeNode> list = new ArrayList<>(1);
		List<ZTreeNode> nodes = null;
		if (!CollectionUtils.isEmpty(departmentList)) {
			nodes = new ArrayList<>();// 把所有资源转换成树模型的节点集合，此容器用于保存所有节点
			ZTreeNode node = null;
			for (Department entity : departmentList) {
				node = (ZTreeNode) createNode(entity, false);
				node.setChecked(getChecked(node, departmentIds));
				nodes.add(node);// 添加到节点容
			}
		}
		ZTreeNode root = getRootNode();
		// 只要有一个被选中，根节点就被选中
		if (!CollectionUtils.isEmpty(departmentIds)) {
			root.setChecked(true);
		}
		ZTreeNode tree = root.createTree(nodes, root);
		list.add(tree);
		return list;
	}

	private boolean getChecked(ZTreeNode node, List<Integer> departmentIds) {
		if (CollectionUtils.isEmpty(departmentIds)) {
			return false;
		}
		for (Integer id : departmentIds) {
			if (node.getId() == id) {
				return true;
			}
		}
		return false;
	}

	private boolean hasChildren(Integer id) {
		List<Department> list = this.getDepartmentByPid(id);
		return CollectionUtils.isEmpty(list) ? false : true;
	}

}