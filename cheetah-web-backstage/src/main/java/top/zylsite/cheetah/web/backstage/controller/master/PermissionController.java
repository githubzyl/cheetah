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
import top.zylsite.cheetah.backstage.model.master.Permission;
import top.zylsite.cheetah.backstage.model.master.RolePermission;
import top.zylsite.cheetah.backstage.model.vo.PermissionVO;
import top.zylsite.cheetah.backstage.service.master.IPermissionService;
import top.zylsite.cheetah.backstage.service.master.IRolePermissionService;
import top.zylsite.cheetah.base.common.BaseRequestController;
import top.zylsite.cheetah.base.common.BaseService;
import top.zylsite.cheetah.base.common.QueryParameter;
import top.zylsite.cheetah.base.common.enums.EnableOrDisableEnum;
import top.zylsite.cheetah.base.common.tree.BaseTree;
import top.zylsite.cheetah.base.common.tree.ZTreeNode;
import top.zylsite.cheetah.web.backstage.common.annotation.ControllerLogs;

@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseRequestController<Permission> {

	@Autowired
	private IPermissionService permissionService;

	@Autowired
	private IRolePermissionService rolePermissionService;

	@Override
	protected BaseService<Permission> getService() {
		return permissionService;
	}

	@ControllerLogs(description = "查询权限列表")
	@GetMapping("/list")
	public Object list(QueryParameter queryParameter, HttpServletRequest request) {
		return super.list(queryParameter, request);
	}

	@GetMapping("/{id}")
	public Object queryById(@PathVariable Integer id) {
		PermissionVO permissionVO = permissionService.queryById(id);
		return this.ajaxSuccess(permissionVO);
	}

	@ControllerLogs(description = "删除单个权限")
	@GetMapping("/remove/{id}")
	public Object remove(@PathVariable Integer id) {
		return super.removeByPrimaryKey(id);
	}

	@ControllerLogs(description = "新增权限信息")
	@PostMapping("/add")
	public Object add(Permission entity) {
		return this.save(entity);
	}

	@ControllerLogs(description = "编辑权限信息")
	@PostMapping("/edit")
	public Object edit(Permission entity) {
		return this.save(entity);
	}

	@ControllerLogs(description = "批量删除权限")
	@GetMapping("/remove")
	public Object remove(Integer[] ids) {
		return super.remove(ids);
	}

	@GetMapping("/tree")
	public Object permissionTree(@RequestParam(required = false) Integer roleId,
			@RequestParam(required = false) Integer permissionId,
			@RequestParam(defaultValue = "true") String containBtn) {
		// 查询所有的菜单集合
		Example example = new Example(Permission.class);
		example.setOrderByClause("l_sort asc");
		List<Permission> permissionList = permissionService.queryList(example);
		// 查找角色对应的权限
		List<RolePermission> rolePermissions = null;
		if (null != roleId) {
			Example example2 = new Example(RolePermission.class);
			example2.createCriteria().andEqualTo("roleId", roleId);
			rolePermissions = rolePermissionService.queryList(example2);
		}
		// 如果权限id不为空，那么要设置为选中状态
		if (null != permissionId) {
			if (null == rolePermissions) {
				rolePermissions = new ArrayList<>();
			}
			RolePermission rolePermission = new RolePermission();
			rolePermission.setPermissionId(permissionId);
			rolePermissions.add(rolePermission);
		}
		// 生成菜单树
		List<? extends BaseTree> tree = permissionService.getPermissionTreeWithPermissions(permissionList,
				rolePermissions, Boolean.parseBoolean(containBtn));
		if (!CollectionUtils.isEmpty(tree)) {
			return this.ajaxSuccess(tree.get(0));
		}
		return this.ajaxSuccess(tree);
	}

	@GetMapping("/queryByParentId")
	public Object queryByParentId(@RequestParam(required = true, defaultValue = "0") String parentId) throws Exception {
		List<Permission> list = permissionService.getPermissionByParentId(Integer.parseInt(parentId));
		if (CollectionUtils.isEmpty(list)) {
			return this.ajaxSuccess(list);
		}
		ArrayList<ZTreeNode> nodeList = new ArrayList<>(list.size());
		for (Permission entity : list) {
			nodeList.add((ZTreeNode) permissionService.createNode(entity, true));
		}
		if ("0".equals(parentId)) {
			ZTreeNode root = permissionService.getRootNode();
			root.setChildren(nodeList);
			if (!CollectionUtils.isEmpty(nodeList)) {
				root.setParent(true);
				root.setOpen(true);
			}
			return this.ajaxSuccess(root);
		}
		return this.ajaxSuccess(nodeList);
	}

	@ControllerLogs(description = "修改权限状态")
	@PostMapping("/status")
	public Object saveStatus(Integer permissionId, String status) {
		permissionService.changeStatus(permissionId, status);
		return this.ajaxSuccess(null);
	}

	@Override
	protected Example getExample(QueryParameter queryParameter, HttpServletRequest request) {
		String vcName = request.getParameter("vcName");
		String parentId = request.getParameter("parentId");
		Example example = new Example(Permission.class);
		Example.Criteria criteria = example.createCriteria();
		super.andFullLike(criteria, "vcName", vcName);
		if (StringUtils.isNotBlank(parentId)) {
			int pId = Integer.parseInt(parentId);
			criteria.andEqualTo("parentId", pId);

			Example.Criteria criteria2 = example.createCriteria();
			criteria2.andEqualTo("id", pId);
			example.or(criteria2);
		}
		example.orderBy("lSort").asc();
		return example;
	}

	private Object save(Permission entity) {
		entity.setParentIds(getParent(entity.getParentId()));
		if (null == entity.getId()) {
			entity.setcEnable(EnableOrDisableEnum.ENABLE.code());
			super.insert(entity);
		} else {
			super.update(entity);
		}
		return this.ajaxSuccess(null);
	}

	private String getParent(Integer parentId) {
		List<Permission> list = permissionService.queryList(null);
		String ids = getParentId(parentId, list);
		return ids;
	}

	private String getParentId(Integer id, List<Permission> list) {
		String ids = id.toString();
		if (0 == id) {
			return ids;
		}
		if (null != list && list.size() > 0) {
			for (Permission permission : list) {
				if (permission.getId() == id) {
					if (0 != permission.getParentId()) {
						ids += "," + getParentId(permission.getParentId(), list);
						return ids;
					} else {
						ids += "," + permission.getParentId();
					}
				}
			}
		}
		return ids;
	}

}