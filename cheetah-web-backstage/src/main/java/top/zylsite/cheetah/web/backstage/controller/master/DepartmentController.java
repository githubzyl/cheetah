package top.zylsite.cheetah.web.backstage.controller.master;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import top.zylsite.cheetah.backstage.model.master.Department;
import top.zylsite.cheetah.backstage.model.vo.DepartmentVO;
import top.zylsite.cheetah.backstage.service.master.IDepartmentService;
import top.zylsite.cheetah.base.common.BaseRequestController;
import top.zylsite.cheetah.base.common.BaseService;
import top.zylsite.cheetah.base.common.QueryParameter;
import top.zylsite.cheetah.base.common.enums.EnableOrDisableEnum;
import top.zylsite.cheetah.base.common.tree.BaseTree;
import top.zylsite.cheetah.base.common.tree.ZTreeNode;
import top.zylsite.cheetah.web.backstage.common.annotation.ControllerLogs;

@RestController
@RequestMapping("/department")
public class DepartmentController extends BaseRequestController<Department> {

	@Autowired
	private IDepartmentService departmentService;

	@Override
	protected BaseService<Department> getService() {
		return departmentService;
	}

	@GetMapping("/list")
	public Object list(QueryParameter queryParameter, HttpServletRequest request) {
		return super.list(queryParameter, request);
	}

	@GetMapping("/{id}")
	public Object queryById(@PathVariable Integer id) {
		DepartmentVO departmentVO = departmentService.queryById(id);
		return this.ajaxSuccess(departmentVO);
	}

	@PostMapping("/add")
	public Object add(Department entity) {
		return this.save(entity);
	}

	@PostMapping("/edit")
	public Object edit(Department entity) {
		return this.save(entity);
	}

	@GetMapping("/remove/{id}")
	public Object remove(@PathVariable Integer id) {
		return super.removeByPrimaryKey(id);
	}

	@GetMapping("/remove")
	public Object remove(Integer[] ids) {
		return super.remove(ids);
	}

	@GetMapping("/tree")
	public Object departmentTree(@RequestParam(required = false) Integer departmentId) {
		// 查询所有的菜单集合
		Example example = new Example(Department.class);
		example.setOrderByClause("l_sort asc");
		List<Department> departmentList = departmentService.queryList(example);
		// 生成部门树
		List<Integer> departmentIds = new ArrayList<>();
		List<? extends BaseTree> tree = departmentService.getDepartmentTree(departmentList, departmentIds);
		if (!CollectionUtils.isEmpty(tree)) {
			return this.ajaxSuccess(tree.get(0));
		}
		return this.ajaxSuccess(tree);
	}

	@GetMapping("/queryByParentId")
	public Object queryByParentId(@RequestParam(required = true, defaultValue = "0") String parentId) throws Exception {
		List<Department> list = departmentService.getDepartmentByPid(Integer.parseInt(parentId));
		if (CollectionUtils.isEmpty(list)) {
			return this.ajaxSuccess(list);
		}
		ArrayList<ZTreeNode> nodeList = new ArrayList<>(list.size());
		for (Department entity : list) {
			nodeList.add((ZTreeNode) departmentService.createNode(entity, true));
		}
		if ("0".equals(parentId)) {
			ZTreeNode root = departmentService.getRootNode();
			root.setChildren(nodeList);
			if (!CollectionUtils.isEmpty(nodeList)) {
				root.setParent(true);
				root.setOpen(true);
			}
			return this.ajaxSuccess(root);
		}
		return this.ajaxSuccess(nodeList);
	}
	
	@ControllerLogs(description = "修改部门状态")
	@PostMapping("/status")
	public Object saveStatus(Integer departmentId, String status) {
		departmentService.changeStatus(departmentId, status);
		return this.ajaxSuccess(null);
	}

	@Override
	protected Example getExample(QueryParameter queryParameter, HttpServletRequest request) {
		String vcName = request.getParameter("vcName");
		String parentId = request.getParameter("pid");
		Example example = new Example(Department.class);
		Example.Criteria criteria = example.createCriteria();
		super.andFullLike(criteria, "vcName", vcName);
		if (StringUtils.isNotBlank(parentId)) {
			int pId = Integer.parseInt(parentId);
			criteria.andEqualTo("pid", pId);

			Example.Criteria criteria2 = example.createCriteria();
			criteria2.andEqualTo("id", pId);
			example.or(criteria2);
		}
		example.orderBy("lSort").asc();
		return example;
	}

	private Object save(Department entity) {
		entity.setPids(getParent(entity.getPid()));
		if (null == entity.getId()) {
			entity.setcEnable(EnableOrDisableEnum.ENABLE.code());
			super.insert(entity);
		} else {
			super.update(entity);
		}
		return this.ajaxSuccess(null);
	}
	
	private String getParent(Integer parentId) {
		List<Department> list = departmentService.queryList(null);
		String ids = getParentId(parentId, list);
		return ids;
	}

	private String getParentId(Integer id, List<Department> list) {
		String ids = id.toString();
		if (0 == id) {
			return ids;
		}
		if (null != list && list.size() > 0) {
			for (Department entity : list) {
				if (entity.getId() == id) {
					if (0 != entity.getPid()) {
						ids += "," + getParentId(entity.getPid(), list);
						return ids;
					} else {
						ids += "," + entity.getPid();
					}
				}
			}
		}
		return ids;
	}

}