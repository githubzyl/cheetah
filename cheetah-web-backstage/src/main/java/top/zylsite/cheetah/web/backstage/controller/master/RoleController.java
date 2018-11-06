package top.zylsite.cheetah.web.backstage.controller.master;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.entity.Example;
import top.zylsite.cheetah.base.common.BaseService;
import top.zylsite.cheetah.base.common.BaseRequestController;
import top.zylsite.cheetah.base.common.QueryParameter;
import top.zylsite.cheetah.base.common.enums.EnableOrDisableEnum;
import top.zylsite.cheetah.web.backstage.common.annotation.ControllerLogs;
import top.zylsite.cheetah.backstage.service.master.IRoleService;
import top.zylsite.cheetah.backstage.model.master.Role;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseRequestController<Role> {

	@Autowired
	private IRoleService roleService;

	@Override
	protected BaseService<Role> getService() {
		return roleService;
	}

	@ControllerLogs(description="查询角色列表")
	@GetMapping("/list")
	public Object list(QueryParameter queryParameter, HttpServletRequest request) {
		return super.list(queryParameter, request);
	}

	@GetMapping("/{id}")
	public Object queryById(@PathVariable Integer id) {
		return super.queryByPrimaryKey(id);
	}

	@ControllerLogs(description="删除单个角色")
	@GetMapping("/remove/{id}")
	public Object remove(@PathVariable Integer id) {
		return super.removeByPrimaryKey(id);
	}

	@ControllerLogs(description="保存角色信息")
	@PostMapping("/save")
	public Object save(Role entity) {
		if (null == entity.getId()) {
			super.insert(entity);
		} else {
			super.update(entity);
		}
		return this.ajaxSuccess(null);
	}

	@ControllerLogs(description="批量删除角色")
	@GetMapping("/remove")
	public Object remove(Integer[] ids) {
		return super.remove(ids);
	}

	@ControllerLogs(description = "查询可用的角色")
	@GetMapping("/queryEnableRoles")
	public Object queryEnableRoles() {
		Example example = new Example(Role.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("cEnable", EnableOrDisableEnum.ENABLE.code());
		List<Role> list = roleService.queryList(example);
		return this.ajaxSuccess(list);
	}
	
	@ControllerLogs(description="为角色分配权限")
	@PostMapping("/savePermission")
	public Object saveMenu(Integer roleId, Integer[] permissions) {
		roleService.saveRoleInfo(roleId, permissions);
		return this.ajaxSuccess(null);
	}
	
	@ControllerLogs(description="修改角色状态")
	@PostMapping("/status")
	public Object saveStatus(Integer roleId, String status) {
		roleService.changeStatus(roleId, status);
		return this.ajaxSuccess(null);
	}

	@Override
	protected Example getExample(QueryParameter queryParameter, HttpServletRequest request) {
		String vcCode = request.getParameter("vcCode");
		String vcName = request.getParameter("vcName");
		Example example = new Example(Role.class);
		Example.Criteria criteria = example.createCriteria();
		super.andFullLike(criteria, "vcCode", vcCode);
		super.andFullLike(criteria, "vcName", vcName);
		return example;
	}

}